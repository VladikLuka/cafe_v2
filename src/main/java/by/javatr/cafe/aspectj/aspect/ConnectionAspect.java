package by.javatr.cafe.aspectj.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
public class ConnectionAspect {


//    @Pointcut("@annotation(by.javatr.cafe.aspectj.annotation.Resources)")
//    public void testCon() { }

    @Around(value = "@annotation(by.javatr.cafe.aspectj.annotation.Resources)", argNames = "pjp,point")
    public Object processCon(ProceedingJoinPoint pjp, JoinPoint point){

        Method[] methods = point.getTarget().getClass().getMethods();

        for (Method method:methods) {
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
        }

        return null;
    }

}
