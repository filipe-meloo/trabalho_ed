/**
 * This class manages exceptions and logs them for later analysis.
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.exceptions;

import Structures.ArrayList;

import java.time.LocalDateTime;

public class ExceptionManager {

    private static final ExceptionManager instance = new ExceptionManager();

    private static final ArrayList<Exception> exceptions = new ArrayList<>();
    private static final ArrayList<String> logsExceptions = new ArrayList<>();

    private ExceptionManager() {

    }

    /**
     * Retrieves the singleton instance of the ExceptionManager class.
     *
     * @return The singleton instance of ExceptionManager.
     */
    public static ExceptionManager getInstance() {
        return instance;
    }

    /**
     * Adds the given Exception to the list of exceptions stored in this
     * ExceptionManager and logs a message about the exception.
     *
     * @param e the Exception to log and store in this ExceptionManager.
     */
    public synchronized void addException(Exception e) {
        exceptions.add(e);

        String initialMessage = "Exception Occurred [%s]: %s";
        String formattedMessage = String.format(initialMessage, LocalDateTime.now(), e.getMessage());

        logsExceptions.add(formattedMessage);
    }

    /**
     * Returns a copy of the list of exceptions stored in this ExceptionManager.
     *
     * @return A copy of the list of exceptions.
     */
    public static ArrayList<Exception> getExceptions() {
        ArrayList<Exception> returnCopy = new ArrayList<>();
        for (Exception e : exceptions) {
            returnCopy.add(new Exception(e));
        }
        return returnCopy;
    }

    /**
     * Returns a copy of the list of exception log messages stored in this ExceptionManager.
     *
     * @return A copy of the list of exception log messages.
     */
    public static ArrayList<String> getLogsExceptions() {
        ArrayList<String> returnCopy = new ArrayList<>();
        for (String l : logsExceptions) {
            returnCopy.add(l);
        }
        return returnCopy;
    }

    /**
     * Returns the number of exceptions stored in this ExceptionManager.
     *
     * @return An integer representing the size of the exceptions list.
     */
    public static int getExceptionsSize() {
        return exceptions.size();
    }
}
