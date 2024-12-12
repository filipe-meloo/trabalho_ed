package pt.ipp.estg.classes.entities;

import pt.ipp.estg.classes.Division;

import java.util.Random;

public class Enemy extends Entity {

    public Enemy(String name, Integer power, Integer health, Division division) {
        super(name, health, power, division);
    }

    public boolean moveRandomly() {
        int divisionIndex = new Random().nextInt(0, this.currentDivision.getNeighbors().size() - 1);
        this.currentDivision = this.currentDivision.getNeighbors().get(divisionIndex);
        return true;
    }
}
