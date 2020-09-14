package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.entity.Cart;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.DIException;
import by.javatr.cafe.exception.ServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    static CartService cartService;

    @BeforeAll
    static void setUp() throws DIException {

        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        absolutePath = absolutePath + "/target/test/WEB-INF/classes/by/javatr/cafe/";
        BeanFactory.getInstance().run(absolutePath);

        cartService = (CartService) BeanFactory.getInstance().getBean("cartService");

    }

    @Test
    void addToCart() {

        Cart cart = new Cart();
        cart.setUserCart(new ArrayList<>());
        Dish dish = new Dish();

        cartService.addToCart(cart, dish);

        boolean t = cart.getUserCart().contains(dish);

        assertTrue(t);

    }

    @Test
    void deleteFromCart_Should_Return_True() {

        Cart cart = new Cart();
        cart.setUserCart(new ArrayList<>());
        Dish dish = new Dish(1);
        cart.getUserCart().add(dish);
        List<Dish> cart1 = cart.getUserCart();

        boolean t = cartService.deleteFromCart(cart, dish.getId());

        assertTrue(t);

    }


    @Test
    void deleteFromCart_Should_Return_False() {

        Cart cart = new Cart();
        cart.setUserCart(new ArrayList<>());
        Dish dish = new Dish(1);
        cart.getUserCart().add(dish);
        List<Dish> cart1 = cart.getUserCart();

        final boolean f = cartService.deleteFromCart(cart, 2);

        assertFalse(f);

    }

    @Test
    void clear() {

        Cart cart = new Cart();
        cart.setUserCart(new ArrayList<>());
        cart.getUserCart().add(new Dish(1));

        boolean t = cartService.clean(cart);

        assertTrue(t);
    }

    @Test
    void getAll() {

        Cart cart = new Cart();
        ArrayList<Dish> listCart = new ArrayList<>();
        cart.setUserCart(listCart);
        cart.getUserCart().add(new Dish(1));

        final List<Dish> all = cartService.getAll(cart);

        assertEquals(listCart, all);

    }

    @Test
    void amount() throws ServiceException {

        Cart cart = new Cart();
        cart.setUserCart(new ArrayList<>());

        cart.getUserCart().add(new Dish(1, "qwe", new BigDecimal(25)));
        cart.getUserCart().add(new Dish(1, "qwe", new BigDecimal(32)));

        final BigDecimal amount = cartService.amount(cart);

        assertEquals(new BigDecimal("57.00"), amount);

    }

}