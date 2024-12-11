package pt.ipp.estg.classes;

import Exceptions.EmptyCollectionException;
import Structures.ArrayStack;
import pt.ipp.estg.classes.items.Item;

public class Player {

    private final String NAME = "Tó Cruz";

    private int health;
    private int power;

    private ArrayStack<Item> inventory;

    public Player(Integer health, Integer power) {
        this(health, power, new ArrayStack<>());
    }

    public Player(Integer health, Integer power, ArrayStack<Item> inventory) {
        this.health = health;
        this.power = power;
        this.inventory = inventory;
    }

    public String getName() {
        return NAME;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public ArrayStack<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayStack<Item> inventory) {
        this.inventory = inventory;
    }

    public void addItem(Item item) {
        this.inventory.push(item);
    }

    public Item useItem() throws EmptyCollectionException {
        return this.inventory.pop();
    }

    public Integer getItemCount() {
        return this.inventory.size();
    }


}
