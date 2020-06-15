package by.javatr.cafe.dao.repository;

import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;

import java.util.List;

public interface IOrderRepository {

    Order createOrder(Order order) throws DAOException;

    List<Order> getAll(User user) throws DAOException;

    List<Order> getAll() throws DAOException;

    Order createOrderBalance(Order order) throws DAOException;
}
