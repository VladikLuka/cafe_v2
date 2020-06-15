package by.javatr.cafe.aspectj.aspect;

import by.javatr.cafe.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ExceptionAspect {

    @Pointcut("execution(public * by.javatr.cafe.dao.repository.impl.MySqlDishRepository.* (..))")
    public void dishRepository(){}

    @AfterThrowing(value = "dishRepository()", throwing = "e")
    public void logDishRep(JoinPoint point , Throwable e){
        final Logger logger = LogManager.getLogger(point.getTarget().getClass());
        logger.error(e);
    }


    @AfterThrowing(pointcut = "execution(public * by.javatr.cafe.dao.repository.impl.MySqlUserRepository.* (..))", throwing = "e")
    public void logUserRep(JoinPoint point , Throwable e) throws DAOException {
        final Logger logger = LogManager.getLogger(point.getTarget().getClass());
        logger.error(e);
        throw new DAOException(e);
    }
}
