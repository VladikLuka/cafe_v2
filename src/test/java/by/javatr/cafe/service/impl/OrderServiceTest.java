package by.javatr.cafe.service.impl;

import by.javatr.cafe.constant.PaymentStatus;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.dao.repository.IOrderRepository;
import by.javatr.cafe.dao.repository.IUserRepository;
import by.javatr.cafe.dao.repository.impl.MySqlOrderRepository;
import by.javatr.cafe.dao.repository.impl.MySqlUserRepository;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.DIException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    OrderService orderService;
    IOrderRepository orderRepository;
    IUserRepository userRepository;

    @BeforeEach
    public void setUp() throws DIException {

        if(orderService != null){
            return;
        }

        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        absolutePath = absolutePath + "/target/test/WEB-INF/classes/by/javatr/cafe/";
        BeanFactory.getInstance().run(" " + absolutePath);

        orderService = (OrderService) BeanFactory.getInstance().getBean("orderService");
        MySqlOrderRepository mock = Mockito.mock(MySqlOrderRepository.class);
        orderService.setOrderRepository(mock);
        orderRepository = mock;
        MySqlUserRepository user_mock = Mockito.mock(MySqlUserRepository.class);
        orderService.setUserRepository(user_mock);
        userRepository = user_mock;

    }

    @Test
    void makeOrder() throws DAOException, ServiceException {

        Order order = new Order(1);
        User user = new User();

        when(orderRepository.createOrder(order)).thenReturn(order);
        when(orderRepository.createOrderDish(order)).thenReturn(order);
        when(userRepository.update(user)).thenReturn(user);


        Order order1 = orderService.makeOrderCashCard(order, user);

        assertEquals(25, user.getLoyaltyPoint());
        assertEquals(order, order1);

    }

    @Test
    void makeOrder_Should_Throw_Exception() throws DAOException{

        Order order = new Order(1);
        User user = new User();

        when(orderRepository.createOrder(order)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            orderService.makeOrderCashCard(order, user);
        });

        assertEquals(0, user.getLoyaltyPoint());

    }


    @Test
    void makeOrderBalance() throws DAOException, ServiceException {

        Order order = new Order();
        User user = new User();

        order.setAmount(new BigDecimal(10));
        user.setMoney(new BigDecimal(11));

        when(orderRepository.createOrder(order)).thenReturn(order);
        when(orderRepository.createOrderDish(order)).thenReturn(order);
        when(userRepository.update(user)).thenReturn(user);

        Order order1 = orderService.makeOrderBalance(order, user);

        assertEquals(order, order1);
        assertEquals(new BigDecimal(1), user.getMoney());
        assertEquals(25, user.getLoyaltyPoint());
    }

    @Test
    void makeOrderBalance_Should_Throw_Illegal_Exception() throws DAOException{

        Order order = new Order(1);
        User user = new User();

        order.setAmount(new BigDecimal(20));
        user.setMoney(new BigDecimal(19));

        assertThrows(IllegalArgumentException.class, ()->{
            orderService.makeOrderBalance(order, user);
        });

        assertEquals(new BigDecimal(19), user.getMoney());
        assertEquals(new BigDecimal(20), order.getAmount());

    }

    @Test
    void makeOrderBalance_Should_Throw_Service_Exception() throws DAOException{

        Order order = new Order(1);
        User user = new User();

        order.setAmount(new BigDecimal(20));
        user.setMoney(new BigDecimal(25));

        when(orderRepository.createOrder(order)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            orderService.makeOrderBalance(order, user);
        });

        assertEquals(new BigDecimal(25), user.getMoney());
        assertEquals(new BigDecimal(20), order.getAmount());

    }


    @Test
    void getOrders() throws DAOException, ServiceException {

        User user= new User();

        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());
        orders.add(new Order());

        when(orderRepository.getAll(user)).thenReturn(orders);

        List<Order> order1 = orderService.getOrders(user);

        ArrayList<Order> order_expected = new ArrayList<>();
        order_expected.add(new Order());
        order_expected.add(new Order());
        order_expected.add(new Order());

        assertEquals(order_expected, order1);

    }

    @Test
    void getAllUser_Should_Throw_Service_Exception() throws DAOException{

        User user = new User();

        when(orderRepository.getAll(user)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            orderService.getOrders(user);
        });
    }


    @Test
    void deposit() throws DAOException, ServiceException {

        Order order = new Order();
        User user = new User();

        order.setAmount(new BigDecimal(25));

        when(userRepository.findUser(user)).thenReturn(user);
        when(orderRepository.createOrder(order)).thenReturn(order);
        when(userRepository.update(user)).thenReturn(user);

        final Order order1 = orderService.deposit(order);

        assertEquals(new BigDecimal(25), user.getMoney());
        assertNotNull(order1);

    }

    @Test
    void depositUser_Should_Throw_Service_Exception() throws DAOException{

        Order order = new Order();
        User user = new User();
        order.setAmount(new BigDecimal(10));

        when(userRepository.findUser(user)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            orderService.deposit(order);
        });
    }


    @Test
    void updateOrder() throws DAOException, ServiceException {

        Order order = new Order();

        when(orderRepository.updateOrder(order)).thenReturn(order);

        final Order order1 = orderService.updateOrder(order);

        assertEquals(order, order1);

    }

    @Test
    void updateOrder_Should_Throw_Service_Exception() throws DAOException{

        Order order = new Order();

        when(orderRepository.updateOrder(order)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            orderService.updateOrder(order);
        });
    }

    @Test
    void getOrder_Should_Return_Null_When_No_User_Id() throws DAOException, ServiceException {

        Order order = new Order();
        when(orderRepository.getAll()).thenReturn(new ArrayList<>());

        final Order order1 = orderService.getOrder(order);

        assertNull(order1);
    }

    @Test
    void getOrder_Should_Return_Order_When_No_User_Id() throws DAOException, ServiceException {

        Order order = new Order();
        List<Order> list = new ArrayList<>();
        when(orderRepository.getAll()).thenReturn(list);
        list.add(order);

        final Order order1 = orderService.getOrder(order);

        assertEquals(order, order1);
    }

    @Test
    void getOrder_Should_Return_Order_When_User_Id_Is_Exist() throws DAOException, ServiceException {

        Order order = new Order();
        order.setUserId(1);
        List<Order> list = new ArrayList<>();
        when(orderRepository.getAll(new User(1))).thenReturn(list);
        list.add(order);

        final Order order1 = orderService.getOrder(order);

        assertEquals(order, order1);
    }

    @Test
    void getOrder_Should_Throw_Service_Exception() throws DAOException{

        Order order = new Order();

        when(orderRepository.getAll()).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            orderService.getOrder(order);
        });
    }


    @Test
    void cancelOrder_When_Time_More_Than_Three_Day() throws DAOException, ServiceException, ParseException {

        User user = new User();
        Order order = new Order();
        order.setOrderId(1);
        order.setAmount(new BigDecimal(20));

        DateFormat instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = instance.format(Calendar.getInstance().getTime());

        final long time = instance.parse(now).getTime() + 370_000_000;

        final String del_time = instance.format(time);

        order.setDeliveryTime(del_time);

        List<Order> list = new ArrayList<>();
        list.add(order);

        IOrderService new_orderService = Mockito.mock(IOrderService.class);

        when(userRepository.findUser(user)).thenReturn(user);
        when(userRepository.update(user)).thenReturn(user);
        when(orderRepository.getAll()).thenReturn(list);
        doReturn(order).when(new_orderService).getOrder(order);
        when(orderService.updateOrder(order)).thenReturn(order);

        boolean b = orderService.cancelOrder(order);

        assertTrue(b);
        assertEquals(PaymentStatus.CANCELED, order.getStatus());
        assertEquals(new BigDecimal(20), user.getMoney());
        assertEquals(-25, user.getLoyaltyPoint());
    }

    @Test
    void cancelOrder_When_Time_Less_Than_Three_Day() throws DAOException, ServiceException, ParseException {

        User user = new User();
        Order order = new Order();
        order.setOrderId(1);
        order.setAmount(new BigDecimal(20));

        DateFormat instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = instance.format(Calendar.getInstance().getTime());

        final long time = instance.parse(now).getTime();

        final String del_time = instance.format(time);

        order.setDeliveryTime(del_time);

        List<Order> list = new ArrayList<>();
        list.add(order);

        IOrderService new_orderService = Mockito.mock(IOrderService.class);

        when(userRepository.findUser(user)).thenReturn(user);
        when(userRepository.update(user)).thenReturn(user);
        when(orderRepository.getAll()).thenReturn(list);
        doReturn(order).when(new_orderService).getOrder(order);
        when(orderService.updateOrder(order)).thenReturn(order);

        boolean b = orderService.cancelOrder(order);

        assertTrue(b);
        assertEquals(PaymentStatus.CANCELED, order.getStatus());
        assertEquals(new BigDecimal(20), user.getMoney());
        assertEquals(-150, user.getLoyaltyPoint());

    }

    @Test
    void cancelOrder_Throw_Exception() throws DAOException, ServiceException, ParseException {

        User user = new User();
        Order order = new Order();

        List<Order> list = new ArrayList<>();
        list.add(order);

        IOrderService new_orderService = Mockito.mock(IOrderService.class);

        when(userRepository.findUser(user)).thenThrow(DAOException.class);
        when(userRepository.update(user)).thenThrow(DAOException.class);
        when(orderRepository.getAll()).thenReturn(list);
        doReturn(order).when(new_orderService).getOrder(order);
        when(orderService.updateOrder(order)).thenReturn(order);

        assertThrows(ServiceException.class, ()->{
            orderService.cancelOrder(order);
        });

    }

    @Test
    void violateOrder_True() throws ServiceException, DAOException {

        User user = new User();
        Order order = new Order();
        order.setOrderId(1);
        order.setAmount(new BigDecimal(20));

        List<Order> list = new ArrayList<>();
        list.add(order);

        IOrderService new_orderService = Mockito.mock(IOrderService.class);

        when(userRepository.findUser(user)).thenReturn(user);
        when(userRepository.update(user)).thenReturn(user);
        when(orderRepository.getAll()).thenReturn(list);
        doReturn(order).when(new_orderService).getOrder(order);
        when(orderService.updateOrder(order)).thenReturn(order);

        boolean b = orderService.violateOrder(order);

        assertTrue(b);
        assertEquals(-250, user.getLoyaltyPoint());

    }


    @Test
    void violateOrder_Exception() throws DAOException, ServiceException {


        User user = new User();
        Order order = new Order();
        order.setOrderId(1);
        order.setAmount(new BigDecimal(20));

        List<Order> list = new ArrayList<>();
        list.add(order);

        IOrderService new_orderService = Mockito.mock(IOrderService.class);

        when(userRepository.findUser(user)).thenThrow(DAOException.class);
        when(userRepository.update(user)).thenThrow(DAOException.class);
        when(orderRepository.getAll()).thenReturn(list);
        doReturn(order).when(new_orderService).getOrder(order);
        when(orderService.updateOrder(order)).thenReturn(order);

        assertThrows(ServiceException.class, ()->{
            orderService.violateOrder(order);
        });

    }
}