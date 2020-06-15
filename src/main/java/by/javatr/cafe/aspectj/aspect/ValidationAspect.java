package by.javatr.cafe.aspectj.aspect;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.exception.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Aspect
public class ValidationAspect {

    private static final Logger logger = LogManager.getLogger(ValidationAspect.class);

    @Before(value = "execution(protected * by.javatr.cafe.controller.Controller.processRequest(..))")
    public void test(){
        System.out.println("AspectJ WORK CORRECT");
    }


    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.AddToCart.execute(..))")
    public void addToCart(){}


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
    @Pointcut("execution(public * by.javatr.cafe.controller.command.impl.AddAddress.execute(..))")
    public void AddAddress(){}

    @Around(value = "AddAddress()", argNames = "pjp,point")
    public Object addAddress(ProceedingJoinPoint pjp, JoinPoint point) throws ServiceException {
        Object[] args = point.getArgs();
        RequestContent content = (RequestContent) args[0];
        String method = content.getMethod();
        RequestResult result;
        result = new RequestResult(Navigation.FORWARD, Path.FRW_ERROR, HttpServletResponse.SC_BAD_REQUEST);
        if(Role.getRoleByName((String) content.getSessionAttr(SessionAttributes.ACCESS_LEVEL)).ordinal() > Role.getRoleByName(AccessLevel.USER).ordinal()){
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



}

