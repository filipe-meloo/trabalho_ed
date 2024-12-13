/**
 * This class represents a target in the game. A target is a location in the map where the player has to go.
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.classes.mission;

import pt.ipp.estg.classes.entities.Player;
import pt.ipp.estg.enums.TargetType;

public class Target {

    private Division division;
    private TargetType type;
    private boolean reached;

    public Target(Division division, TargetType type) {
        this.division = division;
        this.type = type;
        this.reached = false;
    }

    /**
     * Gets the division where this target is located.
     *
     * @return The division where this target is located.
     */
    public Division getDivision() {
        return division;
    }

    /**
     * Sets the division where this target is located.
     *
     * @param division The new division where this target is located.
     */
    public void setDivision(Division division) {
        this.division = division;
    }

    /**
     * Returns the type of target.
     *
     * @return The type of target as a TargetType enum.
     */
    public TargetType getType() {
        return type;
    }

    /**
     * Sets the type of this target.
     *
     * @param type The new type of target to set, as a TargetType enum.
     */
    public void setType(TargetType type) {
        this.type = type;
    }

    /**
     * Checks if the target has been reached.
     *
     * @return true if the target has been reached, false otherwise.
     */
    public boolean wasFound() {
        return reached;
    }


    /**
     * Marks the target as found and prints messages based on the target type.
     *
     * <p>
     * When the target is reached, the player is notified with a message.
     * The specific message depends on the type of target: LAB, HOSTAGE, or BOSS.
     * The target is then marked as reached.
     * </p>
     *
     * @param player The player who has found the target.
     */
    public void find(Player player) {

        player.print("Reached the target!");
        switch (this.type) {
            case LAB -> player.print("This lab smells like Silco! Let's search for the chem-tech!");
            case HOSTAGE -> player.print("Found a hostage!");
            case BOSS -> player.print("So you're the big guy... Let's see");
        }
        player.print("Moving back");

        this.reached = true;
    }
}
