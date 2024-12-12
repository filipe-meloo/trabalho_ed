package pt.ipp.estg.classes.items;

import pt.ipp.estg.classes.Division;
import pt.ipp.estg.classes.entities.Player;
import pt.ipp.estg.enums.ItemType;

public class MedkitItem extends UsableAbstractItem {

    private static final int MEDKIT_POINTS = 25;

    public MedkitItem(Division division) {
        super("Medkit", division, ItemType.ITEM_MEDKIT);
    }

    public int getPoints() {
        return MEDKIT_POINTS;
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
