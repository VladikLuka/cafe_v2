package by.javatr.cafe.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart extends Entity implements Serializable {

    private static final long serialVersionUID = -778037186749978446L;

    int id;

    List<Dish> cart = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Dish> getCart() {
        return cart;
    }

    public void setCart(List<Dish> cart) {
        this.cart = cart;
    }
}

