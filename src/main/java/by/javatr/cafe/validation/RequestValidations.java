package by.javatr.cafe.validation;

import by.javatr.cafe.controller.content.RequestContent;

public class RequestValidations {

    public static boolean checkParamsAddAddress(RequestContent content) {
        String city = content.getRequestParam("city");
        String street = content.getRequestParam("street");
        String house = content.getRequestParam("house");
        String flat = content.getRequestParam("flat");

        if(city != null && street != null && house != null && flat != null){
            return city.length() < 20 && street.length() < 30;
        }
        return false;
    }
}
