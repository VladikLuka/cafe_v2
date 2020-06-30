package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;

public class User implements Page {

    private final String ACCESS = AccessLevel.USER;

    String path = "/user";

    public boolean thisURL(String url){
        return url.matches("/user");
    }
}
