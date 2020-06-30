package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;

public class Garnish implements Page {

    private final String ACCESS = AccessLevel.GUEST;


    String path = "/garnish";

    public boolean thisURL(String url){
        return url.matches("/garnish");
    }

}
