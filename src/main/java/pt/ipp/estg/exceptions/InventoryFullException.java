package pt.ipp.estg.exceptions;

public class InventoryFullException extends RuntimeException {
    public InventoryFullException(String message) {
        super(message);
        ExceptionManager.addException(this);
    }
}
