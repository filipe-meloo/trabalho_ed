package pt.ipp.estg.classes;

public class Enemy {

    private String name;
    private Integer power;
    private String division; //divisao a qual o inimigo pertence inicialmente (pode mover-se depois)

    public Enemy(String name, Integer power) {
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

}
