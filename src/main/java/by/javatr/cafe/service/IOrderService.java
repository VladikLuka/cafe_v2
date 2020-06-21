package by.javatr.cafe.service;

import by.javatr.cafe.entity.Cart;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IOrderService {

    boolean deposit(User user) throws ServiceException;

    Order makeOrder(Order order, User user) throws ServiceException;

    Order makeOrderBalance(Order order, User user) throws ServiceException;

    BigDecimal amount(Cart cart);

    List<Order> getOrders(User user) throws ServiceException;

    Map<Integer, ArrayList<Order>> getOrders() throws ServiceException;

    Order deposit(Order order) throws ServiceException;

    Order updateOrder(Order order) throws ServiceException;

    Order getOrder(Order order);
}
