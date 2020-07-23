package by.javatr.cafe.aspectj.log;

import by.javatr.cafe.container.annotation.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Component
public aspect LoggingAspect {

    Logger logger = LogManager.getLogger(getClass());

    @Pointcut("@annotation(by.javatr.cafe.aspectj.log.annotation.LogException)")
    public void log(){}


    @Around(value = "log()", argNames = "pjp,point")
    public Object logging(ProceedingJoinPoint pjp, JoinPoint point){
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            logger.error("asdqwe");
        }

        return null;
    }

}
