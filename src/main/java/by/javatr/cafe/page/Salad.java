package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;

public class Salad implements Page {

    private final String ACCESS = AccessLevel.GUEST;

    String path = "/salad";

    public boolean thisURL(String url){
        return url.matches("/salad");
    }
}
