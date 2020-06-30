package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.container.BeanFactory;

public class Admin implements Page {

    private final String ACCESS = AccessLevel.ADMIN;

    String path = "/admin";


    public boolean thisURL(String url){
        return url.matches("/admin");
    }
}
