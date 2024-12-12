package pt.ipp.estg.classes.items;

import pt.ipp.estg.classes.Division;
import pt.ipp.estg.classes.entities.Player;
import pt.ipp.estg.enums.ItemType;

public class MedkitItem extends UsableAbstractItem {

    private static final int MEDKIT_POINTS = 25;
    private static final String NAME = "Medkit";
    private static final int MAX_STACK = 5;

    public MedkitItem(Division division) {
        super(NAME, division, ItemType.ITEM_MEDKIT);
    }

    public int getPoints() {
        return MEDKIT_POINTS;
    }

    public int getMaxStack() {
        return MAX_STACK;
    }

    @Override
    public void use(Player player) {
        if (player.getHealth() + getPoints() > 100) {
            player.heal(100 - player.getHealth());
            return;
        }

        player.heal(getPoints());
    }
}
