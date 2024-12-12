package pt.ipp.estg.classes;

import pt.ipp.estg.enums.TargetType;

public class Target {

    private String division;
    private String type;

    public Target(String division, TargetType type) {
        this.division = division;
        this.type = type;  }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
