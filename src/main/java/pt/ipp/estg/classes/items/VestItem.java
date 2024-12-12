package pt.ipp.estg.classes.items;

import pt.ipp.estg.classes.Division;
import pt.ipp.estg.enums.ItemType;

public class VestItem extends UsableAbstractItem {

    private static final int VEST_POINTS = 50;

    public VestItem(Division division) {
        super("Colete", division, ItemType.ITEM_VEST);
    }

    @Override
    public int getPoints() {
        return VEST_POINTS;
    }

    @Override
    public void use() {

    }

}
