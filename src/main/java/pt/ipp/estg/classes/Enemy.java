package pt.ipp.estg.classes;

public class Enemy {

    private String name;
    private Integer power;
    private Integer health;
    private String division; //divisao a qual o inimigo pertence inicialmente (pode mover-se depois)

    public Enemy(String name, Integer power, Integer health, String division) {
        this.name = name;
        this.power = power;
        this.health = health;
        this.division = division;
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

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

}
