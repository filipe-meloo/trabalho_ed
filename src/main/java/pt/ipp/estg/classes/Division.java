package pt.ipp.estg.classes;

import Structures.ArrayList;
import pt.ipp.estg.classes.entities.Enemy;
import pt.ipp.estg.classes.items.Item;
import pt.ipp.estg.classes.items.UsableAbstractItem;

public class Division {

    private String name;
    private ArrayList<UsableAbstractItem> items;
    private ArrayList<Enemy> enemies;
    private ArrayList<Division> neighbors;
    private boolean isExit;

    public Division(String name) {
        this(name, new ArrayList<>(), new ArrayList<>(), false);
    }

    public Division(String name, ArrayList<UsableAbstractItem> items, ArrayList<Enemy> enemies, boolean isExit) {
        this.name = name;
        this.items = (items == null) ? new ArrayList<>() : items;
        this.enemies = (enemies == null) ? new ArrayList<>() : enemies;
        this.isExit = isExit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<UsableAbstractItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<UsableAbstractItem> items) {
        this.items = items;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean isExit) {
        this.isExit = isExit;
    }

    public void addNeighbor(Division division) {
        this.neighbors.add(division);
    }

    public ArrayList<Division> getNeighbors() {
        return neighbors;
    }

}
