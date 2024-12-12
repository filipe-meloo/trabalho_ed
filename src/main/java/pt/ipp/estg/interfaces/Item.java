package pt.ipp.estg.interfaces;

import pt.ipp.estg.classes.Division;
import pt.ipp.estg.enums.ItemType;

public interface Item {
    String getName();
    Division getLocation();
    ItemType getType();
    int getPoints();
    void use();
}
