package pt.ipp.estg.exceptions;

public class PlayerDeathException extends RuntimeException {
    public PlayerDeathException(String message) {
        super(message);
        ExceptionManager.getInstance().addException(this);
    }
}
