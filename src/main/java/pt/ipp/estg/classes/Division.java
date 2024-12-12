package pt.ipp.estg.classes;

import Exceptions.EmptyCollectionException;
import Structures.ArrayList;
import pt.ipp.estg.classes.entities.Enemy;
import pt.ipp.estg.classes.items.Item;
import pt.ipp.estg.classes.items.UsableAbstractItem;

public class Division {

    private String name;
    private ArrayList<UsableAbstractItem> items;
    private ArrayList<Enemy> enemies;
    private boolean isExit;
    private ArrayList<String> connectedDivisionNames; // Lista de nomes das divisões conectadas a esta divisão

    //private boolean isPlayerInside;

    public Division(String name) {
        this(name, new ArrayList<>(), new ArrayList<>(), false);
    }

    public Division(String name, ArrayList<UsableAbstractItem> items, ArrayList<Enemy> enemies, boolean isExit) {
        this.name = name;
        this.items = (items == null) ? new ArrayList<>() : items;
        this.enemies = (enemies == null) ? new ArrayList<>() : enemies;
        this.isExit = isExit; //Ela é uma saída?
        this.connectedDivisionNames = new ArrayList<>(); // Inicializa a lista de nomes de divisões conectadas
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

    // Novos métodos
    public void addConnectedDivision(String divisionName) throws EmptyCollectionException {
        if (connectedDivisionNames == null) {
            connectedDivisionNames = new ArrayList<>();
        }
        if (!connectedDivisionNames.contains(divisionName)) {
            connectedDivisionNames.add(divisionName);
        }
    }

    public boolean isConnectedTo(String divisionName) throws EmptyCollectionException {
        return connectedDivisionNames != null &&
                connectedDivisionNames.contains(divisionName);
    }

    public ArrayList<String> getConnectedDivisionNames() {
        return connectedDivisionNames;
    }
}