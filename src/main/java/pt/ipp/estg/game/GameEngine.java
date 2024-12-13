/**
 * This class contains methods for the game logic.
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.game;

import Exceptions.EmptyCollectionException;
import Structures.ArrayList;
import Structures.ArrayUnorderedList;
import pt.ipp.estg.classes.entities.Enemy;
import pt.ipp.estg.classes.entities.Player;
import pt.ipp.estg.classes.items.UsableAbstractItem;
import pt.ipp.estg.classes.mission.Division;
import pt.ipp.estg.classes.mission.Mission;
import pt.ipp.estg.exceptions.PlayerDeathException;
import pt.ipp.estg.gui.Menus;

public class GameEngine extends Menus {

    //to cruz entra na sala e encontra inimigos
    protected static boolean runFirstScenario(Mission mission) {
        Player player = mission.getPlayer();
        if (mission.getEnemies().isEmpty()) {
            return true;
        }

        player.print("Oops, found some badguys!");
        player.print("Let's kill them!");

        while (mission.areEnemiesAliveInDivision(player.getCurrentDivision())) {

            if (player.getHealth() <= 50) {
                player.print("I'm weak! Let's see if I have something in my backpack...");
                if (!player.getInventory().isEmpty()) {
                    player.print("I found something! Let's see what it is...");
                    boolean success = player.useItem();
                    if (!success) {
                        player.print("I don't know what that is!");
                    }
                } else {
                    player.print("I don't have anything in my backpack!");
                }
            } else {
                for (Enemy enemy : mission.getEnemiesInside(player.getCurrentDivision())) {
                    if (enemy.isAlive()) {
                        player.print("Dealt " + player.getPower() + " damage to " + enemy.getName());
                        enemy.takeDamage(player.getPower());
                        if (!enemy.isAlive())
                            player.print("Better be dead scumbag!");
                    }
                }
            }

            for (Enemy enemy : mission.getEnemiesInside(player.getCurrentDivision())) {
                if (enemy.isAlive()) {
                    enemy.print("Dealt " + enemy.getPower() * 0.5 + " damage to " + player.getName());
                    player.takeDamage((int) (enemy.getPower() * 0.5));

                    if (!player.isAlive()) {
                        throw new PlayerDeathException("Player has died");
                    }
                }
            }

            print("Enemies remaining: " + mission.getAliveEnemies().size());
            for (Enemy enemy : mission.getAliveEnemies()) {
                if (enemy.isAlive() && (enemy.getCurrentDivision() != player.getCurrentDivision())) {
                    enemy.moveRandomly();
                }
            }

        }

        return player.isAlive();
    }

    /**
     * Scenario where the player doesn't find any enemies in the current division,
     * so he just continues moving.
     *
     * @param mission the mission that is being played
     */
    protected static void runSecondScenario(Mission mission) {
        mission.getPlayer().print("No one here, let's continue!");

        print("Enemies remaining: " + mission.getAliveEnemies().size());
        ArrayList<Enemy> enemies = mission.getEnemies();
        for (Enemy enemy : enemies) {
            enemy.moveRandomly();
        }
    }

    /**
     * Executes the third scenario where the player engages in combat with enemies in the current division.
     * The player attacks all alive enemies in the current division, dealing damage based on player's power.
     * Enemies also attack the player, dealing damage proportional to their power.
     * If the player dies during this scenario, a PlayerDeathException is thrown.
     * Enemies that remain alive and are not in the player's current division move randomly.
     *
     * @param player the player involved in the scenario
     * @param mission the mission context within which the scenario is executed
     * @throws PlayerDeathException if the player dies during the scenario
     */
    protected static void runThirdScenario(Player player, Mission mission) {

        while (mission.areEnemiesAliveInDivision(player.getCurrentDivision())) {

            for (Enemy enemy : mission.getEnemiesInside(player.getCurrentDivision())) {
                if (enemy.isAlive()) {
                    player.print("Dealt " + player.getPower() + " damage to " + enemy.getName());
                    enemy.takeDamage(player.getPower());
                    if (!enemy.isAlive())
                        player.print("Better be dead scumbag!");
                }
            }

            for (Enemy enemy : mission.getEnemiesInside(player.getCurrentDivision())) {
                if (enemy.isAlive()) {
                    enemy.print("Dealt " + enemy.getPower() * 0.5 + " damage to " + player.getName());
                    player.takeDamage((int) (enemy.getPower() * 0.5));

                    if (!player.isAlive()) {
                        throw new PlayerDeathException("Player has died");
                    }
                }
            }

            print("Enemies remaining: " + mission.getAliveEnemies().size());
            for (Enemy enemy : mission.getAliveEnemies()) {
                if (enemy.isAlive() && (enemy.getCurrentDivision() != player.getCurrentDivision())) {
                    enemy.moveRandomly();
                }
            }

        }

    }

    /**
     * Verifies if the given division is the target division of the given mission and if the target
     * has not been found yet.
     *
     * @param division the division to be verified
     * @param mission the mission context
     * @return true if the division is the target division of the mission and the target has not been found, false otherwise
     */
    public boolean isTargetDivision (Division division, Mission mission){
        return division.getName().equals(mission.getTarget().getDivision().getName()) && !mission.getTarget().wasFound();
    }

}
