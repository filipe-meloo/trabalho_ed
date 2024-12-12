package pt.ipp.estg.exceptions;

public class ItemNullException extends NullPointerException {
    public ItemNullException(String message) {
        super(message);
        ExceptionManager.addException(this);
    }
}
