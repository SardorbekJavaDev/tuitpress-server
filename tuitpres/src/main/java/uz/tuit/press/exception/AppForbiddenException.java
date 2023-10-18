package uz.tuit.press.exception;

public class AppForbiddenException extends RuntimeException{
    public AppForbiddenException(String message) {
        super(message);
    }
}
