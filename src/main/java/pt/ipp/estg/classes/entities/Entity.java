package pt.ipp.estg.classes.entities;

import pt.ipp.estg.classes.Division;

public class Entity {

    private String name;
    private int health;
    private int power;

    protected Division currentDivision; // protected para quem for usar, ambos PLAYER/ENEMY tem metodos de movimentacao diferentes

    public Entity(String name, int health, int power, Division currentDivision) {
        this.name = name;
        this.health = health;
        this.power = power;
        this.currentDivision = currentDivision;
    }

    public String getName() {
        return name;
    }

    public Integer getPower() {
        return power;
    }

    protected void setPower(Integer power) {
        this.power = power;
    }

    public Integer getHealth() {
        return health;
    }

    public Division getCurrentDivision() {
        return currentDivision;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    protected void heal(int heal) {
        this.health += heal;
    }
}
