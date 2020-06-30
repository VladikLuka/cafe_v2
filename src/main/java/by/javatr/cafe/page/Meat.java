package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;

public class Meat implements Page {

    private final String ACCESS = AccessLevel.GUEST;

    String path = "/meat";

    public boolean thisURL(String url){
        return url.matches("/meat");
    }

}
