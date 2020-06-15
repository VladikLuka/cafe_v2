package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.dao.Cache;
import by.javatr.cafe.dao.connection.impl.ConnectionPool;
import by.javatr.cafe.dao.repository.IOrderRepository;
import by.javatr.cafe.dao.repository.IUserRepository;
import by.javatr.cafe.entity.Cart;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IOrderService;

import java.math.BigDecimal;
import java.util.List;


@Component
public class OrderService implements IOrderService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IOrderRepository orderRepository;
    @Autowired
    Cache cache;

    @Override
    public boolean deposit(User user) throws ServiceException {

        try {
            userRepository.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return true;

    }

    @Override
    public Order makeOrder(Order order) throws ServiceException {

        try {
            order = orderRepository.createOrder(order);
            cache.addOrder(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return order;
    }

    @Override
    public Order makeOrderBalance(Order order) throws ServiceException {

        try{
            order = orderRepository.createOrderBalance(order);
            cache.addOrder(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return order;
    }

    @Override
    public BigDecimal amount(Cart cart) {

        List<Dish> cart1 = cart.getCart();

        BigDecimal decimal = new BigDecimal(0);
        decimal.setScale(2);

        for (Dish dish: cart1) {
            decimal = decimal.add(dish.getPrice());
        }
        return decimal;
    }

    @Override
    public List<Order> getOrders(User user) throws ServiceException {

        try {
          return orderRepository.getAll(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public List<Order> getOrders() throws ServiceException {

        try {
            return orderRepository.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }
}
