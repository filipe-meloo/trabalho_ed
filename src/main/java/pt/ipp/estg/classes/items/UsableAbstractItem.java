package pt.ipp.estg.classes.items;

import pt.ipp.estg.classes.Division;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Division getLocation() {
        return division;
    }

    @Override
    public ItemType getType() {
        return type;
    }
}
