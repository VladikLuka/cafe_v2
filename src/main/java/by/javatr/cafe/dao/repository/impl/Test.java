package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.annotation.Field;
import by.javatr.cafe.annotation.Table;
import by.javatr.cafe.entity.Entity;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Test {

    public static void create(Entity entity) throws IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test?useUnicode=true&serverTimezone=UTC&useSSL=false", "root", "vladislav7890");


        Map<String, Object> vars = new HashMap<>();

        String table = null;
        System.out.println(entity.getClass());
        Class<? extends Entity> entityClass = entity.getClass();

        if(entityClass.isAnnotationPresent(Table.class)){
            Table declaredAnnotation = entityClass.getDeclaredAnnotation(Table.class);
            table = declaredAnnotation.table();
            System.out.println(table);
        }

        java.lang.reflect.Field[] declaredFields = entityClass.getDeclaredFields();

//        for (int i = 0; i < declaredFields.length; i++) {
//            if(declaredFields[i].isAnnotationPresent(Field.class)){
//                String name = declaredFields[i].getName();
//                Class<?> type = declaredFields[i].getType();
//                Object o = declaredFields[i].get(entity);
//                vars.put(name, o);
//            }
//        }

        for (int i = 0; i < declaredFields.length; i++) {
            if(declaredFields[i].isAnnotationPresent(Field.class)){
                Field declaredAnnotation = declaredFields[i].getDeclaredAnnotation(Field.class);
                String name = declaredAnnotation.name();
                Class<?> type = declaredFields[i].getType();
                Object o = declaredFields[i].get(entity);
                vars.put(name, o);
            }
        }

        Set<String> keys = vars.keySet();
        StringBuilder str = new StringBuilder();

        for (String key:keys) {
            str.append(key).append(",");
        }

        str.delete(str.length()-1, str.length());

        //values
        StringBuilder str2 = new StringBuilder();
        StringBuilder countMark = new StringBuilder();
        for (String key:keys) {
            countMark.append("?").append(",");
            Object o = vars.get(key);
            if(Objects.nonNull(o) && o.getClass().equals(String.class)){
                str2.append("\"").append(o).append("\"").append(",");
            }else {
                str2.append(o).append(",");
            }
        }

        str2.delete(str2.length()-1, str2.length());
        countMark.delete(countMark.length()-1, countMark.length());

        String query = "insert into " + table + "(" + str + ")" + "values(" + countMark + ")";
        System.out.println(query);

        PreparedStatement statement = connection.prepareStatement(query);

        int count = 0;
        for (String key:keys) {
            count++;
            Object o = vars.get(key);
            statement.setObject(count, o);
            if(Objects.nonNull(o) && o.getClass().equals(String.class)){
                str2.append("\"").append(o).append("\"").append(",");
            }else {
                str2.append(o).append(",");
            }
        }

        statement.executeUpdate();
    }



}
