/**
 * This class represents an item in the game. An item is a type of entity
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.classes.items;

import pt.ipp.estg.classes.mission.Division;
import pt.ipp.estg.enums.ItemType;

public class Item {

    private String name;
    private Division division;
    private ItemType type;
    public Item(String name, Division division) {
        this.name = name;
        this.division = division;
    }

    /**
     * Returns the name of this item.
     *
     * @return the name of this item
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this item.
     *
     * @param name the new name to be set for this item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the division in which this item is located.
     *
     * @return the division in which this item is located
     */
    public Division getLocation() {
        return division;
    }

    /**
     * Sets the division in which this item is located.
     *
     * @param division the division in which this item is to be located
     */
    public void setLocation(Division division) {
        this.division = division;
    }

    /**
     * Returns the type of this item.
     *
     * @return the type of this item
     */
    public ItemType getType() {
        return type;
    }
}
