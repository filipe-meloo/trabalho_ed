package pt.ipp.estg.gui;

import java.util.Scanner;

public class Menus {

    public Menus() {
    }

    /**
     * Prints the logo of the application to the standard output.
     */
    protected static void printLogo() {


        System.out.println("\n" +
                "-==-==-==-==-==-==-==-==-==-==-==-==-==-==-\n " +
                "________         _________                   \n" +
                "___  __/_____    __  ____/__________  _______\n" +
                "__  /  _  __ \\   _  /    __  ___/  / / /__  /\n" +
                "_  /   / /_/ /   / /___  _  /   / /_/ /__  /_\n" +
                "/_/    \\____/    \\____/  /_/    \\__,_/ _____/\n" +
                "-==-==-==-==-==-==-==-==-==-==-==-==-==-==-\n");
    }

    /**
     * Clear Screen
     */
    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n\n\n\n\n\n\n\n\n");
    }

    /**
     * Prompts the user to enter a valid option and returns the selected option.
     *
     * @param scan The Scanner instance used to read user input.
     * @return The selected option as an integer.
     */
    protected static int getValidOption(Scanner scan) {
        System.out.print("Opção: ");
        while (!scan.hasNextInt()) {
            scan.next();
        }
        return scan.nextInt();
    }

    /**
     * Pauses the program and waits for the user to press Enter to continue.
     *
     * @param scan The Scanner instance used to read user input.
     */
    protected static void pause(Scanner scan) {
        System.out.println("Pressione Enter para continuar...");
        scan.nextLine();
    }

    /**
     * Prints a divider line to separate sections in the console output.
     */
    protected static void divider() {
        System.out.println("-----------*-----------");
    }

    /**
     * Prompts the user to enter a valid string input and returns the entered string.
     *
     * @param message The prompt message to display to the user.
     * @param scan The Scanner instance used to read user input.
     * @return The entered string.
     */
    protected static String getValidString(String message, Scanner scan) {
        System.out.print(message);
        String input = scan.nextLine().trim();

        while (input.isEmpty()) {
            System.out.println("Input Inválido.");
            System.out.print(message);
            input = scan.nextLine().trim();
        }

        return input;
    }
}
