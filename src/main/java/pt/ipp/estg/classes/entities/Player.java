/**
 * This class represents the player in the game. The player is a type of entity
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.classes.entities;

import Exceptions.EmptyCollectionException;
import Exceptions.NoSuchElementException;
import Structures.ArrayList;
import Structures.ArrayStack;
import Structures.LinkedQueue;
import pt.ipp.estg.classes.items.MedkitItem;
import pt.ipp.estg.classes.items.VestItem;
import pt.ipp.estg.classes.mission.Division;
import pt.ipp.estg.classes.items.UsableAbstractItem;
import pt.ipp.estg.exceptions.InvalidPlayerException;
import pt.ipp.estg.exceptions.InventoryFullException;
import pt.ipp.estg.exceptions.ItemNullException;

public class Player extends Entity {

    private final int INVENTORY_SIZE = 10;

    private static final String NAME = "Tó Cruz"; //static para ser acedida antes da superclasse e o player vai ser sempre o To Cruz tambem

    private ArrayStack<UsableAbstractItem> inventory;

    private ArrayStack<Division> path;
    private LinkedQueue<String> actionsHistory;

    public Player(Integer health, Integer power) {
        this(health, power, new ArrayStack<>());
        this.actionsHistory = new LinkedQueue<>();
        this.path = new ArrayStack<>();
    }

    public Player(Integer health, Integer power, ArrayStack<UsableAbstractItem> inventory) {
        super(NAME, health, power, null);
        this.inventory = inventory;
        this.actionsHistory = new LinkedQueue<>();
        this.path = new ArrayStack<>();
    }

    /**
     * Retrieves the player's inventory.
     *
     * @return An ArrayStack containing the UsableAbstractItems in the player's inventory.
     */
    public ArrayStack<UsableAbstractItem> getInventory() {
        return inventory;
    }


    /**
     * Retrieves the number of items currently in the player's inventory.
     *
     * @return An Integer representing the count of UsableAbstractItems in the inventory.
     */
    public Integer getItemCount() {
        return this.inventory.size();
    }

    /**
     * Sets the player's inventory to the given ArrayStack.
     * <p>
     * If the given ArrayStack contains more than {@link #INVENTORY_SIZE} items, an
     * {@link InventoryFullException} is thrown.
     *
     * @param inventory The ArrayStack containing the UsableAbstractItems to set in the inventory.
     * @throws InventoryFullException If the given ArrayStack contains more than {@link #INVENTORY_SIZE} items.
     */
    public void setInventory(ArrayStack<UsableAbstractItem> inventory) {
        if (inventory.size() > INVENTORY_SIZE) {
            throw new InventoryFullException(String.format("Cannot set an inventory with more than %d items", INVENTORY_SIZE));
        }

        this.inventory = inventory;
    }

    /**
     * Adds the given UsableAbstractItem to the player's inventory.
     * <p>
     * If the item is a VestItem, it is used immediately.
     * <p>
     * If the inventory is not full, the item is added to it.
     * <p>
     * If the inventory is full, the method returns false.
     *
     * @param item The UsableAbstractItem to add to the inventory.
     * @return true if the item was added to the inventory, false otherwise.
     */
    public boolean addItem(UsableAbstractItem item) {
        if (item instanceof VestItem) {
            print("Oh, a vest! Let's use it!");
            item.use(this);
            return true;
        }

        if (this.inventory.size() < INVENTORY_SIZE) {
            this.inventory.push(item);
            print("Picked up this: " + item.getName());
            return true;
        }

        return false;
    }

    /**
     * Tries to use an item from the player's inventory.
     * <p>
     * If the inventory is empty, the method prints an error message and returns false.
     * <p>
     * If the item is null, an {@link ItemNullException} is thrown.
     * <p>
     * Otherwise, the item is used on the player and the method returns true.
     *
     * @return true if an item was used, false otherwise.
     * @throws ItemNullException If the item is null.
     */
    public boolean useItem() {

        UsableAbstractItem item = null;
        try {
            item = this.inventory.peek();
        } catch (EmptyCollectionException ignored) {
            System.out.println("Empty");
        }

        if (item == null) {
            print("Uh-oh! I don't have any item to use!");
            throw new ItemNullException("Item is null");
        }

        if (getItemCount() == 0) {
            print("Uh-oh! I don't have any item to use!");
            return false;
        }

        item.use(this);
        return true;
    }

    /**
     * Tries to move the player to the given division.
     * <p>
     * If the player is already in the given division, the method prints an error message and returns.
     * <p>
     * If the given division is null, the method prints an error message and returns.
     * <p>
     * If the given division is not a neighbour of the player's current division, the method prints an error message and returns.
     * <p>
     * Otherwise, the player is moved to the given division, the division is added to the player's path and the method calls {@link #checkRoomForItems()}.
     *
     * @param division The division to move the player to.
     */
    public void moveTo(Division division) {
        if (this.currentDivision == null) {
            this.currentDivision = division;
            print("I moved into " + this.currentDivision.getName());
            path.push(this.currentDivision);
            checkRoomForItems();
            return;
        }

        if (division == null) {
            print("I don't know where division " + division.getName() + " is!");
            return;
        }

        if (division.getName().equals(this.currentDivision.getName())) {
            print("Can't Move! Already in " + this.currentDivision.getName());
            return;
        }

        try {
            if (division.getNeighbours().contains(this.currentDivision)) {
                this.currentDivision = division;
                print("I moved into " + this.currentDivision.getName());
                path.push(this.currentDivision);
                checkRoomForItems();
            }
        } catch (EmptyCollectionException ignored) {
        }

    }

    /**
     * Checks the current division for items and attempts to collect them.
     * <p>
     * If the current division contains items, iterates through each item and checks
     * if the item's location matches the current division. If so, the item is
     * removed from the division's item list. VestItems are used immediately,
     * while other items are added to the player's inventory.
     * <p>
     * Prints a message when an item is found and collected.
     */
    private void checkRoomForItems() {
        if (this.currentDivision.getItems().isEmpty()) {
            return;
        }

        ArrayList<UsableAbstractItem> items = this.currentDivision.getItems();
        for (UsableAbstractItem item : items) {
            if (item.getLocation() == this.currentDivision) {
                try {
                    this.currentDivision.getItems().remove(item);

                    if (item instanceof VestItem) {
                        item.use(this);
                    } else {
                        this.inventory.push(item);
                    }

                    print("Found this " + item.getName() + " inside " + this.currentDivision.getName());
                } catch (EmptyCollectionException | NoSuchElementException ignored) {
                }
            }
        }
    }

    /**
     * Retrieves the size of the player's back path.
     * <p>
     * This method returns the number of divisions in the player's path stack,
     * representing the count of places the player has visited.
     *
     * @return An integer representing the size of the back path.
     */
    public int getBackPathSize() {
        return path.size();
    }

    /**
     * Retrieves the player's back path.
     * <p>
     * This method returns the {@link ArrayStack} containing the {@link Division}s the player has visited.
     * <p>
     * The back path is a stack of divisions, where the top element is the most recent division visited.
     * <p>
     * The {@link ArrayStack} is a copy of the player's internal path stack.
     *
     * @return An {@link ArrayStack} containing the player's back path.
     */
    public ArrayStack<Division> getPath() {
        return path;
    }

    /**
     * Moves the player back to the previously visited division.
     * <p>
     * This method attempts to pop the most recent division from the player's path stack
     * and move the player to that division.
     * <p>
     * If the path stack is empty, indicating there are no previous divisions to return to,
     * an error message is printed.
     */
    public void moveBack() {
        try {
            moveTo(path.pop());
        } catch (EmptyCollectionException e) {
            print("I don't have any division to move back!");
        }
    }

    /**
     * Retrieves the player's action history.
     * <p>
     * This method returns the {@link LinkedQueue} containing the player's action history.
     * <p>
     * The action history is a queue of strings, where each string represents an action
     * performed by the player. The most recent action is at the front of the queue.
     *
     * @return A {@link LinkedQueue} containing the player's action history.
     */
    public LinkedQueue<String> getActionsHistory() {
        return actionsHistory;
    }

    /**
     * Retrieves the last division the player visited.
     * <p>
     * This method peeks at the top element of the player's path stack, which is the most recent division the player visited.
     * <p>
     * If the path stack is empty, an {@link EmptyCollectionException} is thrown.
     *
     * @return The last division the player visited.
     * @throws EmptyCollectionException If the path stack is empty.
     */
    public Division getLastPlaceVisited() throws EmptyCollectionException {
        return path.peek();
    }

    /**
     * Checks if the player is in a valid state.
     * <p>
     * Checks if the player's health is above 0 and if the player's power is above 0.
     * If the player is in an invalid state, an {@link InvalidPlayerException} is thrown.
     *
     * @return true if the player is valid, false otherwise.
     * @throws InvalidPlayerException If the player is not valid.
     */
    public boolean isValid() {
        if (this.getHealth() <= 0) {
            print("Avenge me! I'm dead!");
            throw new InvalidPlayerException("Player is dead");
        }

        if (this.getPower() <= 0) {
            throw new InvalidPlayerException("Player power can't be less than 0.");
        }

        return true;
    }

    /**
     * Retrieves the player's tag.
     * <p>
     * The player's tag is a string in the format
     * <code>[name - health &hearts;]</code>, where <code>name</code> is the player's name and
     * <code>health</code> is the player's current health.
     * <p>
     * If the player's health is greater than 100, a star (&#x273F;) is added after the heart (&hearts;)
     * symbol, indicating that the player has a shield.
     * <p>
     * This method is overridden from the superclass.
     *
     * @return The player's tag.
     */
    @Override
    public String getTag() {

        if (this.getHealth() > 100) {
            return "[" + this.getName() + " - " + this.getHealth().toString() + " ♥ | " + (this.getHealth()-100) + "✸] ";
        }

        return "[" + this.getName() + " - " + this.getHealth().toString() + " ♥] ";
    }
}
