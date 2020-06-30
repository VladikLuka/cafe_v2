package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;

public class Checkout implements Page {

    private final String ACCESS = AccessLevel.USER;

    String path = "/checkout";
    int id;

    public boolean thisURL(String url){
        return url.matches("/checkout/[\\d]");
    }

}
