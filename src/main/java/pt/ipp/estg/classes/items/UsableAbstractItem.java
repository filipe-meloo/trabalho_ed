/**
 * This class represents an abstract item in the game. An abstract item is a type of item
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.classes.items;

import pt.ipp.estg.classes.mission.Division;
import pt.ipp.estg.enums.ItemType;
import pt.ipp.estg.interfaces.Item;

public abstract class UsableAbstractItem implements Item {

    private String name;
    private Division division;
    private ItemType type;

    public UsableAbstractItem(String name, Division division, ItemType type) {
        this.name = name;
        this.division = division;
        this.type = type;
    }

    /**
     * Retrieves the name of this item.
     *
     * @return the name of this item as a String.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Retrieves the division location of this item.
     *
     * @return the Division in which this item is located.
     */
    @Override
    public Division getLocation() {
        return division;
    }

    /**
     * Retrieves the type of this item.
     *
     * @return the ItemType of this item as an ItemType enum.
     */
    @Override
    public ItemType getType() {
        return type;
    }
}
