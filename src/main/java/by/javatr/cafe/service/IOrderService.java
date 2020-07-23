package by.javatr.cafe.service;

import by.javatr.cafe.entity.Cart;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IOrderService {

    Order makeOrderCashCard(Order order, User user) throws ServiceException;

    Order makeOrderBalance(Order order, User user) throws ServiceException;

    Order creditOrder(Order order, User user) throws ServiceException;

    List<Order> getOrders(User user) throws ServiceException;

    Order deposit(Order order) throws ServiceException;

    Order updateOrder(Order order) throws ServiceException;

    Order getOrder(Order order) throws ServiceException;

    boolean cancelOrder(Order order) throws ServiceException;

    boolean violateOrder(Order order) throws ServiceException;

    boolean closeCredit(Order order) throws DAOException, ServiceException;
}
