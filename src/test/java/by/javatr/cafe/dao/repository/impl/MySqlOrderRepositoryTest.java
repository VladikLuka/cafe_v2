package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.constant.PaymentMethod;
import by.javatr.cafe.constant.PaymentStatus;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.DIException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MySqlOrderRepositoryTest {

    static MySqlOrderRepository orderRepository;
    static Connection connection;

    @BeforeAll
    static void setUp() throws DIException, SQLException, DAOException {

        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        absolutePath = absolutePath + "/target/test/WEB-INF/classes/by/javatr/cafe/";
        BeanFactory.getInstance().run(absolutePath);

        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/epam-cafe-test?useUnicode=true&serverTimezone=UTC&useSSL=false",
                "root",
                "vladislav7890");

        orderRepository = new MySqlOrderRepository(connection);

        orderRepository.createOrder(new Order(PaymentMethod.CARD, PaymentStatus.CLOSED, "2020-09-24 12:12:12", "2020-09-25 12:12:12", new HashMap<Dish, Integer>(), new BigDecimal(12), 1));
        orderRepository.createOrder(new Order(PaymentMethod.CARD, PaymentStatus.CLOSED, "2020-09-24 12:12:12", "2020-09-25 12:12:12", new HashMap<Dish, Integer>(), new BigDecimal(12), 1));


    }

    @AfterAll
    static void tearDown() throws SQLException {

        connection.prepareStatement("truncate orders").execute();
        connection.prepareStatement("truncate orders_dishes").execute();

        connection.close();

    }

    @Test
    void getAll() throws DAOException {

        final List<Order> all = orderRepository.getAll();

        assertEquals(2, all.size());

    }

    @Test
    void createOrder() throws DAOException, SQLException {

        Order order = new Order(PaymentMethod.CARD, PaymentStatus.CLOSED, "2020-09-24 15:15:15", "2020-09-25 12:12:12", new HashMap<Dish, Integer>(), new BigDecimal(12), 1);

        final Order order1 = orderRepository.createOrder(order);

        PreparedStatement statement = connection.prepareStatement("select * from orders where order_id = ?");

        statement.setInt(1, order1.getOrderId());

        final ResultSet resultSet = statement.executeQuery();

        resultSet.next();

        assertEquals(resultSet.getString("order_receipt_time"), "2020-09-24 15:15:15");

        orderRepository.delete(order1);
    }

    @Test
    void createOrderDish() throws DAOException, SQLException {

        Map<Dish, Integer> map = new HashMap<>();

        map.put(new Dish(1), 2);

        final Order order = orderRepository.createOrder(new Order(PaymentMethod.CARD, PaymentStatus.CLOSED, "2020-09-24 15:15:15", "2020-09-25 12:12:12", map, new BigDecimal(12), 1));
        orderRepository.createOrderDish(order);

        PreparedStatement statement = connection.prepareStatement("select * from orders_dishes where orders_order_id = ?");

        statement.setInt(1, order.getOrderId());

        final ResultSet resultSet = statement.executeQuery();

        resultSet.next();

        final int dishes_dish_id = resultSet.getInt("dishes_dish_id");

        assertEquals(1, dishes_dish_id);


    }


    @Test
    void updateOrder() throws DAOException, SQLException {

        Order order = new Order(PaymentMethod.CARD, PaymentStatus.CLOSED, "2020-09-24 12:12:12", "2020-09-25 13:13:13", new HashMap<Dish, Integer>(), new BigDecimal(12), 1);
        order.setOrderId(1);

        order = orderRepository.updateOrder(order);

        assertEquals("2020-09-25 13:13:13", order.getDeliveryTime());


    }

    @Test
    void delete() throws DAOException {



        Order order = new Order(PaymentMethod.CARD, PaymentStatus.CLOSED, "2020-09-24 12:12:12", "2020-09-25 13:13:13", new HashMap<Dish, Integer>(), new BigDecimal(12), 1);
        order.setOrderId(4);

        final boolean delete = orderRepository.delete(order);

        assertTrue(delete);

    }
}