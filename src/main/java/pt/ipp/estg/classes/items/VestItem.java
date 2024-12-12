package pt.ipp.estg.classes.items;

import pt.ipp.estg.classes.Division;
import pt.ipp.estg.classes.entities.Player;
import pt.ipp.estg.enums.ItemType;

public class VestItem extends UsableAbstractItem {

    private static final int VEST_POINTS = 50;
    private static final String NAME = "Colete";
    private static final int MAX_STACK = 2;

    public VestItem(Division division) {
        super(NAME, division, ItemType.ITEM_VEST);
    }

    @Override
    public int getPoints() {
        return VEST_POINTS;
    }

    @Override
    public int getMaxStack() {
        return 0;
    }

    @Override
    public void use(Player player) {
        player.heal(getPoints());
    }

}
