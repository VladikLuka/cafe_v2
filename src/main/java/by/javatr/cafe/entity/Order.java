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
    private int orderId;
//    @Join(fieldColumn = "order_id")
//    @ManyToMany(table = "orders_dishes", joinName = "orders_order_id", type = Dish.class)
    private Map<Dish, Integer> dishes;
    @Field(name="order_rating")
    private int orderRating;
    @Field(name="order_review")
    private String orderReview;
    @Field(name="order_payment_method")
    private PaymentMethod method;
    @Field(name="order_receipt_time")
    private String time;
    @Field(name = "order_delivery_time")
    private String deliveryTime;
    @Field(name = "order_credit_time")
    private String creditTime;
    @Field(name="users_ownerId")
    private int userId;
    @Field(name="order_status")
    private PaymentStatus status;
    private BigDecimal amount;
    @Join(fieldColumn = "address_address_id")
    @OneToMany(joinName = "address_id", field = "id",type = Address.class)
    private Address address;

    public Order() {
    }

    public Order(PaymentMethod method, PaymentStatus status, String time,String deliveryTime ,Map<Dish,Integer> dishes,BigDecimal amount,int userId) {
        this.method = method;
        this.userId = userId;
        this.status = status;
        this.time = time;
        this.deliveryTime = deliveryTime;
        this.dishes = dishes;
        this.amount = amount;
    }

    public Order(PaymentMethod method, PaymentStatus status, String time,String deliveryTime ,Map<Dish,Integer> dishes, Address address, BigDecimal amount,int userId) {
        this.method = method;
        this.userId = userId;
        this.status = status;
        this.time = time;
        this.deliveryTime = deliveryTime;
        this.dishes = dishes;
        this.address = address;
        this.amount = amount;
    }

    public Order(PaymentMethod method, PaymentStatus status, String time, String deliveryTime,Address address,BigDecimal amount, int userId) {
        this.method = method;
        this.status = status;
        this.time = time;
        this.deliveryTime = deliveryTime;
        this.address = address;
        this.amount = amount;
        this.userId = userId;

    }
    public Order(PaymentMethod method, PaymentStatus status, String time,String deliveryTime , BigDecimal amount, int userId) {
        this.method = method;
        this.status = status;
        this.time = time;
        this.deliveryTime = deliveryTime;
        this.amount = amount;
        this.userId = userId;
    }

    public Order(int orderId) {
        this.orderId = orderId;
    }

    public Order(int orderId, int userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public Order(PaymentMethod method, Map<Dish, Integer> dishes, int userId, String createTime, String deliveryTime, Address userAddress, BigDecimal amount, PaymentStatus status) {
        this.method = method;
        this.dishes = dishes;
        this.userId = userId;
        this.time = createTime;
        this.deliveryTime = deliveryTime;
        this.address = userAddress;
        this.amount = amount;
        this.status = status;
    }

    public Order(PaymentMethod method, Map<Dish, Integer> dishes, String createTime, String creditTime, Address userAddress, BigDecimal amount, PaymentStatus status, int userId) {
        this.method = method;
        this.dishes = dishes;
        this.userId = userId;
        this.time = createTime;
        this.creditTime = creditTime;
        this.address = userAddress;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                orderRating == order.orderRating &&
                userId == order.userId &&
                Objects.equals(dishes, order.dishes) &&
                Objects.equals(orderReview, order.orderReview) &&
                method == order.method &&
                Objects.equals(time, order.time) &&
                Objects.equals(deliveryTime, order.deliveryTime) &&
                Objects.equals(creditTime, order.creditTime) &&
                status == order.status &&
                Objects.equals(amount, order.amount) &&
                Objects.equals(address, order.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, dishes, orderRating, orderReview, method, time, deliveryTime, creditTime, userId, status, amount, address);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", dishes=" + dishes +
                ", orderRating=" + orderRating +
                ", orderReview='" + orderReview + '\'' +
                ", method=" + method +
                ", time='" + time + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", creditTime='" + creditTime + '\'' +
                ", userId=" + userId +
                ", status=" + status +
                ", amount=" + amount +
                ", address=" + address +
                '}';
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Map<Dish, Integer> getDishes() {
        return dishes;
    }

    public void setDishes(Map<Dish, Integer> dishes) {
        this.dishes = dishes;
    }

    public int getOrderRating() {
        return orderRating;
    }

    public void setOrderRating(int orderRating) {
        this.orderRating = orderRating;
    }

    public String getOrderReview() {
        return orderReview;
    }

    public void setOrderReview(String orderReview) {
        this.orderReview = orderReview;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getCreditTime() {
        return creditTime;
    }

    public void setCreditTime(String creditTime) {
        this.creditTime = creditTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
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
}
