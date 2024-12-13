/**
 * This class contains the main method for the game.
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg;

import Exceptions.EmptyCollectionException;
import pt.ipp.estg.gui.InitialMenu;

public class Main {
    /**
     * Main method for the game.
     *
     * This method starts the game by displaying the main menu.
     *
     * @param args Command line arguments. Not used.
     * @throws EmptyCollectionException if the collection of missions is empty
     */
    public static void main(String[] args) throws EmptyCollectionException {
        InitialMenu.displayMainMenu();
    }
}