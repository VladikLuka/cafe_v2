package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.PaymentMethod;
import by.javatr.cafe.constant.PaymentStatus;
import by.javatr.cafe.dao.repository.AbstractRepository;
import by.javatr.cafe.dao.repository.IOrderRepository;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.util.Cache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.javatr.cafe.constant.DbColumns.*;

/**
 * Contains methods for working with the database.
 * Order table
 */
@Component
public class MySqlOrderRepository extends AbstractRepository<Order> implements IOrderRepository {

    private static final String CREATE_ORDER = "INSERT INTO orders (order_status, order_receipt_time,order_delivery_time, order_credit_time, order_payment_method, users_ownerId, address_address_id) values (?,?,?,?,?,?,?)";
    private static final String CREATE_ORDER_DISH = "INSERT INTO orders_dishes (orders_has_dishes_quantity, orders_order_id,dishes_dish_id) values (?,?,?)";
    private static final String GET_LAST_ID = "select LAST_INSERT_ID()";
    private static final String GET_USER_ORDER = "select * from orders_dishes left join orders on orders_order_id = order_id left join dish on dishes_dish_id = dish.dish_id where orders.users_ownerId = ?";
    private static final String GET_ALL_ORDERS = "select * from orders as o left join orders_dishes as od on od.orders_order_id = o.order_id left join dish as d on dishes_dish_id = d.dish_id left join address as a on a.address_id = o.address_address_id";

    @Autowired
    private Cache cache;

    /**
     * Returns all orders
     * @return list of orders
     */
    @Override
    public List<Order> getAll() throws DAOException {

        if (!cache.getListOrders().isEmpty()) {
            return cache.getListOrders();
        }

        List<Order> list = new ArrayList<>();
        Map<Dish, Integer> map;
        try (
                Connection connection = getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS);
                final ResultSet resultSet = preparedStatement.executeQuery();
        ){
            while(resultSet.next()){
                Order order = new Order();
                Dish dish = new Dish();
                map = new HashMap<>();

                order.setOrderId(resultSet.getInt(ORDER_ID));
                order.setStatus(PaymentStatus.valueOf(resultSet.getString(ORDER_STATUS)));
                order.setTime(resultSet.getString(ORDER_RECEIPT_TIME));
                order.setDeliveryTime(resultSet.getString(ORDER_DELIVERY_TIME));
                order.setCreditTime(resultSet.getString(ORDER_CREDIT_TIME));
                order.setMethod(PaymentMethod.valueOf(resultSet.getString(ORDER_PAYMENT_METHOD)));
                order.setOrderRating(resultSet.getInt(ORDER_RATING));
                order.setOrderReview(resultSet.getString(ORDER_REVIEW));
                if(resultSet.getBigDecimal(DISH_PRICE)!=null) {
                    order.setAmount(resultSet.getBigDecimal(DISH_PRICE).multiply(resultSet.getBigDecimal(ORDERS_DISHES_QUANTITY)));
                }
                order.setUserId(resultSet.getInt(USER_OWNER_ID));
                order.setDishes(map);
                order.setAddress(new Address(resultSet.getInt(ADDRESS_ID),resultSet.getString(ADDRESS_CITY), resultSet.getString(ADDRESS_STREET), resultSet.getString(ADDRESS_HOUSE), resultSet.getString(ADDRESS_FLAT), order.getUserId()));


                dish.setId(resultSet.getInt(DISH_ID));
                dish.setName(resultSet.getString(DISH_NAME));
                dish.setDescription(resultSet.getString(DISH_DESCRIPTION));
                dish.setPrice(resultSet.getBigDecimal(DISH_PRICE));
                dish.setAvailable(resultSet.getBoolean(DISH_IS_AVAILABLE));
                dish.setCategoryId(resultSet.getInt(CATEGORY_ID));
                dish.setWeight(resultSet.getInt(DISH_WEIGHT));
                dish.setPicturePath(resultSet.getString(DISH_PICTURE_PATH));

                map.put(dish, resultSet.getInt(ORDERS_DISHES_QUANTITY));

                list.add(order);
            }


        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }

        return list;
    }

    /**
     * Create order
     * @param order order being created
     * @return created order
     */
    @Override
    public Order createOrder(Order order) throws DAOException{
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER);
            PreparedStatement preparedStatement2 = connection.prepareStatement(GET_LAST_ID);
        ){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, order.getStatus().name());
            preparedStatement.setString(2, order.getTime());
            preparedStatement.setString(3, order.getDeliveryTime());
            preparedStatement.setString(4, order.getCreditTime());
            preparedStatement.setString(5, order.getMethod().name());
            preparedStatement.setInt(6, order.getUserId());
            if(order.getAddress() != null) {
                preparedStatement.setInt(7, order.getAddress().getId());
            }else {
                preparedStatement.setObject(7, null);
            }
            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement2.executeQuery();){
                resultSet.next();
                order.setOrderId(resultSet.getInt("last_insert_id()"));
            }
            connection.commit();
        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        cache.addOrder(order);
    return order;
    }

    /**
     * Create order in orderDish table
     * @param order order being created
     * @return created order
     */
    @Override
    public Order createOrderDish(Order order) throws DAOException{
        try(    Connection connection = getConnection();
                PreparedStatement preparedStatement1 = connection.prepareStatement((CREATE_ORDER_DISH));
        ){
            connection.setAutoCommit(false);
            if(order.getDishes() != null) {
                for (Dish dish : order.getDishes().keySet()) {
                    preparedStatement1.setInt(1, order.getDishes().get(dish));
                    preparedStatement1.setInt(2, order.getOrderId());
                    preparedStatement1.setInt(3, dish.getId());
                    preparedStatement1.addBatch();
                }
                preparedStatement1.executeBatch();
                connection.commit();
            }else throw new IllegalArgumentException("Order has no dish");
        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        return order;
    }

    /**
     * Returns user orders
     * @param user user whose orders are returned
     * @return list of orders
     */
    @Override
    public List<Order> getAll(User user) throws DAOException {
        if (!cache.getOrders(user.getId()).isEmpty()) {
            return cache.getOrders(user.getId());
        }

        List<Order> list = new ArrayList<>();
        Map<Dish, Integer> map;
        try (
                Connection connection = getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ORDER);
        ){

            preparedStatement.setInt(1, user.getId());
            try (final ResultSet resultSet = preparedStatement.executeQuery();){

                while(resultSet.next()){
                    Order order = new Order();
                    Dish dish = new Dish();
                    map = new HashMap<>();
                    dish.setId(resultSet.getInt(DISH_ID));
                    dish.setName(resultSet.getString(DISH_NAME));
                    dish.setDescription(resultSet.getString(DISH_DESCRIPTION));
                    dish.setPrice(resultSet.getBigDecimal(DISH_PRICE));
                    dish.setAvailable(resultSet.getBoolean(DISH_IS_AVAILABLE));
                    dish.setCategoryId(resultSet.getInt(CATEGORY_ID));
                    dish.setWeight(resultSet.getInt(DISH_WEIGHT));
                    dish.setPicturePath(resultSet.getString(DISH_PICTURE_PATH));

                    map.put(dish, resultSet.getInt(ORDERS_DISHES_QUANTITY));

                    order.setOrderId(resultSet.getInt(ORDERS_ORDER_ID));
                    order.setStatus(PaymentStatus.valueOf(resultSet.getString(ORDER_STATUS)));
                    order.setTime(resultSet.getString(ORDER_RECEIPT_TIME));
                    order.setDeliveryTime(resultSet.getString(ORDER_DELIVERY_TIME));
                    order.setCreditTime(resultSet.getString(ORDER_CREDIT_TIME));
                    order.setMethod(PaymentMethod.valueOf(resultSet.getString(ORDER_PAYMENT_METHOD)));
                    order.setOrderRating(resultSet.getInt(ORDER_RATING));
                    order.setOrderReview(resultSet.getString(ORDER_REVIEW));
                    if(resultSet.getBigDecimal(DISH_PRICE)!=null){
                        order.setAmount(resultSet.getBigDecimal(DISH_PRICE).multiply(resultSet.getBigDecimal(ORDERS_DISHES_QUANTITY)));
                    }
                    order.setUserId(resultSet.getInt(USER_OWNER_ID));
                    order.setDishes(map);
                    order.setAddress(new Address(resultSet.getString(ADDRESS_CITY), resultSet.getString(ADDRESS_STREET), resultSet.getString(ADDRESS_HOUSE), resultSet.getString(ADDRESS_FLAT), order.getUserId(), resultSet.getBoolean(ADDRESS_IS_AVAILABLE)));
                    list.add(order);
                }
            }

        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }

        return list;
    }


    /**
     * Update existing order
     * @param order order being updated
     * @return updated order
     */
    @Override
    public Order updateOrder(Order order) throws DAOException {
        try(Connection connection = getConnection()){
            connection.setAutoCommit(false);
            super.update(connection, order);
            connection.commit();
            cache.updateOrder(order);
        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        return order;
    }

    /**
     * Delete order
     * @param order being deleted
     * @return boolean
     */
    @Override
    public boolean delete(Order order) throws DAOException {
        try (Connection connection = getConnection()){
            connection.setAutoCommit(false);
            super.delete(connection, order);
            connection.commit();
            cache.deleteOrder(order);
        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        return true;
    }
}
