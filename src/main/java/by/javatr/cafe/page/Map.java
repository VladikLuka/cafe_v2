package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;

public class Map implements Page {

    private final String ACCESS = AccessLevel.GUEST;


    String path = "/map";

    public boolean thisURL(String url){
        return url.matches("/map");
    }

}
