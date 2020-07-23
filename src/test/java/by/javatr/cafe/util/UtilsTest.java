package by.javatr.cafe.util;

import by.javatr.cafe.entity.Dish;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void countSameDish() {

        ArrayList<Dish> list = new ArrayList<>();
        list.add(new Dish(1));
        list.add(new Dish(1));
        list.add(new Dish(2));

        final Map<Dish, Integer> dishIntegerMap = Utils.countSame(list);

        HashMap<Dish, Integer> map = new HashMap<>();
        map.put(new Dish(1), 2);
        map.put(new Dish(2), 1);

        assertEquals(map, dishIntegerMap);

    }

    @Test
    void amount() {

        ArrayList<Dish> cart = new ArrayList<>();
        cart.add(new Dish(1, "qwe", new BigDecimal(25)));
        cart.add(new Dish(2, "qwe", new BigDecimal(25)));
        cart.add(new Dish(3, "qwe", new BigDecimal(25)));

        BigDecimal amount = Utils.amount(cart);
        amount = amount.setScale(0, RoundingMode.HALF_UP);

        assertEquals(new BigDecimal(75), amount);

    }

    @Test
    void hashPass() {

        String pass = "qwe";
        String hashed_pass = Utils.hashPass(pass);

        assertEquals("76d80224611fc919a5d54f0ff9fba446", hashed_pass);

    }
}