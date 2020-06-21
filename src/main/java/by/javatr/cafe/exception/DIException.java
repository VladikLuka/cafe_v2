package by.javatr.cafe.exception;

public class DIException extends Exception{
    public DIException(String message) {
        super(message);
    }

    public DIException(String message, Throwable cause) {
        super(message, cause);
    }

    public DIException(Throwable cause) {
        super(cause);
    }

}
