/**
 * This class represents a medical kit item in the game. A medical kit item is a type of item
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.classes.items;

import pt.ipp.estg.classes.mission.Division;
import pt.ipp.estg.classes.entities.Player;
import pt.ipp.estg.enums.ItemType;

public class MedkitItem extends UsableAbstractItem {

    private static final int MEDKIT_POINTS = 25;
    private static final String NAME = "Medkit";
    private static final int MAX_STACK = 5;

    public MedkitItem(Division division) {
        super(NAME, division, ItemType.ITEM_MEDKIT);
    }

    /**
     * Retrieves the number of points this MedkitItem provides.
     *
     * @return the integer value representing the healing points of the MedkitItem.
     */
    public int getPoints() {
        return MEDKIT_POINTS;
    }


    /**
     * Retrieves the maximum number of MedkitItems that can be stacked on top of each other.
     *
     * @return the maximum number of MedkitItems that can be stacked on top of each other.
     */
    public int getMaxStack() {
        return MAX_STACK;
    }

    /**
     * Heals the player up to their maximum health.
     *
     * <p>
     * If the player's health plus the points this MedkitItem provides exceeds the player's maximum health, the player is healed up to their maximum health, and the excessive points are discarded.
     * </p>
     *
     * @param player the player to heal.
     */
    @Override
    public void use(Player player) {
        if (player.getHealth() + getPoints() <= player.getMaxHealth()) {
            player.heal(player.getMaxHealth() - player.getHealth());
        } else {
            player.print("I'm on max health!");
            return;
        }

        player.heal(getPoints());
        player.print("Item used: " + getName());
    }
}
