package by.javatr.cafe;

import by.javatr.cafe.constant.Regex;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.aspectj.annotation.Connect;
import by.javatr.cafe.aspectj.annotation.Resources;
import by.javatr.cafe.dao.repository.AbstractRepository;
import by.javatr.cafe.dao.repository.impl.MySqlUserRepository;
import by.javatr.cafe.entity.*;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;


public class Main  extends AbstractRepository<User> {


    static class Test extends AbstractRepository<User> {

        private String tes;
        private int i;

        @Override
        public User update(Connection connection, Entity<User> entity) throws DAOException {
            return super.update(connection, entity);
        }
    }


    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, DAOException, ServiceException, NoSuchFieldException, FileNotFoundException {

        String str = "+375333044914";
        System.out.println(str.matches("^\\+\\d{12}$"));

    }



    static private List<ArrayList<Integer>> countSameDish(List<Dish> dishes){
        List<ArrayList<Integer>> list = new ArrayList<>();

        for (int i = 0; i < dishes.size(); i++) {
            int id = dishes.get(i).getId();
            int counter = 0;
            for (int j = 0; j < list.size(); j++) {
                List<Integer> integers = list.get(j);
                if(integers.get(0) == id){
                    integers.add(0, id);
                    integers.add(1, integers.get(2) + 1);
                    integers.remove(3);
                    integers.remove(2);
                    counter++;
                }
            }
            if(counter == 0){
                ArrayList<Integer> ls = new ArrayList<>();
                list.add(ls);
                ls.add(0, id);
                ls.add(1, 1);
            }
        }

        return list;
    }


    static private Map<Dish, Integer> countSameDish2(List<Dish> dishes){
        HashMap<Dish, Integer> map = new HashMap<>();

        map.put(dishes.get(0), 1);

        for (int i = 1; i < dishes.size(); i++) {
            Dish dish = dishes.get(i);
            if(map.containsKey(dishes.get(i))){
                Integer integer = map.get(dishes.get(i));
                map.put(dishes.get(i), integer + 1);
            }else{
                map.put(dish, 1);
            }
        }

        return map;
    }

    public int devide(int i1, int i2){
        throw new IllegalArgumentException();
    }

    private static BeanFactory beanFactoryInit(String path) throws NoSuchMethodException, IOException, InstantiationException, URISyntaxException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        BeanFactory factory = BeanFactory.getInstance();
//        factory.instantiate(path);
//        factory.populateProperties();
        return factory;
    }

    public Dish testRet(Dish dish2){
        Dish dish = new Dish();
        dish.setId(1);
        dish.setName("DAY");
        dish.setDescription("HUYNYA");
        return dish2;
    }



    public void testAsp(){
        System.out.println("TESTING");
    }

    public int hz(){
        return 24;
    }

}

class B{


    @Resources
    public void test(){

        @Connect
        Connection connection;

        System.out.println("OK");

    }

    private String coord(String str){

        str = str.replaceAll(" ", "\n");
        System.out.println(str);

        String res = "";
        int co = 0;
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == ','){
                res +="{ lng: " + str.substring(co+1, i) + ", ";
                co = i;
            }

            if(str.charAt(i) == '\n'){
                res += " lat: " + str.substring(co+1, i) + " }," + "\n";
                co = i;
            }

        }

        System.out.println(res);


        return res;
    }

}