package by.javatr.cafe.aspectj.aspect;


import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Objects;

@Aspect
public class TestAspect {

    @AfterThrowing( pointcut = "execution(public * by.javatr.cafe.Main.devide(..))", throwing = "e")
    public void dev(JoinPoint point, Throwable e) throws DAOException {
        final Logger logger = LogManager.getLogger(point.getTarget().getClass());
        logger.error("ERROR EX",e );
        throw new DAOException("ARITHMETIC EX",e);
    }

    @Pointcut("execution(public * by.javatr.cafe.Main.testRet(by.javatr.cafe.entity.Dish)) && args(dish,..)")
    public void retVal(Dish dish){}

    @Around(value = "retVal(dish)", argNames = "pjp,dish")
    public Object testR(ProceedingJoinPoint pjp, Dish dish){
        dish.setName("HACKED");
        return dish;
    }

    @Pointcut("execution(public * by.javatr.cafe.dao.repository.impl.MySqlUserRepository.* (..))")
    public void log(){}

    @Pointcut("execution(public void by.javatr.cafe.Main.testAsp (..))")
    public void test(){}


    @Before("test()")
    public void testT(JoinPoint point){
        System.out.println("ok");
    }

    @AfterThrowing("log()")
    public void logUserRepository(JoinPoint point){
        System.out.println(point.getTarget());
    }









}
