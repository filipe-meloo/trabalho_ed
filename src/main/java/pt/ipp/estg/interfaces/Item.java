/**
 * This interface defines the methods that an item must implement.
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.interfaces;

import pt.ipp.estg.classes.mission.Division;
import pt.ipp.estg.classes.entities.Player;
import pt.ipp.estg.enums.ItemType;

public interface Item {
    String getName();
    Division getLocation();
    ItemType getType();
    int getPoints();
    int getMaxStack();
    void use(Player player);
}
