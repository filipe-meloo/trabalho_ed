/**
 * This class represents an enemy in the game. An enemy is a type of entity
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.classes.entities;

import Exceptions.EmptyCollectionException;
import Exceptions.NoSuchElementException;
import Structures.ArrayList;
import Structures.ArrayUnorderedList;
import pt.ipp.estg.classes.mission.Division;

import java.util.Random;

public class Enemy extends Entity {

    public Enemy(String name, Integer power, Integer health, Division division) {
        super(name, health, power, division);
    }


    /**
     * Moves the enemy to a random division. The enemy will move to one of the
     * divisions that are one or two steps away from its current division.
     *
     * @return true if the enemy moved, false otherwise
     */
    public boolean moveRandomly()  {
        ArrayUnorderedList<Division> possibleMoves = new ArrayUnorderedList<>();

        for (Division division : this.currentDivision.getNeighbours()) {
            possibleMoves.add(division);

            for (Division secondNeighbor : division.getNeighbours()) {
                try {
                    if (!possibleMoves.contains(secondNeighbor)) {
                        possibleMoves.add(secondNeighbor);
                    }
                } catch (EmptyCollectionException ignored) {
                    possibleMoves.add(secondNeighbor);
                }
            }
        }

        try {
            possibleMoves.remove(this.currentDivision);
        } catch (EmptyCollectionException | NoSuchElementException ignored) {
        }

        Random random = new Random();
        int divisionIndex = random.nextInt(0, possibleMoves.size() - 1);
        this.currentDivision = possibleMoves.get(divisionIndex);
        //print("moved into " + this.currentDivision.getName());

        return true;
    }
}
