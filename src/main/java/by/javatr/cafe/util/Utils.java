package by.javatr.cafe.util;

import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.entity.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static Map<Dish, Integer> countSameDish(List<Dish> dishes) {
        HashMap<Dish, Integer> map = new HashMap<>();

        map.put(dishes.get(0), 1);

        for (int i = 1; i < dishes.size(); i++) {
            Dish dish = dishes.get(i);
            if (map.containsKey(dishes.get(i))) {
                Integer integer = map.get(dishes.get(i));
                map.put(dishes.get(i), integer + 1);
            } else {
                map.put(dish, 1);
            }
        }

        return map;
    }

    public static BigDecimal amount(List<Dish> list){
        BigDecimal amount = new BigDecimal(0);
        for (Dish dish:list) {
            amount = amount.add(dish.getPrice());
        }
        amount = amount.setScale(6, RoundingMode.HALF_UP);

        return amount;
    }

//    public static List<Order> buildOrder(){
//
//    }

}
