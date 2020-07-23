package by.javatr.cafe.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart extends Entity<Cart> implements Serializable {

    private static final long serialVersionUID = -778037186749978446L;

    private int id;
    private List<Dish> userCart = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Dish> getUserCart() {
        return userCart;
    }

    public void setUserCart(List<Dish> userCart) {
        this.userCart = userCart;
    }
}

