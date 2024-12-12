package pt.ipp.estg.exceptions;

public class IONotRecognizedException extends RuntimeException {
    public IONotRecognizedException(String message) {
        super(message);
        ExceptionManager.addException(this);
    }
}
