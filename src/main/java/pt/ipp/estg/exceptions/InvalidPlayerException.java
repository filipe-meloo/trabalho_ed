package pt.ipp.estg.exceptions;

public class InvalidPlayerException extends RuntimeException {
    public InvalidPlayerException(String message) {
        super(message);
        ExceptionManager.addException(this);
    }
}
