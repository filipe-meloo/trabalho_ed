/**
 * This class represents a division in the map.
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.classes.mission;

import Exceptions.EmptyCollectionException;
import Structures.ArrayList;
import pt.ipp.estg.classes.entities.Enemy;
import pt.ipp.estg.classes.items.UsableAbstractItem;

public class Division {

    private String name;
    private ArrayList<UsableAbstractItem> items;
    private boolean isExit;
    private ArrayList<Division> neighbours;

    public Division(String name) {
        this(name, new ArrayList<>(), new ArrayList<>(), false);
    }

    public Division(String name, ArrayList<UsableAbstractItem> items, ArrayList<Enemy> enemies, boolean isExit) {
        this.name = name;
        this.items = (items == null) ? new ArrayList<>() : items;
        this.isExit = isExit;
        this.neighbours = new ArrayList<>();
    }

    /**
     * Returns the name of this Division.
     *
     * @return the name of this Division
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this Division.
     *
     * @param name the name to which to set this Division
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the list of items in this Division.
     *
     * @return An ArrayList of UsableAbstractItem objects representing the items present in this Division.
     */
    public ArrayList<UsableAbstractItem> getItems() {
        return items;
    }

    /**
     * Returns true if this Division has any items in it, false otherwise.
     *
     * @return true if this Division has items, false otherwise
     */
    public boolean hasItems() {
        return !items.isEmpty();
    }

    /**
     * Sets the list of items in this Division to the given list.
     *
     * @param items The new list of items to set in this Division.
     */
    public void setItems(ArrayList<UsableAbstractItem> items) {
        this.items = items;
    }

    /**
     * Adds the given UsableAbstractItem to the list of items in this Division.
     *
     * @param item the item to add to this Division
     */
    public void addItem(UsableAbstractItem item) {
        this.items.add(item);
    }

    /**
     * Sets whether this Division is an exit or not.
     *
     * @param isExit true if this Division is an exit, false otherwise
     */
    public void setExit(boolean isExit) {
        this.isExit = isExit;
    }

    /**
     * Adds the given Division to the list of neighbors of this Division.
     *
     * <p>If the given Division is not already in the list of neighbors, it is added.
     *
     * @param division The Division to add as a neighbor of this Division.
     *
     * @throws EmptyCollectionException if the list of neighbors is empty.
     */
    public void addNeighbor(Division division) throws EmptyCollectionException {
        if (neighbours.isEmpty()) {
            neighbours.add(division);
        } else {
            try {
                if (!neighbours.contains(division)) {
                    neighbours.add(division);
                }
            } catch (EmptyCollectionException ignored) {

            }
        }
    }

    /**
     * Checks if this Division is connected to the given Division.
     *
     * @param division The Division to check for a connection to.
     * @return true if this Division is connected to the given Division, false otherwise
     * @throws EmptyCollectionException if the list of neighbors of this Division is empty
     */
    public boolean isConnectedTo(Division division) throws EmptyCollectionException {
        try {
            return neighbours != null && neighbours.contains(division);
        } catch (EmptyCollectionException ignored) {
            return false;
        }
    }

    /**
     * Returns the list of neighbors of this Division.
     *
     * @return An ArrayList of Division objects representing the neighbors of this Division
     */
    public ArrayList<Division> getNeighbours() {
        return neighbours;
    }
}