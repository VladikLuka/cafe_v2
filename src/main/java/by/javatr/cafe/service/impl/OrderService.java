package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.util.Cache;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
    public Order makeOrder(Order order, User user) throws ServiceException {

        try {
            order = orderRepository.createOrder(order, user);
            user.setLoyalty_point(user.getLoyalty_point() + 25);
            cache.addOrder(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return order;
    }

    @Override
    public Order makeOrderBalance(Order order, User user) throws ServiceException {
            final BigDecimal zero = new BigDecimal(0);
        try{
            final BigDecimal subtract = user.getMoney().subtract(order.getAmount());

            if(subtract.compareTo(zero) < 0) throw new IllegalArgumentException("Not enough money");
            user.setMoney(subtract);
            user.setLoyalty_point(user.getLoyalty_point() + 25);
            Order order1 = orderRepository.createOrderBalance(order, user);
            if(order1 != null){
                cache.updateUser(user);
                return order1;
            }else {
                user.setLoyalty_point(user.getLoyalty_point() - 25);
                user.setMoney(user.getMoney().add(order.getAmount()));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    return null;
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
        return cache.getOrders(user.getId());
    }

    @Override
    public Map<Integer, ArrayList<Order>> getOrders() throws ServiceException {
        return cache.getMapOrders();
    }

    @Override
    public Order deposit(Order order) throws ServiceException {

        try {
            User user = cache.getUser(order.getUser_id());
            order = orderRepository.createOrder(order, user);
            cache.addOrder(order);
            user.setMoney(user.getMoney().add(order.getAmount()));
            cache.updateUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return order;
    }

    @Override
    public Order updateOrder(Order order) throws ServiceException {

        try {
            order = orderRepository.updateOrder(order);
            cache.updateOrder(order);
            return order;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Order getOrder(Order order) {

        final Map<Integer, ArrayList<Order>> mapOrders = cache.getMapOrders();
        final Set<Integer> keySet = mapOrders.keySet();

        for (int i :keySet) {
            final ArrayList<Order> orders = mapOrders.get(i);
            for (Order usr_order :orders) {
                if (usr_order.getOrder_id() == order.getOrder_id()) {
                    return usr_order;
                }
            }
        }

//        final List<Order> orders = cache.getOrders(order.getUser_id());
//
//        for (Order user_order :orders) {
//            if (user_order.getOrder_id() == order.getOrder_id()) {
//                return user_order;
//            }
//        }
        return null;
    }
}
