package pt.ipp.estg.classes.items;

public class VestItem extends Item {

    private static final int VEST_POINTS = 50;

    public VestItem(String location) {
        super("Colete", location);
    }

    public Integer getPoints() {
        return VEST_POINTS;
    }

    public void usar() {
        System.out.println("Usei um vest");
    }

}
