/**
 * This class represents an entity in the game. An entity is a type of entity
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */

package pt.ipp.estg.classes.entities;

import pt.ipp.estg.classes.mission.Division;

public class Entity {

    private String name;
    private int health;
    private int power;

    private int maxHealth = 0;

    protected Division currentDivision; // protected para quem for usar, ambos PLAYER/ENEMY tem metodos de movimentacao diferentes

    public Entity(String name, int health, int power, Division currentDivision) {
        this.name = name;
        this.health = health;
        this.power = power;
        this.currentDivision = currentDivision;
        this.maxHealth = health;
    }

    /**
     * Gets the name of the entity.
     *
     * @return the name of the entity.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the power level of the entity.
     *
     * @return the power level of the entity as an Integer.
     */
    public Integer getPower() {
        return power;
    }

    /**
     * Sets the power level of the entity.
     *
     * @param power the power level of the entity as an Integer.
     */
    protected void setPower(Integer power) {
        this.power = power;
    }

    /**
     * Gets the current health of the entity.
     *
     * @return the current health of the entity as an Integer.
     */
    public Integer getHealth() {
        return health;
    }

    /**
     * Retrieves the current division the entity is located in.
     *
     * @return the current Division object representing the entity's location.
     */
    public Division getCurrentDivision() {
        return currentDivision;
    }


    /**
     * Determines if the entity is alive.
     *
     * @return true if the entity has health greater than 0, false otherwise.
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    /**
     * Decreases the entity's health by a given amount.
     *
     * If the entity's health falls to 0 or below, the entity is considered dead
     * and a message is printed to the console.
     *
     * @param damage the amount of damage to apply to the entity's health.
     */
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            print("I died!");
        }
    }

    /**
     * Increases the entity's health by a given amount.
     *
     * The entity's health is increased by the given amount, but will not exceed
     * the entity's maximum health.
     *
     * @param heal the amount of health to add to the entity's health.
     */
    public void heal(int heal) {
        this.health += heal;
    }

    /**
     * Gets the maximum health the entity can have.
     *
     * @return the maximum health of the entity as an Integer.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Sets the entity's health to a given value.
     *
     * The entity's health is set to the given value, but will not exceed the
     * entity's maximum health.
     *
     * @param health the new health value as an Integer.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Constructs a tag string representing the entity's name and current health.
     *
     * @return a string in the format "[Name - Health ♥]" where "Name" is the entity's
     *         name and "Health" is the current health of the entity.
     */
    public String getTag() {
        return "[" + this.getName() + " - " + this.getHealth().toString() + " ♥] ";
    }

    /**
     * Prints a message to the console with the entity's tag prepended to it.
     *
     * @param message the message to print to the console.
     */
    public void print(String message) {
        System.out.println(this.getTag() + message);
    }
}
