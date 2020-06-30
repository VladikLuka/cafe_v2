package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;

public class Drinks implements Page {

    private final String ACCESS = AccessLevel.GUEST;


    String path = "/drink";

    public boolean thisURL(String url){
        return url.matches("/drink");
    }

}
