package by.javatr.cafe.service;

import by.javatr.cafe.entity.Cart;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface ICartService {

    void addToCart(Cart cart, Dish dish);

    List<Dish> getAll(Cart cart);

    boolean deleteFromCart(Cart cart, int id);

    boolean clean(Cart cart);

    BigDecimal amount(Cart cart) throws ServiceException;
}
