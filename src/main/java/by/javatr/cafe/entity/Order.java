package by.javatr.cafe.entity;

import by.javatr.cafe.annotation.Ignore;
import by.javatr.cafe.constant.PaymentMethod;
import by.javatr.cafe.constant.PaymentStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Order  extends Entity implements Serializable {

    private static final long serialVersionUID = -5305625013407239807L;

    @Ignore(name="order_id")
    private int order_id;
    private Map<Dish, Integer> dishes;
    private int order_rating;
    private String order_review;
    private PaymentMethod method;
    private String time;
    private int user_id;
    private PaymentStatus status;
    private String braintree_id;
    private BigDecimal amount;
    private boolean isAvailable;

    public Order() {
    }

    public Order(PaymentMethod method, PaymentStatus status, String time, Map<Dish,Integer> dishes, int user_id, String braintree_id) {
        this.method = method;
        this.user_id = user_id;
        this.status = status;
        this.time = time;
        this.braintree_id = braintree_id;
        this.dishes = dishes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return order_id == order.order_id &&
                order_rating == order.order_rating &&
                user_id == order.user_id &&
                Objects.equals(dishes, order.dishes) &&
                Objects.equals(order_review, order.order_review) &&
                method == order.method &&
                Objects.equals(time, order.time) &&
                status == order.status &&
                Objects.equals(braintree_id, order.braintree_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, dishes, order_rating, order_review, method, time, user_id, status, braintree_id);
    }


    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", dishes=" + dishes +
                ", order_rating=" + order_rating +
                ", order_review='" + order_review + '\'' +
                ", method=" + method +
                ", time='" + time + '\'' +
                ", user_id=" + user_id +
                ", status=" + status +
                ", braintree_id='" + braintree_id + '\'' +
                '}';
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Map<Dish, Integer> getDishes() {
        return dishes;
    }

    public void setDishes(Map<Dish, Integer> dishes) {
        this.dishes = dishes;
    }

    public int getOrder_rating() {
        return order_rating;
    }

    public void setOrder_rating(int order_rating) {
        this.order_rating = order_rating;
    }

    public String getOrder_review() {
        return order_review;
    }

    public void setOrder_review(String order_review) {
        this.order_review = order_review;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBraintree_id() {
        return braintree_id;
    }

    public void setBraintree_id(String braintree_id) {
        this.braintree_id = braintree_id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
