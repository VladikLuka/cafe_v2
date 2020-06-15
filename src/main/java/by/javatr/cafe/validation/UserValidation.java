package by.javatr.cafe.validation;

public class UserValidation {

    public static boolean isValidMail(String str){
        return str.matches("^([a-zA-Z0-9_-]+\\.)*[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*\\.[a-zA-Z]{2,6}");
    }

    public static boolean isValidPhoneNumber(String str){
        return str.matches("^\\+\\d{11,}");
    }

    public static boolean isValidName(String str){
        return str.matches("[A-Za-zА-Я-а-я]{2,30}");
    }


    public static boolean isValidSurname(String str){
        return str.matches("[A-Za-zА-Я-а-я]{2,30}");
    }

    public static boolean isValidPassword(String str){
        return str.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s){8,}");
    }
}
