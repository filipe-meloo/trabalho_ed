package pt.ipp.estg.classes.items;

public class Item {

    private String location;
    private int points;

    public Item(String location, int points) {
        this.location = location;
        this.points = points;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
