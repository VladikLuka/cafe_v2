package by.javatr.cafe.aspectj.aspect;

import by.javatr.cafe.constant.*;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.*;
import by.javatr.cafe.service.*;
import by.javatr.cafe.service.impl.AddressService;
import by.javatr.cafe.service.impl.UserService;
import by.javatr.cafe.util.Cache;
import by.javatr.cafe.exception.ServiceException;
import com.braintreegateway.util.Http;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Aspect
@Component
public class ValidationAspect {

    private final Logger logger = LogManager.getLogger(getClass());

    @Before(value = "execution(protected * by.javatr.cafe.controller.Controller.processRequest(..))")
    public void test(){
        System.out.println("AspectJ WORK CORRECT");
    }

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.AddAddress.execute(..))")
    public void AddAddress(){}

    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.AddToCart.execute(..))")
    public void addToCart(){}

    @Pointcut("execution(public * by.javatr.cafe.service.impl.OrderService.makeOrder(..))")
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

    @Around(value = "balanceOrderValid()", argNames = "pjp,point")
    public Object balanceOrder(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {
        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        String delivery_time = content.getRequestParam("delivery_time");
        String address_id_str = content.getRequestParam("address");

        if(delivery_time == null || address_id_str == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(!delivery_time.matches("^(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})")){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        Cart cart = (Cart) content.getSessionAttr(SessionAttributes.CART);

        if(cart.getCart() == null){
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

        String name = content.getRequestParam("name");
        String surname = content.getRequestParam("surname");
        String email = content.getRequestParam("email");
        String password = content.getRequestParam("password");
        String phone = content.getRequestParam("phone");

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

        final String stars = content.getRequestParam("stars");
        final String feedback = content.getRequestParam("feedback");
        String order_id_str = content.getRequestParam("order_id");

        if(stars == null || feedback == null || order_id_str == null) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        if(Integer.parseInt(stars) < 1 || Integer.parseInt(stars) > 5) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        if(!order_id_str.matches(Regex.POS_INTEGER)) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        int order_id = Integer.parseInt(order_id_str);

        try {

            IOrderService orderService = (IOrderService) BeanFactory.getInstance().getBean("orderService");

            Order order = orderService.getOrder(new Order(order_id));

            if(order == null){
                return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
            }

            if (order.getUser_id() != (int) content.getSessionAttr(SessionAttributes.USER_ID)) {
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

        String dish_id_str = content.getRequestParam("id");

        if(!dish_id_str.matches(Regex.POS_INTEGER)) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        int dish_id = Integer.parseInt(dish_id_str);

        final Cart cart = (Cart) content.getSessionAttr(SessionAttributes.CART);

        if(cart == null) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        if(cart.getCart() == null) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        if(cart.getCart().isEmpty()) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        IDishService dishService = (IDishService) BeanFactory.getInstance().getBean("dishService");

        Dish dish = dishService.get(dish_id);

        if (!cart.getCart().contains(dish)) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable);
            throw new ServiceException(throwable);
        }

    }


    @Around(value = "delAddress()", argNames = "pjp,point")
    public Object deleteAddress(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {
        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];

        int user_id = (int) content.getSessionAttr(SessionAttributes.USER_ID);


        int id;

        if (content.getRequestParam("id")!=null) {
            final String address_id = content.getRequestParam("id");
            if (address_id.matches(Regex.POS_INTEGER)){
                id = Integer.parseInt(address_id);
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

            User user = userService.find(user_id);
            Address address = addressService.find(new Address(id));

            if(user.getId() != address.getUser_id()){
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
        String old_pass = content.getRequestParam("old_pass");
        String new_pass = content.getRequestParam("new_pass");

        if(old_pass == null || new_pass == null) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        if(old_pass.equals(new_pass)) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        if(!new_pass.matches(Regex.PASSWORD)) {
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
        if (requestParameters.containsKey("id")) {

            id = content.getRequestParam("id");
            try {
                Integer.parseInt(id);
                RequestResult proceed = (RequestResult) pjp.proceed();
                return proceed;
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

        Integer user_id = (Integer) content.getSessionAttr(SessionAttributes.USER_ID);

        if(user_id == null){
            return result ;
        }

        if(userService.find(user_id).getRole().ordinal() > Role.getRoleByName(AccessLevel.USER).ordinal()){
            return result;
        }
        Map<String, String[]> requestParameters = content.getRequestParameters();
        String city = null;
        String street = null;
        String house = null;
        String flat = null;
        if (requestParameters.containsKey("city")) city = content.getRequestParam("city");
        if (requestParameters.containsKey("street")) street = content.getRequestParam("street");
        if(requestParameters.containsKey("house")) house = content.getRequestParam("house");
        if(requestParameters.containsKey("flat")) flat = content.getRequestParam("flat");

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


//    @Around(value = "makeOrderValidation()", argNames = "pjp,point")
//    public Object balanceEnough(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {
//        Object[] args = point.getArgs();
//        Order order =(Order) args[0];
//        User user =(User) args[1];
//        BigDecimal subtract = user.getMoney().subtract(order.getAmount());
//        if(subtract.compareTo(new BigDecimal(0)) <0 ){
//            throw new ServiceException("not enough money");
//        }
//        try {
//           return pjp.proceed();
//        } catch (Throwable throwable) {
//            LogManager.getLogger(UserService.class).error(throwable);
//            throw new ServiceException("Smth went wrong");
//        }
//
//    }

}

