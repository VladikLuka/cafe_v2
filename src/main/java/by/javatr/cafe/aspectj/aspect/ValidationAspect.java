package by.javatr.cafe.aspectj.aspect;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.service.impl.UserService;
import by.javatr.cafe.util.Cache;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.exception.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
@Aspect
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

    @Around(value = "changePass()", argNames = "pjp,point")
    public Object changePassword(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {
        logger.info("ASPECT VALID PASS");
        Object[] args = point.getArgs();
        final RequestContent content = (RequestContent) args[0];
        String old_pass = content.getRequestParam("old_pass");
        String new_pass = content.getRequestParam("new_pass");

        if(old_pass == null || new_pass == null) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        if(old_pass.equals(new_pass)) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error("ChangePass exception", throwable);
            throw new ServiceException(throwable);
        }

    }

    // Validation empty id
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
                return new RequestResult(Navigation.FORWARD, Path.FRW_ERROR, HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        return new RequestResult(Navigation.FORWARD, Path.FRW_ERROR, HttpServletResponse.SC_BAD_REQUEST);
    }


    @Around(value = "AddAddress()", argNames = "pjp,point")
    public Object addAddress(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {
        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];
        RequestResult result = new RequestResult(Navigation.FORWARD, Path.FRW_ERROR, HttpServletResponse.SC_BAD_REQUEST);

        Cache cache =(Cache) BeanFactory.getInstance().getBean("cache");

        Integer user_id = (Integer) content.getSessionAttr(SessionAttributes.USER_ID);

        if(user_id == null){
            return result ;
        }

        if(cache.getUser(user_id).getRole().ordinal() > Role.getRoleByName(AccessLevel.USER).ordinal()){
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
                    return new RequestResult(Navigation.FORWARD, Path.FRW_ERROR, HttpServletResponse.SC_BAD_REQUEST);
                }
            }else {
                return new RequestResult(Navigation.FORWARD, Path.FRW_ERROR, HttpServletResponse.SC_BAD_REQUEST);
            }
        }else{
            return new RequestResult(Navigation.FORWARD, Path.FRW_ERROR, HttpServletResponse.SC_BAD_REQUEST);
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

