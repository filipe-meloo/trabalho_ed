package pt.ipp.estg.classes.items;

public class MedkitItem extends Item {

    private static final int MEDKIT_POINTS = 25;

    public MedkitItem(String location) {
        super("Medkit", location);
    }

    public int getPoints() {
        return MEDKIT_POINTS;
    }

    public void usar() {
        System.out.println("Usei um medkit");
    }

}
