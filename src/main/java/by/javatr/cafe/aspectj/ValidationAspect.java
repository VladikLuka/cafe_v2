package by.javatr.cafe.aspectj;

import by.javatr.cafe.constant.*;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.*;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IAddressService;
import by.javatr.cafe.service.IDishService;
import by.javatr.cafe.service.IOrderService;
import by.javatr.cafe.service.IUserService;
import by.javatr.cafe.service.impl.AddressService;
import by.javatr.cafe.service.impl.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Represents a validation class
 * for a service layer and command implementations.
 * AspectJ technology is used.
 */
@Aspect
@Component
public class ValidationAspect {

    private final Logger logger = LogManager.getLogger(getClass());

    @Before(value = "execution(public * by.javatr.cafe.controller.listener.Config.contextInitialized(..))")
    public void test(){
        logger.info("ASPECT WORK");
        System.out.println("AspectJ WORK CORRECT");
    }

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.AddAddress.execute(..))")
    public void AddAddress(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.AddToCart.execute(..))")
    public void addToCart(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.OrderService.makeOrderCashCard(..))")
    public void makeOrderValidation(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.ChangePass.execute(..))")
    public void changePass(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.DeleteAddress.execute(..))")
    public void delAddress(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.DeleteDishFromCart.execute(..))")
    public void deleteFromCart(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.Feedback.execute(..))")
    public void fb(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.LogIn.execute(..))")
    public void logIn(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.SignUp.execute(..))")
    public void signUp(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.payment.BalanceOrder.execute(..))")
    public void balanceOrderValid(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.payment.CancelOrder.execute(..))")
    public void cancelOrder(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.payment.CashOrder.execute(..)) " +
            "  || execution(public * by.javatr.cafe.controller.command.impl.payment.BalanceOrder.execute(..))")
    public void cashBalanceOrder(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.payment.Deposit.execute(..))")
    public void deposit_test(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.payment.CardOrder.execute(..))")
    public void makeOrderCard(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.admin.AddMoney.execute(..)) " +
            "  || execution(public * by.javatr.cafe.controller.command.impl.admin.SubtractMoney.execute(..))")
    public void changeMoney(){}


    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.admin.AddPoints.execute(..))" +
             " || execution(public * by.javatr.cafe.controller.command.impl.admin.SubtractPoints.execute(..))")
    public void changePoints(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.admin.BanUser.execute(..))")
    public void banUser(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.admin.UnbanUser.execute(..))")
    public void unbanUser(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.admin.CloseOrder.execute(..))")
    public void closeOrder(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.admin.CloseOrder.execute(..))")
    public void violateOrder(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.admin.HideDish.execute(..))")
    public void hideDish(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.admin.ShowDish.execute(..))")
    public void showDish(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.admin.ShowUserInfo.execute(..))")
    public void showUserInfo(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.UserService.find(..))" +
            "||execution(public * by.javatr.cafe.service.impl.UserService.delete(..))")
    public void userServiceFindDelete(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.UserService.update(..))")
    public void userServiceUpdate(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.UserService.loginUser(..))")
    public void loginUserService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.UserService.createUser(..))")
    public void createUserService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.UserService.addMoney(..))" +
            "||execution(public * by.javatr.cafe.service.impl.UserService.subtractMoney(..))")
    public void addSubstMoneyUserService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.UserService.addPoints(..))" +
            "||execution(public * by.javatr.cafe.service.impl.UserService.subtractPoints(..))")
    public void addSubstPointsUserService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.UserService.banUser(..))" +
            "||execution(public * by.javatr.cafe.service.impl.UserService.unbanUser(..))")
    public void banUnbanUserService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.AddressService.update(..))")
    public void updateAddressService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.AddressService.getAllForUser(..))")
    public void getAllForUserAddressService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.AddressService.create(..))")
    public void createAddressService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.AddressService.find(..))" +
            "||execution(public * by.javatr.cafe.service.impl.AddressService.delete(..))")
    public void findDeleteAddressService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.CartService.addToCart(..))")
    public void addToCartService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.CartService.deleteFromCart(..))")
    public void deleteFromCartService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.CartService.clean(..))" +
            "||execution(public * by.javatr.cafe.service.impl.CartService.getAll(..)) ")
    public void clearGetAllCartService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.DishService.get(..))")
    public void getDishService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.DishService.update(..))" +
            "||execution(public * by.javatr.cafe.service.impl.DishService.delete(..))" +
            "||execution(public * by.javatr.cafe.service.impl.DishService.hideDish(..))" +
            "||execution(public * by.javatr.cafe.service.impl.DishService.showDish(..))")
    public void dishServiceValid(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.DishService.create(..))")
    public void createDishService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.OrderService.makeOrderCashCard(..))")
    public void makeOrderService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.OrderService.makeOrderBalance(..))")
    public void makeOrderBalanceService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.OrderService.amount(..))")
    public void amountService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.OrderService.getOrders(..))")
    public void getOrdersService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.OrderService.deposit(..))")
    public void depositOrderService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.OrderService.updateOrder(..))")
    public void updateOrderService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.OrderService.getOrder(..))")
    public void getOrderService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.OrderService.cancelOrder(..))")
    public void cancelOrderService(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.OrderService.violateOrder(..))")
    public void violateOrderService(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.payment.CreditOrder.execute(..))")
    public void creditOrder(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.payment.CloseCreditBalance(..))")
    public void closeCreditBalance(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.admin.CreateDish.execute(..))")
    public void createDish(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.admin.MakeUserAdmin(..)) &&" +
              "execution(public * by.javatr.cafe.controller.command.impl.admin.MakeAdminUser(..))")
    public void changeRoleValid(){}

    @Around(value = "changeRoleValid()", argNames = "pjp,point")
    public Object changeRole(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String userId = content.getRequestParam(RequestParameters.USER_ID);

        if(userId == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!Regex.POS_INTEGER.matches(userId)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");

        try {
            User user = userService.find((int)content.getSessionAttr(SessionAttributes.USER_ID));

            if(!user.getRole().equals(Role.ADMIN)){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            return pjp.proceed();

        }catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "createDish()", argNames = "pjp,point")
    public Object createDishValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        String name = content.getRequestParam("dishName");
        String description = content.getRequestParam("dishDescription");
        String amount = content.getRequestParam("dishPrice");
        String weight = content.getRequestParam("dishWeight");
        String dishCategory = content.getRequestParam("dishCategory");
        String picturePath = content.getRequestParam("dishPicture");


        if(name == null || description == null || amount == null || weight == null || dishCategory == null || picturePath == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!weight.matches(Regex.POS_INTEGER)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            new BigDecimal(amount);
        }catch (NumberFormatException e){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");

        try {
            User user = userService.find((int)content.getSessionAttr(SessionAttributes.USER_ID));

            if(!user.getRole().equals(Role.ADMIN)){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            return pjp.proceed();

        }catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }


    }

    @Around(value = "closeCreditBalance()", argNames = "pjp,point")
    public Object closeCreditOrderValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        String userIdStr = content.getRequestParam(RequestParameters.USER_ID);
        String orderIdStr = content.getRequestParam(RequestParameters.ORDER_ID);
        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");

        if (userIdStr == null || orderIdStr == null) {
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if (!userIdStr.matches(Regex.POS_INTEGER) || !orderIdStr.matches(Regex.POS_INTEGER)) {
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        int userId = Integer.parseInt(userIdStr);

        try {
            final User user = userService.find(userId);
            if (user == null) {
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }
            if (user.isCredit()) {
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            return pjp.proceed();

        } catch (Throwable e) {
            logger.error(e);
            throw new ServiceException(e);
        }

    }

    @Around(value = "creditOrder()", argNames = "pjp,point")
    public Object creditOrderValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String addressId = content.getRequestParam(RequestParameters.ADDRESS);
        Integer userId =(Integer) content.getSessionAttr(SessionAttributes.USER_ID);
        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");

        if(addressId == null || userId == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!addressId.matches(Regex.POS_INTEGER)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            final User user = userService.find(userId);
            if(user == null){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            if(user.isCredit()){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            return pjp.proceed();

        } catch (Throwable e) {
            logger.error(e);
            throw new ServiceException(e);
        }

    }

    @Around(value = "violateOrderService()", argNames = "pjp,point")
    public Object violateOrderServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Order order = (Order) args[0];

        if(order == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "cancelOrderService()", argNames = "pjp,point")
    public Object cancelOrderServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Order order = (Order) args[0];

        if(order == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if (order.getUserId() <= 0 || order.getOrderId() <= 0 || order.getAmount() == null) {
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "getOrderService()", argNames = "pjp,point")
    public Object getOrderServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Order order = (Order) args[0];

        if(order == null){
            throw new IllegalArgumentException("order is null");
        }

        if (order.getOrderId() <= 0) {
            throw new IllegalArgumentException("incorrect id");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "updateOrderService()", argNames = "pjp,point")
    public Object updateOrderServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Order order = (Order) args[0];

        if(order == null){
            throw new IllegalArgumentException("order is null");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "depositOrderService()", argNames = "pjp,point")
    public Object depositOrderServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Order order = (Order) args[0];

        if(order == null){
            throw new IllegalArgumentException("order is null");
        }

        if(order.getUserId() <= 0 || order.getAmount() == null){
            throw new IllegalArgumentException("check object");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "getOrdersService()", argNames = "pjp,point")
    public Object getOrdersServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        User user = (User) args[0];

        if(user == null){
            throw new IllegalArgumentException("user is null");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }


    @Around(value = "amountService()", argNames = "pjp,point")
    public Object amountServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Cart cart = (Cart) args[0];

        if(cart == null){
            throw new IllegalArgumentException("cart is null");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }


    @Around(value = "makeOrderBalanceService()", argNames = "pjp,point")
    public Object makeOrderBalanceServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Order order = (Order) args[0];
        User user = (User) args[1];

        if(order == null || user == null){
            throw new IllegalArgumentException("order or user is null");
        }

        if(order.getAmount() == null){
            throw new IllegalArgumentException("order amount is null");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "makeOrderService()", argNames = "pjp,point")
    public Object makeOrderServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Order order = (Order) args[0];
        User user = (User) args[1];

        if(order == null || user == null){
            throw new IllegalArgumentException("order or user is null");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

        @Around(value = "createDishService()", argNames = "pjp,point")
        public Object createDishServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Dish dish = (Dish) args[0];

        if(dish == null){
            throw new IllegalArgumentException("dish is null");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "dishServiceValid()", argNames = "pjp,point")
    public Object dishServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Dish dish = (Dish) args[0];

        if(dish == null){
            throw new IllegalArgumentException("dish is null");
        }

        if(dish.getId() <= 0) {
            throw new IllegalArgumentException("dish incorrect id");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "getDishService()", argNames = "pjp,point")
    public Object getDishServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        int dish_id = (int) args[0];

        if(dish_id <= 0){
            throw new IllegalArgumentException("incorrect id");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }
    }



    @Around(value = "clearGetAllCartService()", argNames = "pjp,point")
    public Object clearGetAllCartServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Cart cart = (Cart) args[0];

        if (cart == null) {
            throw new IllegalArgumentException("cart is null");
        }

        if(cart.getUserCart() == null){
            throw new IllegalArgumentException("cart is null");
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }
    }

    @Around(value = "deleteFromCartService()", argNames = "pjp,point")
    public Object deleteFromCartServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Cart cart = (Cart) args[0];
        int dish_id = (int) args[1];

        if(cart == null || dish_id <= 0){
            throw new IllegalArgumentException("incorrect args");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }
    }

    @Around(value = "addToCartService()", argNames = "pjp,point")
    public Object addToCartServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Cart cart = (Cart) args[0];
        Dish dish = (Dish) args[1];

        if(cart == null || dish == null){
            throw new IllegalArgumentException("cart or dish is null");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }
    }

    @Around(value = "findDeleteAddressService()", argNames = "pjp,point")
    public Object findDeleteAddressServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Address address = (Address) args[0];

        if(address == null){
            throw new IllegalArgumentException("address is null");
        }

        if(address.getId() == 0){
            throw new IllegalArgumentException("incorrect address id");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "getAllForUserAddressService()", argNames = "pjp,point")
    public Object getAllForUserAddressServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        int address_id = (int) args[0];

        if(address_id <= 0){
            throw new IllegalArgumentException("incorrect address id");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "createAddressService()", argNames = "pjp,point")
    public Object createAddressServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Address address = (Address) args[0];

        if(address == null){
            throw new IllegalArgumentException("address is null");
        }

        if(address.getCity() == null || address.getStreet() == null || address.getHouse() == null || address.getFlat() == null || address.getUserId() == 0){
            throw new IllegalArgumentException("incorrect address");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "updateAddressService()", argNames = "pjp,point")
    public Object upadteAddressServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        Address address = (Address) args[0];

        if(address == null){
            throw new IllegalArgumentException("address is null");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "banUnbanUserService()", argNames = "pjp,point")
    public Object banUnbanValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        int userId = (int) args[0];

        if(userId <= 0){
            throw new IllegalArgumentException("incorrect user id");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "addSubstPointsUserService()", argNames = "pjp,point")
    public Object changePointsServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        int points = (int) args[0];
        int userId = (int) args[1];

        if(points == 0 || userId <= 0){
            throw new IllegalArgumentException("incorrect args");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "addSubstMoneyUserService()", argNames = "pjp,point")
    public Object changeMoneyServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        System.out.println("CHANGE MONEY SERVICE");

        Object[] args = point.getArgs();
        BigDecimal amount = (BigDecimal) args[0];
        int userId = (int)args[1];

        if(amount == null || userId <= 0){
            throw new IllegalArgumentException("incorrect user fields");
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("incorrect amount");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "createUserService()", argNames = "pjp,point")
    public Object createUserServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        User user = (User)args[0];

        if(user == null){
            throw new IllegalArgumentException("user is null");
        }

        if(user.getMail() == null || user.getName() == null || user.getSurname() == null || user.getPassword() == null || user.getPhone() == null){
            throw new IllegalArgumentException("incorrect user field");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "loginUserService()", argNames = "pjp,point")
    public Object loginUserServiceValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        User user = (User)args[0];

        if(user == null){
            throw new IllegalArgumentException("user is null");
        }

        if(user.getPassword() == null || user.getMail() == null){
            throw new IllegalArgumentException("incorrect user fields");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "userServiceUpdate()", argNames = "pjp,point")
    public Object userServiceUpdateValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        User user = (User)args[0];

        if(user == null){
            throw new IllegalArgumentException("user is null");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "userServiceFindDelete()", argNames = "pjp,point")
    public Object userServiceFindDeleteValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        int userId = (int)args[0];

        if(userId <= 0){
            throw new IllegalArgumentException("incorrect user id");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "showUserInfo()", argNames = "pjp,point")
    public Object showUserInfoValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");
        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String showUserId = content.getRequestParam(RequestParameters.ID);
        final Integer userId = (Integer) content.getSessionAttr(SessionAttributes.USER_ID);

        if(showUserId == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!showUserId.matches(Regex.POS_INTEGER)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!userService.find(userId).getRole().equals(Role.ADMIN) || userService.find(Integer.parseInt(showUserId)) == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }


        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }
    }


    @Around(value = "showDish()", argNames = "pjp,point")
    public Object showDishValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");
        IDishService dishService = (IDishService) BeanFactory.getInstance().getBean("dishService");

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String dishId = content.getRequestParam("id");
        final Integer userId =(Integer) content.getSessionAttr(SessionAttributes.USER_ID);

        if(dishId == null || userId == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!dishId.matches(Regex.POS_INTEGER)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {

            final User user = userService.find(userId);
            if(!user.getRole().equals(Role.ADMIN)){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            final Dish dish = dishService.get(Integer.parseInt(dishId));
            if(dish == null){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            if(dish.isAvailable()){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (ServiceException e) {
            throw new ServiceException(e);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "hideDish()", argNames = "pjp,point")
    public Object hideDishValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");
        IDishService dishService = (IDishService) BeanFactory.getInstance().getBean("dishService");

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String dishId = content.getRequestParam("id");
        final Integer userId =(Integer) content.getSessionAttr(SessionAttributes.USER_ID);

        if(dishId == null || userId == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!dishId.matches(Regex.POS_INTEGER)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {

            final User user = userService.find(userId);
            if(!user.getRole().equals(Role.ADMIN)){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            final Dish dish = dishService.get(Integer.parseInt(dishId));
            if(dish == null){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            if(!dish.isAvailable()){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (ServiceException e) {
            throw new ServiceException(e);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }


    @Around(value = "violateOrder()", argNames = "pjp,point")
    public Object violateOrderValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");
        IOrderService orderService = (IOrderService) BeanFactory.getInstance().getBean("orderService");

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String orderId = content.getRequestParam("id");
        final Integer userId =(Integer) content.getSessionAttr(SessionAttributes.USER_ID);

        if(orderId == null || userId == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!orderId.matches(Regex.POS_INTEGER)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {

            final User user = userService.find(userId);
            if(!user.getRole().equals(Role.ADMIN)){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            Order order = orderService.getOrder(new Order(Integer.parseInt(orderId)));

            if(order == null){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            if(order.getStatus().equals(PaymentStatus.VIOLATED)){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (ServiceException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }

    }


    @Around(value = "closeOrder()", argNames = "pjp,point")
    public Object closeOrderdValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");
        IOrderService orderService = (IOrderService) BeanFactory.getInstance().getBean("orderService");

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String orderId = content.getRequestParam(RequestParameters.ID);
        final Integer userId =(Integer) content.getSessionAttr(SessionAttributes.USER_ID);

        if(orderId == null || userId == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!orderId.matches(Regex.POS_INTEGER)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {

            final User user = userService.find(userId);
            if(!user.getRole().equals(Role.ADMIN)){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            Order order = orderService.getOrder(new Order(Integer.parseInt(orderId)));

            if(order == null){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            if(order.getStatus().equals(PaymentStatus.CLOSED)){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (ServiceException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "unbanUser()", argNames = "pjp,point")
    public Object unbanUserValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String banUserId = content.getRequestParam(RequestParameters.USER_ID);
        final Integer userId = (Integer)content.getSessionAttr(SessionAttributes.USER_ID);

        if(banUserId == null || userId == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!banUserId.matches(Regex.POS_INTEGER)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            final User user = userService.find(userId);
            if(!user.getRole().equals(Role.ADMIN)){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            final User banUser = userService.find(Integer.parseInt(banUserId));

            if(!banUser.isBan()){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new ServiceException(e);
        }


        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "banUser()", argNames = "pjp,point")
    public Object banUserValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String banUserId = content.getRequestParam(RequestParameters.USER_ID);
        final Integer userId = (Integer)content.getSessionAttr(SessionAttributes.USER_ID);

        if(banUserId == null || userId == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!banUserId.matches(Regex.POS_INTEGER)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            final User user = userService.find(userId);
            if(!user.getRole().equals(Role.ADMIN)){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            final User banUser = userService.find(Integer.parseInt(banUserId));

            if(banUser.isBan()){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new ServiceException(e);
        }


        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "changePoints()", argNames = "pjp,point")
    public Object addSubstrPointValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String points = content.getRequestParam(RequestParameters.POINTS);
        final String userChange = content.getRequestParam(RequestParameters.USER_ID);
        final Integer userId =(Integer) content.getSessionAttr(SessionAttributes.USER_ID);

        if(points == null || userChange == null ||userId == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!points.matches(Regex.POS_INTEGER)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!userChange.matches(Regex.POS_INTEGER)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            final User changeUser = userService.find(Integer.parseInt(userChange));
            if(changeUser == null){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }
            final User user = userService.find(userId);
            if (!user.getRole().equals(Role.ADMIN)){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }
    }

    @Around(value = "changeMoney()", argNames = "pjp,point")
    public Object addSubstMoneyValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        System.out.println("CHANGE MONEY");

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String amountStr = content.getRequestParam(RequestParameters.AMOUNT);
        final String userIdChange = content.getRequestParam(RequestParameters.USER_ID);
        final Integer userId = (Integer) content.getSessionAttr(SessionAttributes.USER_ID);

        if(amountStr == null || userIdChange == null || userId == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!userIdChange.matches(Regex.POS_INTEGER)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");
        try {
            final User changeUser = userService.find(Integer.parseInt(userIdChange));
            if(changeUser == null){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            final User user = userService.find(userId);
            if(!user.getRole().equals(Role.ADMIN)){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        try{
            new BigDecimal(amountStr);
        }catch (NumberFormatException e){
            logger.error(e);
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }

    }


    @Around(value = "makeOrderCard()", argNames = "pjp,point")
    public Object makeOrderCardValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];
        IUserService userService =(IUserService) BeanFactory.getInstance().getBean("userService");

        String addressIdStr = content.getRequestParam(RequestParameters.ADDRESS);
        String payMethod = content.getRequestParam(RequestParameters.PAYMENT_NONCE);
        String deliveryTime = content.getRequestParam(RequestParameters.TIME_DELIVERY);
        Integer userId = (Integer) content.getSessionAttr(SessionAttributes.USER_ID);
        Cart cart = (Cart) content.getSessionAttr(SessionAttributes.CART);

        if(addressIdStr == null || payMethod == null || userId == null || deliveryTime == null || cart == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!deliveryTime.matches("^(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})")){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }


        if(cart.getUserCart() == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(cart.getUserCart().isEmpty()){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        User user = userService.find(userId);

        if(user == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(user.isCredit()){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "deposit_test()", argNames = "pjp,point")
    public Object depositValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        String payMethod = content.getRequestParam(RequestParameters.PAYMENT_NONCE);
        String amount = content.getRequestParam(RequestParameters.AMOUNT);
        Integer userId =(Integer) content.getSessionAttr(SessionAttributes.USER_ID);

        if(payMethod == null || amount == null || userId == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try{
            new BigDecimal(amount);
        }catch (NumberFormatException e){
            throw new ServiceException(e);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "cancelOrder()", argNames = "pjp,point")
    public Object cancelOrderValid(ProceedingJoinPoint pjp, JoinPoint point){

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String orderIdStr = content.getRequestParam(RequestParameters.ORDER_ID);
        Integer userId =(Integer) content.getSessionAttr(SessionAttributes.USER_ID);


        if(userId == null || orderIdStr == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        int orderId = Integer.parseInt(orderIdStr);

        IOrderService orderService = (IOrderService) BeanFactory.getInstance().getBean("orderService");

        Object result = null;

        try {
            final List<Order> orders = orderService.getOrders(new User(userId));

            for (Order order :orders) {
                if(order.getOrderId() == orderId){
                    result = pjp.proceed();
                    break;
                }
            }

            if(result == null){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (Throwable e) {
            logger.error(e);
        }

        return result;
    }

    @Around(value = "cashBalanceOrder()", argNames = "pjp,point")
    public Object cashBalanceOrderValid(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        String deliveryTime = content.getRequestParam(RequestParameters.TIME_DELIVERY);
        String addressIdStr = content.getRequestParam(RequestParameters.ADDRESS);
        int userId = (int) content.getSessionAttr(SessionAttributes.USER_ID);
        Cart cart =(Cart) content.getSessionAttr(SessionAttributes.CART);
        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");


        if(deliveryTime == null || addressIdStr == null || cart == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(cart.getUserCart() == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(cart.getUserCart().isEmpty()){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!deliveryTime.matches("^(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})")){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        User user = userService.find(userId);

        if(user == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(user.isCredit()){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "signUp()", argNames = "pjp,point")
    public Object signup(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        String name = content.getRequestParam(RequestParameters.USER_NAME);
        String surname = content.getRequestParam(RequestParameters.USER_SURNAME);
        String email = content.getRequestParam(RequestParameters.USER_EMAIL);
        String password = content.getRequestParam(RequestParameters.USER_PASSWORD);
        String phone = content.getRequestParam(RequestParameters.USER_PHONE);

        if(name == null || surname == null || email == null || password == null || phone == null) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        if(!name.matches(Regex.NAME) ||
                !surname.matches(Regex.NAME) ||
                !email.matches(Regex.EMAIL) ||
                !password.matches(Regex.PASSWORD) ||
                !phone.matches(Regex.PHONE)){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        User user = new User(name, surname, email, password, phone);

        IUserService userService = (UserService) BeanFactory.getInstance().getBean("userService");

        user = userService.loginUser(user);

        if(user != null) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        try {
            Object o = pjp.proceed();
            logger.info("User registered" + name + " " + surname + " " + email + " " + phone);
            return o;
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "logIn()", argNames = "pjp,point")
    public Object login(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String login = content.getRequestParam(RequestParameters.LOGIN_EMAIL);
        final String password = content.getRequestParam(RequestParameters.LOGIN_PASSWORD);

        if (login == null || password == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!login.matches(Regex.EMAIL) || !password.matches(Regex.PASSWORD)) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }
    }

    @Around(value = "fb()", argNames = "pjp,point")
    public Object feedback(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        final String stars = content.getRequestParam(RequestParameters.ORDER_RATING);
        final String feedback = content.getRequestParam(RequestParameters.ORDER_FEEDBACK);
        String orderIdStr = content.getRequestParam(RequestParameters.ORDER_ID);

        if(stars == null || feedback == null || orderIdStr == null) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        if(Integer.parseInt(stars) < 1 || Integer.parseInt(stars) > 5) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        if(!orderIdStr.matches(Regex.POS_INTEGER)) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        int orderId = Integer.parseInt(orderIdStr);

        try {

            IOrderService orderService = (IOrderService) BeanFactory.getInstance().getBean("orderService");

            Order order = orderService.getOrder(new Order(orderId));

            if(order == null){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            if (order.getUserId() != (int) content.getSessionAttr(SessionAttributes.USER_ID)) {
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            return pjp.proceed();

        } catch (ServiceException e) {
            throw new ServiceException(e);
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }

    }

    @Around(value = "deleteFromCart()", argNames = "pjp,point")
    public Object delFromCart(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        String dishIdStr = content.getRequestParam(RequestParameters.ID);

        if(!dishIdStr.matches(Regex.POS_INTEGER)) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        int dishId = Integer.parseInt(dishIdStr);

        final Cart cart = (Cart) content.getSessionAttr(SessionAttributes.CART);

        if(cart == null) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        if(cart.getUserCart() == null) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        if(cart.getUserCart().isEmpty()) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        IDishService dishService = (IDishService) BeanFactory.getInstance().getBean("dishService");

        Dish dish = dishService.get(dishId);

        if (!cart.getUserCart().contains(dish)) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }

    }


    @Around(value = "execution(public * by.javatr.cafe.controller.command.impl.DeleteAddress.execute(..))", argNames = "pjp,point")
    public Object deleteAddress(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        int userId = (int) content.getSessionAttr(SessionAttributes.USER_ID);


        int id;

        if (content.getRequestParam(RequestParameters.ID)!=null) {
            final String addressId = content.getRequestParam(RequestParameters.ID);
            if (addressId.matches(Regex.POS_INTEGER)){
                id = Integer.parseInt(addressId);
            }else{
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        else{
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            IUserService userService = (UserService) BeanFactory.getInstance().getBean("userService");
            IAddressService addressService = (AddressService) BeanFactory.getInstance().getBean("addressService");

            User user = userService.find(userId);
            Address address = addressService.find(new Address(id));

            if(user.getId() != address.getUserId()){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            return pjp.proceed();

        } catch (ServiceException e) {
            logger.error("cannot find address", e);
            throw new ServiceException(e);
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }

    }


    @Around(value = "changePass()", argNames = "pjp,point")
    public Object changePassword(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        Object[] args = point.getArgs();
        final RequestContent content = (RequestContent) args[0];
        String oldPass = content.getRequestParam(RequestParameters.OLD_PASSWORD);
        String newPass = content.getRequestParam(RequestParameters.NEW_PASSWORD);

        if(oldPass == null || newPass == null) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        if(oldPass.equals(newPass)) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        if(!newPass.matches(Regex.PASSWORD)) {
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error("ChangePass exception", throwable);
            throw new ServiceException(throwable);
        }

    }


    @Around(value = "addToCart()", argNames = "pjp,point")
    public Object addToCart(ProceedingJoinPoint pjp, JoinPoint point) {

        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];
        Map<String, String[]> requestParameters = content.getRequestParameters();
        String id;
        if (requestParameters.containsKey(RequestParameters.ID)) {

            id = content.getRequestParam(RequestParameters.ID);
            try {
                Integer.parseInt(id);
                return (RequestResult) pjp.proceed();
            } catch (Throwable e) {
                logger.error(e);
                return new RequestResult(Navigation.FORWARD, Path.ERROR, HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        return new RequestResult(Navigation.FORWARD, Path.ERROR, HttpServletResponse.SC_BAD_REQUEST);
    }


    @Around(value = "AddAddress()", argNames = "pjp,point")
    public Object addAddress(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {

        IUserService userService = (IUserService) BeanFactory.getInstance().getBean("userService");
        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];
        RequestResult result = new RequestResult(Navigation.FORWARD, Path.ERROR, HttpServletResponse.SC_BAD_REQUEST);

        Integer userId = (Integer) content.getSessionAttr(SessionAttributes.USER_ID);

        if(userId == null){
            return result ;
        }

        if(userService.find(userId).getRole().ordinal() > Role.getRoleByName(AccessLevel.USER).ordinal()){
            return result;
        }
        Map<String, String[]> requestParameters = content.getRequestParameters();
        String city = null;
        String street = null;
        String house = null;
        String flat = null;
        if(requestParameters.containsKey(RequestParameters.ADDRESS_CITY)) city = content.getRequestParam(RequestParameters.ADDRESS_CITY);
        if(requestParameters.containsKey(RequestParameters.ADDRESS_STREET)) street = content.getRequestParam(RequestParameters.ADDRESS_STREET);
        if(requestParameters.containsKey(RequestParameters.ADDRESS_HOUSE)) house = content.getRequestParam(RequestParameters.ADDRESS_HOUSE);
        if(requestParameters.containsKey(RequestParameters.ADDRESS_FLAT)) flat = content.getRequestParam(RequestParameters.ADDRESS_FLAT);

        if(city!= null && street!= null && house!= null && flat!= null) {
            if(!(city.equals("") || street.equals("") || house.equals("") || flat.equals(""))){
                boolean b = city.length() < 20 && street.length() < 30;
                if (b){
                    try {
                        return pjp.proceed();
                    } catch (Throwable throwable) {
                        logger.error(throwable);
                        throw new ServiceException(throwable);
                    }
                }else {
                    return new RequestResult(Navigation.FORWARD, Path.ERROR, HttpServletResponse.SC_BAD_REQUEST);
                }
            }else {
                return new RequestResult(Navigation.FORWARD, Path.ERROR, HttpServletResponse.SC_BAD_REQUEST);
            }
        }else{
            return new RequestResult(Navigation.FORWARD, Path.ERROR, HttpServletResponse.SC_BAD_REQUEST);
        }

    }

}

