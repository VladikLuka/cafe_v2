package by.javatr.cafe.util;

import by.javatr.cafe.entity.Dish;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains util methods
 */
public class Utils {

    private static final Logger logger = LogManager.getLogger(Utils.class);

    /**
     * Count same object in list
     * @param list list of object
     * @return map where key is object and value is number of objects
     */
    public static Map<Dish, Integer> countSame(List<Dish> list) {
        HashMap<Dish, Integer> map = new HashMap<>();

        map.put(list.get(0), 1);

        for (int i = 1; i < list.size(); i++) {
            Dish dishes = list.get(i);
            if (map.containsKey(list.get(i))) {
                Integer integer = map.get(list.get(i));
                map.put(list.get(i), integer + 1);
            } else {
                map.put(dishes, 1);
            }
        }

        return map;
    }

    /**
     * Count summary price of cart
     * @param list dishes
     * @return amount
     */
    public static BigDecimal amount(List<Dish> list){
        BigDecimal amount = new BigDecimal(0);
        for (Dish dish:list) {
            amount = amount.add(dish.getPrice());
        }
        amount = amount.setScale(6, RoundingMode.HALF_UP);

        return amount;
    }

    /**
     * Hash string using MD5
     * @param password to be hashed
     * @return hashed string
     */
    public static String hashPass(String password){

        StringBuilder sb = null;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(password.getBytes());

            sb = new StringBuilder();

            for (byte b : bytes) {
                sb.append(String.format("%02x",b));
            }

        } catch (NoSuchAlgorithmException e) {
            logger.error("Invalid hash password", e);
        }

        return sb.toString();
    }

    private Utils() {}
}
