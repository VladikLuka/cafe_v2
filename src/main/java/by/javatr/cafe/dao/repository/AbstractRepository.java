package by.javatr.cafe.dao.repository;

import by.javatr.cafe.dao.annotation.*;
import by.javatr.cafe.connection.impl.ConnectionPool;
import by.javatr.cafe.entity.Entity;
import by.javatr.cafe.exception.DAOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Contains
 * @param <T>
 */
public class AbstractRepository<T extends Entity<T>> {

    private static final String GET_LAST_ID = "select LAST_INSERT_ID()";

    /**
     * Return connection
     * @return Connection
     */
    public static Connection getConnection() throws DAOException {
        try {
            return ConnectionPool.CONNECTION_POOL.retrieve();
        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
    }

    /**
     * Update entity
     * @param connection connection
     * @param entity to be updated
     * @return updated entity
     */
    public T update (Connection connection, Entity<T> entity) throws DAOException {
        String table = null;
        int id = 0;
        String joinField = null;

        try {
            Map<String, Object> vars = new HashMap<>();

            final Class<? extends Entity> aClass = entity.getClass();
            final boolean annotationPresent = aClass.isAnnotationPresent(Table.class);
            if (annotationPresent) {
                table = aClass.getAnnotation(Table.class).table();
            }

            final java.lang.reflect.Field[] declaredFields = aClass.getDeclaredFields();

            String id_column_name = null;

            for (java.lang.reflect.Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                if (declaredField.get(entity) == null) {
                    continue;
                }
                if (declaredField.isAnnotationPresent(Field.class) || declaredField.isAnnotationPresent(Join.class) || declaredField.isAnnotationPresent(Id.class)) {

                    if (declaredField.isAnnotationPresent(Id.class)) {
                        id_column_name = declaredField.getAnnotation(Id.class).name();
                        id = declaredField.getInt(entity);
                    }

                    if (declaredField.isAnnotationPresent(Join.class)) {
                        joinField = declaredField.getAnnotation(Join.class).fieldColumn();
                        if(declaredField.get(entity) instanceof Enum){
                            int ordinal = ((Enum) declaredField.get(entity)).ordinal() + 1;
                            vars.put(joinField, ordinal);
                            continue;
                        }

                        if (declaredField.isAnnotationPresent(ManyToMany.class)) {
                            Object o = null;
                            o = declaredField.get(entity);
                            vars.put(joinField, o);
                        }
                        if (declaredField.isAnnotationPresent(OneToMany.class)) {
                            continue;
                        }
                        if (declaredField.isAnnotationPresent(ManyToOne.class)) {
                            ManyToOne annotation = declaredField.getAnnotation(ManyToOne.class);
                            String field = annotation.field();
                            final String fieldName = declaredField.getName();
                            Object o = declaredField.get(entity);

                            if (o != null) {
                                field = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                                Method getter = entity.getClass().getDeclaredMethod("get" + field);
                                Object primaryKey = getter.invoke(entity);
                                vars.put(joinField, primaryKey);
                            }
                        }
                    }

                    if (declaredField.isAnnotationPresent(Field.class)) {
                        Field declaredAnnotation = declaredField.getDeclaredAnnotation(Field.class);
                        String name = declaredAnnotation.name();
                        Object o = null;
                        try {
                            o = declaredField.get(entity);
                        } catch (IllegalAccessException e) {
                            throw new DAOException(e);
                        }
                        vars.put(name, o);
                    }

                }
                declaredField.setAccessible(false);
            }

            StringBuilder str = new StringBuilder();

            final Set<String> keys = vars.keySet();

            for (String key : keys) {
                final Object o = vars.get(key);
                if (o.getClass().equals(Boolean.class)) {
                    str.append(key).append("=").append(o).append(",");
                } else {
                    str.append(key).append("=").append("\"").append(o).append("\"").append(",");
                }
            }

            str.delete(str.length() - 1, str.length());

            String query = "update " + table + " set " + str + " where " + id_column_name + " = ?";

            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1, id);
                statement.executeUpdate();
                return (T) entity;
            }

        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        } catch (NoSuchMethodException e) {
            throw new DAOException("Can not find getter", e);
        } catch (InvocationTargetException e) {
            throw  new DAOException("can not invoke method", e);
        } catch (IllegalAccessException e) {
            throw new DAOException("Illegal modifier ",e);
        }
    }

    /**
     * Create entity
     * @param connection connection
     * @param entity entity being created
     * @return created entity
     */
    public T create(Connection connection, Entity<T> entity) throws DAOException {
        Map<String, Object> fieldValues = new HashMap<>();

        String table = null;
        final Class<? extends Entity> entityClass = entity.getClass();

        if (entityClass.isAnnotationPresent(Table.class)) {
            Table declaredAnnotation = entityClass.getDeclaredAnnotation(Table.class);
            table = declaredAnnotation.table();
        }

        java.lang.reflect.Field[] declaredFields = entityClass.getDeclaredFields();

        for (java.lang.reflect.Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (declaredField.isAnnotationPresent(Field.class)) {
                Field declaredAnnotation = declaredField.getDeclaredAnnotation(Field.class);
                String name = declaredAnnotation.name();
                Object o = null;
                try {
                    o = declaredField.get(entity);
                } catch (IllegalAccessException e) {
                    throw new DAOException(e);
                }
                fieldValues.put(name, o);
            }else if(declaredField.isAnnotationPresent(Join.class)){

                try {
                    if(declaredField.get(entity) instanceof Enum){
                        int ordinal = ((Enum) declaredField.get(entity)).ordinal();
                        String joinField = declaredField.getAnnotation(Join.class).fieldColumn();
                        fieldValues.put(joinField, ordinal);
                        break;
                    }
                } catch (IllegalAccessException e) {
                    throw new DAOException("Illegal modifier",e);
                }


                Join declaredAnnotation = declaredField.getDeclaredAnnotation(Join.class);
                String name = declaredAnnotation.fieldColumn();
                Object o = null;
                try {
                    o = declaredField.get(entity);
                } catch (IllegalAccessException e) {
                    throw new DAOException(e);
                }
                fieldValues.put(name, o);
            }
            declaredField.setAccessible(false);
        }
        Set<String> keys = fieldValues.keySet();
        StringBuilder columns = new StringBuilder();

        for (String key : keys) {
            columns.append(key).append(",");
        }

        columns.delete(columns.length() - 1, columns.length());

        //values
        StringBuilder values = new StringBuilder();
        StringBuilder countMark = new StringBuilder();
        for (String key : keys) {
            countMark.append("?").append(",");
            Object o = fieldValues.get(key);
            if(o.getClass().equals(Boolean.class)){
                values.append(key).append("=").append(o).append(",");
            }else {
                values.append(key).append("=").append("\"").append(o).append("\"").append(",");
            }
        }

        values.delete(values.length() - 2, values.length());
        countMark.delete(countMark.length() - 1, countMark.length());

        String query = "insert into " + table + "(" + columns + ")" + "values(" + countMark + ")";

        try (PreparedStatement statement = connection.prepareStatement(query);
             PreparedStatement statement2 = connection.prepareStatement(GET_LAST_ID);
        ){
            int count = 0;

            for (String key : keys) {
                count++;
                Object o = fieldValues.get(key);
                statement.setObject(count, o);
            }
            statement.executeUpdate();

            for (java.lang.reflect.Field field :declaredFields) {
                if(field.isAnnotationPresent(Id.class)){
                    try(ResultSet resultSet = statement2.executeQuery();)
                    {   field.setAccessible(true);
                        resultSet.next();
                        field.set(entity, resultSet.getInt(1));
                        field.setAccessible(false);
                    }
                }
            }
        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        } catch (IllegalAccessException e) {
            throw new DAOException("invalid modifier",e);
        }
            return (T) entity;
    }

    /**
     * Delete entity
     * @param connection connection
     * @param entity entity being deleted
     * @return boolean
     */
    public boolean delete(Connection connection, Entity<T> entity) throws DAOException {
        String table = null;
        int id = 0;
        try {
            if(entity.getClass().isAnnotationPresent(Table.class)){
                table = entity.getClass().getAnnotation(Table.class).table();
            }else{
                throw new IllegalArgumentException("NO TABLE");
            }

            StringBuilder query = new StringBuilder("DELETE FROM " + table + " WHERE " );

            for (java.lang.reflect.Field field:entity.getClass().getDeclaredFields()) {
                if(field.isAnnotationPresent(Id.class)){
                    field.setAccessible(true);
                    query.append(field.getAnnotation(Id.class).name()).append(" = ?");
                    id = field.getInt(entity);
                    field.setAccessible(false);
                }
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement(query.toString())){
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        } catch (IllegalAccessException e) {
            throw new DAOException("invalid modifier",e);
        }

        return true;
    }

}
