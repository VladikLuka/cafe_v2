package by.javatr.cafe.constant;

/**
 * Contains regular expressions in application
 */
public class Regex {

    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$";
    public static final String POS_INTEGER = "[0-9]+";
    public static final String EMAIL = "^([a-zA-Z0-9_-]+\\.)*[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*\\.[a-zA-Z]{2,6}$";
    public static final String NAME = "[A-Za-zА-Я-а-я]{2,30}";
    public static final String PHONE = "^\\+\\d{12}$";

    private Regex() {
    }
}
