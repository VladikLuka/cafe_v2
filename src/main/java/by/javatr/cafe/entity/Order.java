package by.javatr.cafe.entity;

import by.javatr.cafe.dao.annotation.*;
import by.javatr.cafe.constant.PaymentMethod;
import by.javatr.cafe.constant.PaymentStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Table(table = "orders")
public class Order  extends Entity<Order> implements Serializable {

    private static final long serialVersionUID = -5305625013407239807L;

    @Id(name="order_id")
    private int order_id;
//    @Join(fieldColumn = "order_id")
//    @ManyToMany(table = "orders_dishes", joinName = "orders_order_id", type = Dish.class)
    private Map<Dish, Integer> dishes;
    @Field(name="order_rating")
    private int order_rating;
    @Field(name="order_review")
    private String order_review;
    @Field(name="order_payment_method")
    private PaymentMethod method;
    @Field(name="order_receipt_time")
    private String time;
    @Field(name = "order_delivery_time")
    private String delivery_time;
    @Field(name="users_ownerId")
    private int user_id;
    @Field(name="order_status")
    private PaymentStatus status;
    private BigDecimal amount;
    @Join(fieldColumn = "address_address_id")
    @ManyToOne(joinName = "address_id", field = "id",type = Address.class)
    private Address address;
    private boolean isAvailable;

    public Order() {
    }

    public Order(PaymentMethod method, PaymentStatus status, String time,String delivery_time ,Map<Dish,Integer> dishes,BigDecimal amount,int user_id) {
        this.method = method;
        this.user_id = user_id;
        this.status = status;
        this.time = time;
        this.delivery_time = delivery_time;
        this.dishes = dishes;
        this.amount = amount;
    }

    public Order(PaymentMethod method, PaymentStatus status, String time,String delivery_time ,Map<Dish,Integer> dishes, Address address, BigDecimal amount,int user_id) {
        this.method = method;
        this.user_id = user_id;
        this.status = status;
        this.time = time;
        this.delivery_time = delivery_time;
        this.dishes = dishes;
        this.address = address;
        this.amount = amount;
    }

    public Order(PaymentMethod method, PaymentStatus status, String time, String delivery_time,Address address,BigDecimal amount, int user_id) {
        this.method = method;
        this.status = status;
        this.time = time;
        this.delivery_time = delivery_time;
        this.address = address;
        this.amount = amount;
        this.user_id = user_id;

    }
    public Order(PaymentMethod method, PaymentStatus status, String time,String delivery_time , BigDecimal amount, int user_id) {
        this.method = method;
        this.status = status;
        this.time = time;
        this.delivery_time = delivery_time;
        this.amount = amount;
        this.user_id = user_id;
    }

    public Order(int order_id) {
        this.order_id = order_id;
    }

    public Order(int order_id, int user_id) {
        this.order_id = order_id;
        this.user_id = user_id;
    }

    public Order(PaymentMethod method, Map<Dish, Integer> dishes, int user_id, String create_time, String delivery_time, Address user_address, BigDecimal amount, PaymentStatus status) {
        this.method = method;
        this.dishes = dishes;
        this.user_id = user_id;
        this.time = create_time;
        this.delivery_time = delivery_time;
        this.address = user_address;
        this.amount = amount;
        this.status = status;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return order_id == order.order_id &&
                order_rating == order.order_rating &&
                user_id == order.user_id &&
                isAvailable == order.isAvailable &&
                Objects.equals(dishes, order.dishes) &&
                Objects.equals(order_review, order.order_review) &&
                method == order.method &&
                Objects.equals(time, order.time) &&
                Objects.equals(delivery_time, order.delivery_time) &&
                status == order.status &&
                Objects.equals(amount, order.amount) &&
                Objects.equals(address, order.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, dishes, order_rating, order_review, method, time, delivery_time, user_id, status, amount, address, isAvailable);
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
                ", delivery_time='" + delivery_time + '\'' +
                ", user_id=" + user_id +
                ", status=" + status +
                ", amount=" + amount +
                ", address=" + address +
                ", isAvailable=" + isAvailable +
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }
}
