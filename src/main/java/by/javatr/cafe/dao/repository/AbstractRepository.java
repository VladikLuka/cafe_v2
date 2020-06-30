package by.javatr.cafe.dao.repository;

import by.javatr.cafe.dao.annotation.*;
import by.javatr.cafe.dao.connection.impl.ConnectionPool;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.entity.Entity;
import by.javatr.cafe.exception.DAOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AbstractRepository<T extends Entity<T>> {

    private static final String GET_LAST_ID = "select LAST_INSERT_ID()";


    public static Connection getConnection() throws DAOException {

        try {
            return ConnectionPool.CONNECTION_POOL.retrieve();
        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
    }


//    public T read(Connection connection, Class<?> entity, int id){
//        HashMap<String, Object> map = new HashMap<>();
//        ArrayList<Type> list = new ArrayList<>();
//        final String table;
//        java.lang.reflect.Field joinField = null;
//        String id_name = null;
//        PreparedStatement statement = null;
//        final java.lang.reflect.Field[] fields = entity.getClass().getDeclaredFields();
//        try {
//            if (entity.getClass().isAnnotationPresent(Table.class)) {
//                table = entity.getClass().getDeclaredAnnotation(Table.class).table();
//            } else {
//                throw new IllegalArgumentException("NO TABLE");
//            }
//            for (java.lang.reflect.Field field : fields) {
//                if(field.isAnnotationPresent(Field.class)){
//                    final Field annotation = field.getAnnotation(Field.class);
//                    final String name = annotation.name();
//
//                }
//            }
//
//            String query;
//
//            if(map.size()>0){
//                query = "select * from " + table + " join "
//                        + list.get(0).getClass().getDeclaredAnnotation(Table.class).table() + " on "
//                        +  joinField.getAnnotation(Join.class).table() + "."+ joinField.getAnnotation(Join.class).key()
//                        + "=" + table + "." + joinField.getAnnotation(Join.class).thisKey();
//                System.out.println(query);
//            }
//            else{
//                query = "select * from " + table + " where " + id_name + " = ?";
//            }statement = connection.prepareStatement(query);
//            statement.setInt(1, id);
//            final ResultSet resultSet = statement.executeQuery();
//
//            if(Objects.nonNull(resultSet)) {
//                resultSet.next();
//                final int columnCount = resultSet.getMetaData().getColumnCount();
//                for (int i = 0; i < columnCount; i++) {
//                    fields[i].setAccessible(true);
//                    if(fields[i].isAnnotationPresent(Field.class)){
//                        final String name = fields[i].getAnnotation(Field.class).name();
//                        final Object object = resultSet.getObject(name);
//                        fields[i].set(entity, object);
//                    }
//                    if(fields[i].isAnnotationPresent(Ignore.class)){
//                        final String name = fields[i].getAnnotation(Ignore.class).name();
//                        final Object object = resultSet.getObject(name);
//                        fields[i].set(entity, object);
//                    }
//                    fields[i].setAccessible(false);
//                }
//            }
//
//            return (T) entity;
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public List<T> readAll(Connection connection, Class<T> entity) throws DAOException {
//        List<T> list = new ArrayList<>();
//
//        List<Object> fie = new ArrayList<>();
//        PreparedStatement statement = null;
//        try {
//            connection = ConnectionPool.CONNECTION_POOL.retrieve();
//
//        String query = "select * from " + entity.getDeclaredAnnotation(Table.class).table();
//        statement = connection.prepareStatement(query);
//        final ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()){
//                final java.lang.reflect.Field[] declaredFields = entity.getDeclaredFields();
//                int counter = 0;
//                for (java.lang.reflect.Field field: declaredFields) {
//                    counter++;
//                    if(field.isAnnotationPresent(Field.class) || field.isAnnotationPresent(Ignore.class)){
//                        final Field annotation = field.getAnnotation(Field.class);
//                        if(Objects.nonNull(annotation)){
//                            final String name = annotation.name();
//                            final Object object = resultSet.getObject(name);
//                            fie.add(object);
//                        }else {
//                            final String name = field.getAnnotation(Ignore.class).name();
//                            final Object object = resultSet.getObject(name);
//                            fie.add(object);
//                        }
//
//                    }
//                }
//
//                final T t = entity.getDeclaredConstructor().newInstance();
//                System.out.println(t);
//
//
//                list.add(t);
//            }
//        }catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
//            throw new DAOException(e);
//        }finally {
//            try {
//                statement.close();
//                connection.close();
//            } catch (SQLException throwables) {
//                throw new DAOException(throwables);
//            }
//        }
////        return list;
//    return null;
//    }


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
                            Object o = declaredField.get(entity);

                            if (o != null) {
                                field = field.substring(0, 1).toUpperCase() + field.substring(1);
                                Method getter = o.getClass().getDeclaredMethod("get" + field);
                                Object primaryKey = getter.invoke(o);
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

            System.out.println(vars);
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
        }finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

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
