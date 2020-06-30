package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;

public class Pizza implements Page {

    private final String ACCESS = AccessLevel.GUEST;

    String path = "/pizza";

    public boolean thisURL(String url){
        return url.matches("/pizza");
    }
}

