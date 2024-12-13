/**
 * This class represents a vest item in the game. A vest item is a type of item
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.classes.items;

import pt.ipp.estg.classes.mission.Division;
import pt.ipp.estg.classes.entities.Player;
import pt.ipp.estg.enums.ItemType;

public class VestItem extends UsableAbstractItem {

    private static final int VEST_POINTS = 50;
    private static final String NAME = "Colete";
    private static final int MAX_STACK = 2;

    public VestItem(Division division) {
        super(NAME, division, ItemType.ITEM_VEST);
    }

    /**
     * Gets the number of points the player will receive when using this item.
     *
     * @return The number of points to heal the player, as an int.
     */
    @Override
    public int getPoints() {
        return VEST_POINTS;
    }

    /**
     * Gets the maximum number of items of this type that the player can hold.
     *
     * @return The maximum number of items of this type that the player can hold, as an int.
     */
    @Override
    public int getMaxStack() {
        return 0;
    }

    /**
     * Use the item.
     *
     * <p>This method will heal the player and send a message to the player that an item was used.
     *
     * @param player The player to use the item on.
     */
    @Override
    public void use(Player player) {
        player.heal(getPoints());
        player.print("Item used: " + getName());
    }

}
