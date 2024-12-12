package pt.ipp.estg.exceptions;

import Structures.ArrayList;

import java.time.LocalDateTime;

public class ExceptionManager {

    private static ArrayList<Exception> exceptions = new ArrayList<>();
    private static ArrayList<String> logsExceptions = new ArrayList<>();

    private static String initialMessage = "Exception Occured [%s]: %s";

    public ExceptionManager() {

    }

    public static void addException(Exception e) {
        exceptions.add(e);

        String formattedMessage = String.format(initialMessage, LocalDateTime.now(), e.getMessage());
        logsExceptions.add(formattedMessage);
    }

    public static ArrayList<Exception> getExceptions() {
        return exceptions;
    }

}
