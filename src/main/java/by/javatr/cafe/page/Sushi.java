package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;

public class Sushi implements Page {

    private final String ACCESS = AccessLevel.GUEST;

    String path = "/sushi";

    public boolean thisURL(String url){
        return url.matches("/sushi");
    }
}
