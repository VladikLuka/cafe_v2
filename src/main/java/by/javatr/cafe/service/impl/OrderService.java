package by.javatr.cafe.service.impl;

import by.javatr.cafe.constant.PaymentStatus;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.dao.repository.IOrderRepository;
import by.javatr.cafe.dao.repository.IUserRepository;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IOrderService;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Contains methods for work with dishes
 */
@Component
public class OrderService implements IOrderService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IOrderRepository orderRepository;


    /**
     * Create order
     * @param order order to be created
     * @param user user to be updated
     * @return created order
     */
    @Override
    public Order makeOrderCashCard(Order order, User user) throws ServiceException {

        try {
            user.setLoyaltyPoint(user.getLoyaltyPoint() + 25);
            order = orderRepository.createOrder(order);
            order = orderRepository.createOrderDish(order);
            userRepository.update(user);
        } catch (DAOException e) {
            user.setLoyaltyPoint(user.getLoyaltyPoint() - 25);
            throw new ServiceException(e);
        }
        return order;
    }

    /**
     * Create order using user balance
     * @param order to be created
     * @param user to be updated
     * @return created order
     */
    @Override
    public Order makeOrderBalance(Order order, User user) throws ServiceException {

        try{
            final BigDecimal zero = new BigDecimal(0);
            final BigDecimal subtract = user.getMoney().subtract(order.getAmount());

            if(subtract.compareTo(zero) < 0) throw new IllegalArgumentException("Not enough money");
            user.setMoney(subtract);
            user.setLoyaltyPoint(user.getLoyaltyPoint() + 25);
            orderRepository.createOrder(order);
            orderRepository.createOrderDish(order);
            userRepository.update(user);
        } catch (DAOException e) {
            user.setLoyaltyPoint(user.getLoyaltyPoint() - 25);
            user.setMoney(user.getMoney().add(order.getAmount()));
            throw new ServiceException(e);
        }
    return order;
    }

    /**
     * Create credit order
     * @param order to be created
     * @param user to be updated
     * @return created order
     */
    @Override
    public Order creditOrder(Order order, User user) throws ServiceException {

        try{
            user.setLoyaltyPoint(user.getLoyaltyPoint() + 25);
            user.setCredit(true);
            orderRepository.createOrder(order);
            orderRepository.createOrderDish(order);
            userRepository.update(user);
        } catch (DAOException e) {
            user.setCredit(false);
            user.setLoyaltyPoint(user.getLoyaltyPoint() - 25);
            throw new ServiceException(e);
        }
        return order;

    }

    /**
     * Returns all users orders
     * @param user whose orders will be returned
     * @return list of orders
     */
    @Override
    public List<Order> getOrders(User user) throws ServiceException {
        try {
            return orderRepository.getAll(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    /**
     * Replenish user balance
     * @return created order
     * @throws ServiceException
     */
    @Override
    public Order deposit(Order order) throws ServiceException {

        try {
            User user = userRepository.findUser(new User(order.getUserId()));
            user.setMoney(user.getMoney().add(order.getAmount()));
            order = orderRepository.createOrder(order);
            userRepository.update(user);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return order;
    }

    /**
     * Update order
     * @param order to be updated
     * @return updated order
     */
    @Override
    public Order updateOrder(Order order) throws ServiceException {

        try {
            return orderRepository.updateOrder(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns order
     * @param order to be returned
     * @return found order
     */
    @Override
    public Order getOrder(Order order) throws ServiceException {

        try {
            if(order.getUserId() != 0) {
                List<Order> all = orderRepository.getAll(new User(order.getUserId()));
                for (Order user_order :all) {
                    if(user_order.getOrderId() == order.getOrderId()){
                        return user_order;
                    }
                }
            }else {
                final List<Order> all = orderRepository.getAll();
                for (Order user_order :all) {
                    if(user_order.getOrderId() == order.getOrderId()){
                        return user_order;
                    }
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return null;
    }

    /**
     * Cancel order
     * @param order cancelled order
     * @return boolean
     */
    @Override
    public boolean cancelOrder(Order order) throws ServiceException {

        try {
            order = getOrder(order);

            DateFormat instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String now = instance.format(Calendar.getInstance().getTime());

            order.setStatus(PaymentStatus.CANCELED);
            order = updateOrder(order);
            User user = userRepository.findUser(new User(order.getUserId()));
            user.setMoney(user.getMoney().add(order.getAmount()));

            if (instance.parse(now).getTime() + 3_600_000 > instance.parse(order.getDeliveryTime()).getTime()) {
                user.setLoyaltyPoint(user.getLoyaltyPoint() - 150);
                userRepository.update(user);
                return true;
            }

            user.setLoyaltyPoint(user.getLoyaltyPoint() - 25);
            if(user.getLoyaltyPoint() < 0) {
                user.setBan(true);
            }
            userRepository.update(user);

            return true;
        } catch (ServiceException | DAOException e) {
            throw new ServiceException(e);
        } catch (ParseException e) {
            throw new ServiceException("Incorrect date format", e);
        }
    }

    /**
     * Close credit order
     * @param order to be closed
     * @return boolean
     */
    @Override
    public boolean closeCredit(Order order) throws ServiceException {

        User user = new User(order.getUserId());

        try {
            user = userRepository.findUser(user);

            BigDecimal user_money = user.getMoney();

            if (user_money.subtract(order.getAmount()).compareTo(BigDecimal.ZERO) >= 0){

                user.setCredit(false);
                user.setMoney(user_money.subtract(order.getAmount()));
                order.setStatus(PaymentStatus.CLOSED);
                userRepository.update(user);
                orderRepository.updateOrder(order);
            }

        }catch (DAOException e){
            user.setCredit(true);
            user.setMoney(user.getMoney().add(order.getAmount()));
            throw new ServiceException(e);
        }

        return true;
    }

    /**
     * Violate credit order
     * @param order to be violated
     * @return boolean
     */
    @Override
    public boolean violateOrder(Order order) throws ServiceException {

        try {
            order = getOrder(order);
            order.setStatus(PaymentStatus.VIOLATED);

            User user = userRepository.findUser(new User(order.getUserId()));

            user.setLoyaltyPoint(user.getLoyaltyPoint() - 250);

            if (user.getLoyaltyPoint() < 0){
                user.setBan(true);
            }
            orderRepository.updateOrder(order);
            userRepository.update(user);
            return true;
        } catch (ServiceException | DAOException e) {
            throw new ServiceException(e);
        }



    }

    public void setOrderRepository(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
