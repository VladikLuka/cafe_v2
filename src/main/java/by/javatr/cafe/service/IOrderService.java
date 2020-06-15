package by.javatr.cafe.service;

import by.javatr.cafe.entity.Cart;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface IOrderService {

    boolean deposit(User user) throws ServiceException;

    Order makeOrder(Order order) throws ServiceException;

    Order makeOrderBalance(Order order) throws ServiceException;

    BigDecimal amount(Cart cart);

    List<Order> getOrders(User user) throws ServiceException;

    List<Order> getOrders() throws ServiceException;
}
