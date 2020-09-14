package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.entity.Cart;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.ICartService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Contains methods for work with cart
 */
@Component
public class CartService implements ICartService {

    private CartService(){}


    /**
     * Add dish to cart
     * @param cart cart
     * @param dish to be added to cart
     */
    @Override
    public void addToCart(Cart cart, Dish dish) {
        List<Dish> cart1 = cart.getUserCart();
        cart1.add(dish);
    }


    /**
     * Delete dish from cart
     * @param cart cart
     * @param dishId dish id
     * @return boolean
     */
    @Override
    public boolean deleteFromCart(Cart cart, int dishId) {

        List<Dish> cart1 = cart.getUserCart();

        for (int i = 0; i < cart1.size(); i++) {
            Dish dish = cart1.get(i);
            if(dish.getId() == dishId){
                cart1.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Clean cart
     * @param cart cart
     * @return boolean
     */
    @Override
    public boolean clean(Cart cart) {
        cart.getUserCart().clear();
        return true;
    }

    /**
     * Returns all dish from cart
     * @param cart cart
     * @return list of dishes
     */
    @Override
    public List<Dish> getAll(Cart cart) {
        return cart.getUserCart();
    }

    /**
     * Count amount of dish price in cart
     * @param cart cart
     * @return amount 
     */
    @Override
    public BigDecimal amount(Cart cart) throws ServiceException {

        if (cart == null){
            throw new ServiceException("Cart is empty");
        }

        List<Dish> cart1 = cart.getUserCart();

        BigDecimal decimal = new BigDecimal(0);
        decimal = decimal.setScale(2, RoundingMode.HALF_UP);

        for (Dish dish: cart1) {
            decimal = decimal.add(dish.getPrice());
        }
        return decimal;
    }
}
