package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.annotation.*;
import by.javatr.cafe.dao.connection.impl.ConnectionPool;
import by.javatr.cafe.entity.Entity;
import by.javatr.cafe.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public abstract class AbstractRepository {

    private static final Logger logger = LogManager.getLogger(AbstractRepository.class);


    public static Connection getConnection(){

        try(Connection connection = ConnectionPool.CONNECTION_POOL.retrieve()) {
            return connection;
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return null;
    }

}






//package by.javatr.cafe.dao.repository.impl;
//
//        import by.javatr.cafe.annotation.*;
//        import by.javatr.cafe.dao.connection.impl.ConnectionPool;
//        import by.javatr.cafe.entity.Entity;
//        import by.javatr.cafe.entity.User;
//        import by.javatr.cafe.exception.DAOException;
//        import org.apache.logging.log4j.LogManager;
//        import org.apache.logging.log4j.Logger;
//
//        import java.lang.annotation.Annotation;
//        import java.lang.reflect.Constructor;
//        import java.lang.reflect.InvocationTargetException;
//        import java.lang.reflect.Type;
//        import java.sql.*;
//        import java.util.*;
//
//
////public class AbstractRepository<T extends Entity<T>> {
////
////    private static final Logger logger = LogManager.getLogger(AbstractRepository.class);
////    private static final String GET_LAST_ID = "select LAST_INSERT_ID()";
////
////
////    public static Connection getConnection() throws DAOException {
////
////        try {
////            return ConnectionPool.CONNECTION_POOL.retrieve();
////        } catch (SQLException throwables) {
////            logger.error(throwables);
////            throw new DAOException(throwables);
////        }
////    }
////
////
//    public T read(Entity<T> entity){
//        HashMap<Class<?>, Object> map = new HashMap<>();
//        ArrayList<Type> list = new ArrayList<>();
//        final String table;
//        int id = 0;
//        java.lang.reflect.Field joinField = null;
//        String id_name = null;
//        Connection connection = null;
//        PreparedStatement statement = null;
//        final java.lang.reflect.Field[] fields = entity.getClass().getDeclaredFields();
//        try {
//            connection = ConnectionPool.CONNECTION_POOL.retrieve();
//            if (entity.getClass().isAnnotationPresent(Table.class)) {
//                table = entity.getClass().getDeclaredAnnotation(Table.class).table();
//            } else {
//                throw new IllegalArgumentException("NO TABLE");
//            }
//            for (java.lang.reflect.Field field : fields) {
//                field.setAccessible(true);
//                if (field.isAnnotationPresent(Id.class)) {
//                    try {
//                        id_name = field.getAnnotation(Id.class).name();
//                        id = (int) field.get(entity);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if(field.isAnnotationPresent(Join.class)){
//                    joinField = field;
//                    map.put(field.getType(),field.getAnnotation(Join.class).table());
//                    final Type genericType = field.getGenericType();
//                    list.add(field.getGenericType());
//                }
//
//
//
//                field.setAccessible(false);
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
//    public List<T> readAll(Class<T> entity) throws DAOException {
//        List<T> list = new ArrayList<>();
//
//        List<Object> fie = new ArrayList<>();
//        Connection connection = null;
//        PreparedStatement statement = null;
//        try {
//            connection = ConnectionPool.CONNECTION_POOL.retrieve();
//
//            String query = "select * from " + entity.getDeclaredAnnotation(Table.class).table();
//            statement = connection.prepareStatement(query);
//            final ResultSet resultSet = statement.executeQuery();
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
//
//        return list;
//    }
//
//
//
//
//    public T update (Entity<T> entity) throws DAOException {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        String table = null;
//
//        try {
//            Map<String, Object> vars = new HashMap<>();
//
//            connection = ConnectionPool.CONNECTION_POOL.retrieve();
//
//            final Class<? extends Entity> aClass = entity.getClass();
//            final boolean annotationPresent = aClass.isAnnotationPresent(Table.class);
//            if(annotationPresent){
//                table = aClass.getAnnotation(Table.class).table();
//            }
//
//            final java.lang.reflect.Field[] declaredFields = aClass.getDeclaredFields();
//
//            String id_column_name = null;
//            int id = 0;
//
//            for (java.lang.reflect.Field declaredField : declaredFields) {
//                declaredField.setAccessible(true);
//                if (declaredField.isAnnotationPresent(Field.class) || declaredField.isAnnotationPresent(Ignore.class)) {
//
//                    if(declaredField.isAnnotationPresent(Id.class)){
//                        id_column_name = declaredField.getAnnotation(Id.class).name();
//                        id = declaredField.getInt(entity);
//                    }
//
//
//                    if(declaredField.isAnnotationPresent(Field.class)){
//                        Field declaredAnnotation = declaredField.getDeclaredAnnotation(Field.class);
//                        String name = declaredAnnotation.name();
//                        Object o = null;
//                        try {
//                            o = declaredField.get(entity);
//                        } catch (IllegalAccessException e) {
//                            throw new DAOException(e);
//                        }
//                        vars.put(name, o);
//                    }else if(declaredField.isAnnotationPresent(Ignore.class)){
//                        final Ignore declaredAnnotation1 = declaredField.getDeclaredAnnotation(Ignore.class);
//                        String name = declaredAnnotation1.name();
//                        Object o = null;
//                        try {
//                            o = declaredField.get(entity);
//                        } catch (IllegalAccessException e) {
//                            throw new DAOException(e);
//                        }
//                        vars.put(name, o);
//                    }
//
//                }
//                declaredField.setAccessible(false);
//            }
//
//            System.out.println(vars);
//            StringBuilder str = new StringBuilder();
//
//            final Set<String> keys = vars.keySet();
//
//            for (String key : keys) {
//                final Object o = vars.get(key);
//                if(o.getClass().equals(String.class)){
//                    str.append(key).append("=").append("\"").append(o).append("\"").append(",");
//                }else {
//                    str.append(key).append("=").append(o).append(",");
//                }
//            }
//
//
//            str.delete(str.length() - 1, str.length());
//
//            String query = "update " + table + " set " + str + " where " + id_column_name + " = ?" ;
//            System.out.println(query);
//
//            statement = connection.prepareStatement(query);
//            statement.setInt(1, id);
//
//            statement.executeUpdate();
//
//        }catch (SQLException | IllegalAccessException e){
//            throw new DAOException(e);
//        }finally {
//            try {
////                statement.close();
//                connection.close();
//            } catch (SQLException throwables) {
//                logger.error(throwables);
//                throw new DAOException(throwables);
//            }
//        }
//
//        return (T) entity;
//    }
//
//
//    public T create(Entity<T> entity) throws DAOException {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        try {
//            connection = ConnectionPool.CONNECTION_POOL.retrieve();
//            Map<String, Object> vars = new HashMap<>();
//
//            String table = null;
//            System.out.println(entity.getClass());
//            final Class<? extends Entity> entityClass = entity.getClass();
//
//            if (entityClass.isAnnotationPresent(Table.class)) {
//                Table declaredAnnotation = entityClass.getDeclaredAnnotation(Table.class);
//                table = declaredAnnotation.table();
//                System.out.println(table);
//            }
//
//            java.lang.reflect.Field[] declaredFields = entityClass.getDeclaredFields();
//
//            for (java.lang.reflect.Field declaredField : declaredFields) {
//                declaredField.setAccessible(true);
//                if (declaredField.isAnnotationPresent(Field.class)) {
//                    Field declaredAnnotation = declaredField.getDeclaredAnnotation(Field.class);
//                    String name = declaredAnnotation.name();
//                    Class<?> type = declaredField.getType();
//                    Object o = null;
//                    try {
//                        o = declaredField.get(entity);
//                    } catch (IllegalAccessException e) {
//                        throw new DAOException(e);
//                    }
//                    vars.put(name, o);
//                }
//                declaredField.setAccessible(false);
//            }
//
//            Set<String> keys = vars.keySet();
//            StringBuilder str = new StringBuilder();
//
//            for (String key : keys) {
//                str.append(key).append(",");
//            }
//
//            str.delete(str.length() - 1, str.length());
//
//            //values
//            StringBuilder str2 = new StringBuilder();
//            StringBuilder countMark = new StringBuilder();
//            for (String key : keys) {
//                countMark.append("?").append(",");
//                Object o = vars.get(key);
//                if (Objects.nonNull(o) && o.getClass().equals(String.class)) {
//                    str2.append("\"").append(o).append("\"").append(",");
//                } else {
//                    str2.append(o).append(",");
//                }
//            }
//
//            str2.delete(str2.length() - 1, str2.length());
//            countMark.delete(countMark.length() - 1, countMark.length());
//
//            String query = "insert into " + table + "(" + str + ")" + "values(" + countMark + ")";
//            System.out.println(query);
//
//
//            try {
//                statement = connection.prepareStatement(query);
//            } catch (SQLException throwables) {
//                throw new DAOException("SQL troubles", throwables);
//            }
//
//            int count = 0;
//
//            try {
//                for (String key : keys) {
//                    count++;
//                    Object o = vars.get(key);
//                    statement.setObject(count, o);
//                    if (Objects.nonNull(o) && o.getClass().equals(String.class)) {
//                        str2.append("\"").append(o).append("\"").append(",");
//                    } else {
//                        str2.append(o).append(",");
//                    }
//                }
//                statement.executeUpdate();
//
//                for (java.lang.reflect.Field field :declaredFields) {
//                    if(field.isAnnotationPresent(Id.class)){
//                        field.setAccessible(true);
//                        final ResultSet resultSet = connection.prepareStatement(GET_LAST_ID).executeQuery();
//                        resultSet.next();
//                        field.set(entity, resultSet.getInt(1));
//                        field.setAccessible(false);
//                    }
//                }
//
//            } catch (SQLException e) {
//                logger.error("SQL EX", e);
//                throw new DAOException(e);
//            }
//        }catch (SQLException e){
//            logger.error("SQL EX", e);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                statement.close();
//                connection.close();
//            } catch (SQLException throwables) {
//                logger.error(throwables);
//                throw new DAOException(throwables);
//            }
//        }
//        return (T) entity;
//    }
//
//
//    public boolean delete(Entity<T> entity){
//        Connection connection = null;
//        PreparedStatement statement = null;
//        String table = null;
//        try {
//            connection = ConnectionPool.CONNECTION_POOL.retrieve();
//
//            if(entity.getClass().isAnnotationPresent(Table.class)){
//                table = entity.getClass().getAnnotation(Table.class).table();
//            }else{
//                throw new IllegalArgumentException("NO TABLE");
//            }
//
//
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return true;
//    }
//
//}
