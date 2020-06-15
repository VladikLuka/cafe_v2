package by.javatr.cafe.aspectj.annotation;


import by.javatr.cafe.dao.connection.impl.ConnectionPool;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE;

@Target(value={LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Connect {


}
