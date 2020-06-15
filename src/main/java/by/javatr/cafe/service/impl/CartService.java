package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.entity.Cart;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.service.ICartService;

import java.util.List;

@Component
public class CartService implements ICartService {

    private CartService(){}

    @Override
    public void addToCart(Cart cart, Dish dish) {
        List<Dish> cart1 = cart.getCart();
        cart1.add(dish);
    }


    @Override
    public boolean deleteFromCart(Cart cart, int id) {

        List<Dish> cart1 = cart.getCart();

        for (int i = 0; i < cart1.size(); i++) {
            Dish dish = cart1.get(i);
            if(dish.getId() == id){
                cart1.remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean clear(Cart cart) {

        cart.getCart().clear();
        return true;
    }

    @Override
    public List<Dish> getAll(Cart cart) {
        return cart.getCart();
    }

}
