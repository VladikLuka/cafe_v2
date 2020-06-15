package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.aspectj.annotation.Connect;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.PaymentMethod;
import by.javatr.cafe.constant.PaymentStatus;
import by.javatr.cafe.dao.connection.impl.ConnectionPool;
import by.javatr.cafe.dao.repository.IOrderRepository;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.javatr.cafe.constant.BD_Columns.*;

@Component
public class MySqlOrderRepository extends AbstractRepository implements IOrderRepository {

    private static final String CREATE_ORDER = "INSERT INTO orders (order_status, order_receipt_time, order_payment_method, users_ownerId) values (?,?,?,?)";
    private static final String CREATE_ORDER_DISH = "INSERT INTO orders_dishes (orders_has_dishes_quantity, orders_order_id,dishes_dish_id, braintree_order_id) values (?,?,?,?)";
    private static final String GET_LAST_ID = "select LAST_INSERT_ID()";
    private static final String GET_USER_ORDER = "select * from orders_dishes left join orders on orders_order_id = order_id left join dish on dishes_dish_id = dish.dish_id where orders.users_ownerId = ?";
    private static final String GET_ALL_ORDERS = "select * from orders_dishes left join orders on orders_order_id = order_id left join dish on dishes_dish_id = dish.dish_id";
    private static final String UPDATE_BALANCE = "UPDATE user SET user_money = ? where user_id = ?;";

    @Override
    public List<Order> getAll() throws DAOException {
        List<Order> list = new ArrayList<>();
        Map<Dish, Integer> map;
        try (
                Connection connection = getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS);
        ){
            final ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Order order = new Order();
                Dish dish = new Dish();
                map = new HashMap<>();
                dish.setId(resultSet.getInt(DISH_ID));
                dish.setName(resultSet.getString(DISH_NAME));
                dish.setDescription(resultSet.getString(DISH_DESCRIPTION));
                dish.setPrice(resultSet.getBigDecimal(DISH_PRICE));
                dish.setAvailable(resultSet.getBoolean(DISH_IS_AVAILABLE));
                dish.setCategory_id(resultSet.getInt(CATEGORY_ID));
                dish.setWeight(resultSet.getInt(DISH_WEIGHT));
                dish.setPicture_path(resultSet.getString(DISH_PICTURE_PATH));

                map.put(dish, resultSet.getInt(ORDERS_DISHES_QUANTITY));

                order.setOrder_id(resultSet.getInt(ORDERS_ORDER_ID));
                order.setStatus(PaymentStatus.valueOf(resultSet.getString(ORDER_STATUS)));
                order.setTime(resultSet.getString(ORDER_RECEIPT_TIME));
                order.setMethod(PaymentMethod.valueOf(resultSet.getString(ORDER_PAYMENT_METHOD)));
                order.setOrder_rating(resultSet.getInt(ORDER_RATING));
                order.setOrder_review(resultSet.getString(ORDER_REVIEW));
                order.setOrder_review(resultSet.getString(ORDER_REVIEW));
                order.setUser_id(resultSet.getInt(USER_OWNER_ID));
                order.setBraintree_id(resultSet.getString(BRAINTREE_ORDER_ID));
                order.setDishes(map);
                list.add(order);
            }


        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }

        return list;
    }

    @Override
    public Order createOrder(Order order) throws DAOException {

        try(
                Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER);
                PreparedStatement preparedStatement1 = connection.prepareStatement((CREATE_ORDER_DISH));
                PreparedStatement preparedStatement2 = connection.prepareStatement(GET_LAST_ID);
        )
        {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, order.getStatus().name());
            preparedStatement.setString(2, order.getTime());
            preparedStatement.setString(3, order.getMethod().name());
            preparedStatement.setInt(4, order.getUser_id());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement2.executeQuery();
            resultSet.next();
            order.setOrder_id(resultSet.getInt("last_insert_id()"));

            for (Dish dish : order.getDishes().keySet()) {
                preparedStatement1.setInt(1, order.getDishes().get(dish));
                preparedStatement1.setInt(2, order.getOrder_id());
                preparedStatement1.setInt(3, dish.getId());
                preparedStatement1.setString(4, order.getBraintree_id());
                preparedStatement1.addBatch();
            }

            preparedStatement1.executeBatch();
            connection.setAutoCommit(true);
        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }

        return order;
    }

    @Override
    public List<Order> getAll(User user) throws DAOException {
        List<Order> list = new ArrayList<>();
        Map<Dish, Integer> map;
        try (
                Connection connection = getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ORDER);
        ){
            preparedStatement.setInt(1, user.getId());
            final ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Order order = new Order();
                Dish dish = new Dish();
                map = new HashMap<>();
                dish.setId(resultSet.getInt(DISH_ID));
                dish.setName(resultSet.getString(DISH_NAME));
                dish.setDescription(resultSet.getString(DISH_DESCRIPTION));
                dish.setPrice(resultSet.getBigDecimal(DISH_PRICE));
                dish.setAvailable(resultSet.getBoolean(DISH_IS_AVAILABLE));
                dish.setCategory_id(resultSet.getInt(CATEGORY_ID));
                dish.setWeight(resultSet.getInt(DISH_WEIGHT));
                dish.setPicture_path(resultSet.getString(DISH_PICTURE_PATH));

                map.put(dish, resultSet.getInt(ORDERS_DISHES_QUANTITY));

                order.setOrder_id(resultSet.getInt(ORDERS_ORDER_ID));
                order.setStatus(PaymentStatus.valueOf(resultSet.getString(ORDER_STATUS)));
                order.setTime(resultSet.getString(ORDER_RECEIPT_TIME));
                order.setMethod(PaymentMethod.valueOf(resultSet.getString(ORDER_PAYMENT_METHOD)));
                order.setOrder_rating(resultSet.getInt(ORDER_RATING));
                order.setOrder_review(resultSet.getString(ORDER_REVIEW));
                order.setOrder_review(resultSet.getString(ORDER_REVIEW));
                order.setUser_id(resultSet.getInt(USER_OWNER_ID));
                order.setBraintree_id(resultSet.getString(BRAINTREE_ORDER_ID));
                order.setDishes(map);
                list.add(order);
            }


        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }

        return list;
    }


    @Override
    public Order createOrderBalance(Order order) throws DAOException {


        try(Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER);
            PreparedStatement preparedStatement1 = connection.prepareStatement((CREATE_ORDER_DISH));
            PreparedStatement preparedStatement2 = connection.prepareStatement(GET_LAST_ID);
            final PreparedStatement preparedStatement4 = connection.prepareStatement(UPDATE_BALANCE);
            ){
            connection.setAutoCommit(false);
            preparedStatement4.setBigDecimal(1,order.getAmount());
            preparedStatement4.setInt(2,order.getUser_id());
            preparedStatement4.executeUpdate();

            preparedStatement.setString(1, order.getStatus().name());
            preparedStatement.setString(2, order.getTime());
            preparedStatement.setString(3, order.getMethod().name());
            preparedStatement.setInt(4, order.getUser_id());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement2.executeQuery();
            resultSet.next();
            order.setOrder_id(resultSet.getInt("last_insert_id()"));

            for (Dish dish : order.getDishes().keySet()) {
                preparedStatement1.setInt(1, order.getDishes().get(dish));
                preparedStatement1.setInt(2, order.getOrder_id());
                preparedStatement1.setInt(3, dish.getId());
                preparedStatement1.setString(4, order.getBraintree_id());
                preparedStatement1.addBatch();
            }

            preparedStatement1.executeBatch();

            connection.commit();
        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }


        return order;
    }
}
