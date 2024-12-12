package pt.ipp.estg.classes.entities;

import Exceptions.EmptyCollectionException;
import Structures.ArrayStack;
import pt.ipp.estg.classes.Division;
import pt.ipp.estg.classes.items.Item;
import pt.ipp.estg.classes.items.MedkitItem;
import pt.ipp.estg.classes.items.VestItem;
import pt.ipp.estg.exceptions.InventoryFullException;
import pt.ipp.estg.exceptions.ItemNullException;

public class Player extends Entity{

    private final int INVENTORY_SIZE = 10;

    private static final String NAME = "Tó Cruz"; //static para ser acedida antes da superclasse e o player vai ser sempre o To Cruz tambem

    private ArrayStack<Item> inventory;

    public Player(Integer health, Integer power) {
        this(health, power, new ArrayStack<>());
    }

    public Player(Integer health, Integer power, ArrayStack<Item> inventory) {
        super(NAME, health, power, null);
        this.inventory = inventory;
    }

    public ArrayStack<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayStack<Item> inventory) {
        if (inventory.size() > INVENTORY_SIZE) {
            throw new InventoryFullException(String.format("Cannot set an inventory with more than %d items", INVENTORY_SIZE));
        }

        this.inventory = inventory;
    }

    public boolean addItem(Item item) {
        if (this.inventory.size() < INVENTORY_SIZE) {
            this.inventory.push(item);
            return true;
        }
        return false;
    }

    public boolean useItem() throws EmptyCollectionException {
        Item item = this.inventory.pop();

        if (item == null)
            throw new ItemNullException("Item is null");

        switch (item.getClass().getSimpleName()) {
            case MedkitItem.class.getSimpleName():
                if (getHealth() + ((MedkitItem) item).getPoints() > 100) {
                    this.heal(100 - getHealth());
                }
                this.heal(((MedkitItem) item).getPoints());
                break;
            case VestItem.class.getSimpleName():
                this.heal(((VestItem) item).getPoints());
                break;
        }



        return true;
    }

    public Integer getItemCount() {
        return this.inventory.size();
    }

    public boolean moveTo(Division division) throws EmptyCollectionException {
        if (division == null) {
            return false;
        }

        if (division.getName().equals(this.currentDivision.getName())) {
            return false;
        }

        if (division.getNeighbors().contains(this.currentDivision)) {
            this.currentDivision = division;
            return true;
        }
        return false;
    }

}
