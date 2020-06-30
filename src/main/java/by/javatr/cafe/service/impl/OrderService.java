package by.javatr.cafe.service.impl;

import by.javatr.cafe.constant.PaymentStatus;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


@Component
public class OrderService implements IOrderService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IOrderRepository orderRepository;

    @Override
    public Order makeOrder(Order order, User user) throws ServiceException {

        try {
            user.setLoyalty_point(user.getLoyalty_point() + 25);
            order = orderRepository.createOrder(order);
            order = orderRepository.createOrderDish(order);
            userRepository.update(user);
        } catch (DAOException e) {
            user.setLoyalty_point(user.getLoyalty_point() - 25);
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
            orderRepository.createOrder(order);
            orderRepository.createOrderDish(order);
            userRepository.update(user);
        } catch (DAOException e) {
            user.setLoyalty_point(user.getLoyalty_point() - 25);
            user.setMoney(user.getMoney().add(order.getAmount()));
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
    public Map<Integer, ArrayList<Order>> getOrders() throws ServiceException {
//        try {
//            orderRepository.getAll();
//
//        } catch (DAOException e) {
//            throw new ServiceException(e);
//        }
//        return cache.getMapOrders();
    return null;
    }

    @Override
    public Order deposit(Order order) throws ServiceException {

        try {
            User user = userRepository.findUser(new User(order.getUser_id()));
            user.setMoney(user.getMoney().add(order.getAmount()));
            order = orderRepository.createOrder(order);
            userRepository.update(user);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return order;
    }

    @Override
    public Order updateOrder(Order order) throws ServiceException {

        try {
            return orderRepository.updateOrder(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Order getOrder(Order order) throws ServiceException {

        try {
            if(order.getUser_id() != 0) {
                List<Order> all = orderRepository.getAll(new User(order.getUser_id()));
                for (Order user_order :all) {
                    if(user_order.getOrder_id() == order.getOrder_id()){
                        return user_order;
                    }
                }
            }else {
                final List<Order> all = orderRepository.getAll();
                for (Order user_order :all) {
                    if(user_order.getOrder_id() == order.getOrder_id()){
                        return user_order;
                    }
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return null;
    }

    @Override
    public boolean cancelOrder(Order order) throws ServiceException {

        try {
            order = getOrder(order);

            DateFormat instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String now = instance.format(Calendar.getInstance().getTime());

            order.setStatus(PaymentStatus.CANCELED);
            order = updateOrder(order);
            User user = userRepository.findUser(new User(order.getUser_id()));
            user.setMoney(user.getMoney().add(order.getAmount()));

            if (instance.parse(now).getTime() + 3_600_000 > instance.parse(order.getDelivery_time()).getTime()) {
                user.setLoyalty_point(user.getLoyalty_point() - 150);
                if(user.getLoyalty_point() < 0) {
                    user.setBan(true);
                }
                userRepository.update(user);
                return true;
            }

            user.setLoyalty_point(user.getLoyalty_point() - 25);
            userRepository.update(user);
            return true;
        } catch (ServiceException | DAOException e) {
            throw new ServiceException(e);
        } catch (ParseException e) {
            throw new ServiceException("Incorrect format date", e);
        }
    }

    @Override
    public boolean violateOrder(Order order) throws ServiceException {

        try {
            order = getOrder(order);
            order.setStatus(PaymentStatus.VIOLATED);

            User user = userRepository.findUser(new User(order.getUser_id()));

            user.setLoyalty_point(user.getLoyalty_point() - 250);

            if (user.getLoyalty_point() < 0){
                user.setBan(true);
            }
            orderRepository.updateOrder(order);
            userRepository.update(user);
            return true;
        } catch (ServiceException | DAOException e) {
            throw new ServiceException(e);
        }
    }
}
