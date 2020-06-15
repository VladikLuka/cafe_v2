package by.javatr.cafe.service;

import by.javatr.cafe.entity.Cart;
import by.javatr.cafe.entity.Dish;

import java.util.List;

public interface ICartService {

    void addToCart(Cart cart, Dish dish);

    List<Dish> getAll(Cart cart);

    boolean deleteFromCart(Cart cart, int id);

    boolean clear(Cart cart);
}
