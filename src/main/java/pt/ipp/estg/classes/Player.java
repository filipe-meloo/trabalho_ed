package pt.ipp.estg.classes;

import Exceptions.EmptyCollectionException;
import Structures.ArrayStack;
import Structures.Graph;
import pt.ipp.estg.classes.items.Item;

public class Player {

    private final String NAME = "Tó Cruz";

    private int health;
    private int power;
    private Division currentDivision; // Mudança de String para Division

    private ArrayStack<Item> inventory;

    public Player(Integer health, Integer power) {
        this(health, power, new ArrayStack<>());
    }

    public Player(Integer health, Integer power, ArrayStack<Item> inventory) {
        this.health = health;
        this.power = power;
        this.inventory = inventory;
        this.currentDivision = null;
    }

   //Move o jogador para uma nova divisão se a divisão atual permitir o movimento para a divisão desejada.
    public boolean moveTo(Division targetDivision, Graph<Division> building) throws EmptyCollectionException {
        // Verificar se a divisão atual permite movimento para a divisão desejada
        if (currentDivision == null) {
            currentDivision = targetDivision;
            return true;
        }

        // Verificar se a divisão está conectada pelo nome
        if (currentDivision.isConnectedTo(targetDivision.getName())) {
            currentDivision = targetDivision;
            return true;
        }

        return false;
    }

    // Método sobrecarregado para aceitar nome da divisão
    public boolean moveTo(String divisionName, Graph<Division> building) throws EmptyCollectionException {
        // Encontrar a divisão no grafo pelo nome
        for (Division div : building.getVertices()) {
            if (div.getName().equals(divisionName)) {
                return moveTo(div, building);
            }
        }
        return false;
    }

    public Division getCurrentDivision() {
        return currentDivision;
    }
}