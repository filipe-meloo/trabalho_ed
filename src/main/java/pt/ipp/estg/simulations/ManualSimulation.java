/**
 * This class contains methods for the manual simulation.
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.simulations;

import Exceptions.EmptyCollectionException;
import Structures.ArrayList;
import Structures.ArrayUnorderedList;
import pt.ipp.estg.classes.entities.Enemy;
import pt.ipp.estg.classes.entities.Player;
import pt.ipp.estg.classes.mission.Division;
import pt.ipp.estg.classes.mission.Mission;
import pt.ipp.estg.exceptions.PlayerDeathException;
import pt.ipp.estg.files.Import;
import pt.ipp.estg.game.GameEngine;

import java.util.Scanner;

public class ManualSimulation extends GameEngine {

    private Mission mission;

    private ArrayUnorderedList<Mission> missionsCompleted;
    private static final ManualSimulation instance = new ManualSimulation();

    private ManualSimulation() {
        this.mission = new Mission();
        this.missionsCompleted = new ArrayUnorderedList<>();
    }

    /**
     * Gets the instance of the ManualSimulation class.
     *
     * @return The instance of the ManualSimulation class.
     */
    public static ManualSimulation getInstance() {
        return instance;
    }

    /**
     * Starts a manual simulation with the given mission file path.
     * 
     * @param filePath The path to the mission file.
     * @throws Exception If the file path is invalid or if the mission file is invalid.
     */
    public void startSimulation(String filePath) throws Exception {
        Mission mission = Import.importMission(filePath);
        this.mission = mission;

        //ask player health
        Scanner scan = new Scanner(System.in);
        int health = 0;
        while (health <= 0) {
            clearScreen();
            print("Player Health");
            health = getValidOption(scan);
            scan.nextLine();
        }

        int power = 0;
        while (power <= 0) {
            clearScreen();
            print("Player Power");
            power = getValidOption(scan);
            scan.nextLine();
        }

        mission.setPlayer(new Player(health, power));
        runGame(this.mission, filePath);
    }

    /**
     * Starts a manual simulation with the given mission file path.
     * 
     * The simulation goes through the following steps:
     * <ol>
     * <li>Asks the player for its health and power.</li>
     * <li>Asks the player to choose a start division.</li>
     * <li>Loops until the player has found the target or the game is over.</li>
     * </ol>
     * 
     * @param mission The mission to be played.
     * @param filePath The path to the mission file.
     * @throws Exception If the file path is invalid or if the mission file is invalid.
     */
    public void runGame(Mission mission, String filePath) throws Exception {
        print("Starting the Manual Simulation...");

        //chose start division
        playerScreen1(mission);
        Scanner scan = new Scanner(System.in);

        while (!mission.isGameOver() && !mission.hasFoundTarget()) {
            displayTurnOptions(mission);
            processTurn(mission);
        }

        clearScreen();
        print("Manual Simulation ended!");
        divider();
    }

    /**
     * Prints the basic information about a mission.
     * 
     * The information printed is the mission code, the player's name, health and power, and the player's current division and turn count.
     * 
     * @param mission The mission to be printed.
     */
    public void printBasicInfo(Mission mission) {
        System.out.println("Mission: " + mission.getCod_mission());
        System.out.println(mission.getPlayer().getName() + " " + mission.getPlayer().getHealth() + " ♥ | " + mission.getPlayer().getPower() + " ★");
        System.out.println("Localização: " + mission.getPlayer().getCurrentDivision().getName() + " | Turn:" + mission.getTurnCount());
    }

    /**
     * Processes the current turn for the given mission.
     * 
     * This method determines the player's actions based on their current location and the state of the mission.
     * It checks if the player is in the target division and whether there are enemies present.
     * Depending on the situation, it initiates different scenarios or finds the target.
     * 
     * @param mission The mission being processed in the current turn.
     * @throws EmptyCollectionException If an attempt is made to access an empty collection.
     */
    public void processTurn(Mission mission) throws EmptyCollectionException {
        if (isTargetDivision(mission.getPlayer().getCurrentDivision(), mission)) {
            if (mission.areEnemiesAliveInDivision(mission.getPlayer().getCurrentDivision())) {
                firstScenario(mission);
            } else {
                mission.getTarget().find(mission.getPlayer());
            }
        } else if (mission.areEnemiesAliveInDivision(mission.getPlayer().getCurrentDivision())) {
            firstScenario(mission);
        } else {
            runSecondScenario(mission);
        }
    }

    /**
     * Handles the first scenario, which is when the player is in a division with enemies.
     * 
     * In this scenario, the player is asked whether they want to attack the enemies or use an item.
     * If the player chooses to attack, the enemies are attacked and the player's health is reduced by half of each enemy's power.
     * If the player chooses to use an item, the item is used and the player's health is increased by the item's power.
     * The scenario ends when there are no more enemies in the division.
     * 
     * @param mission The mission being processed in the current turn.
     * @throws EmptyCollectionException If an attempt is made to access an empty collection.
     */
    public void firstScenario(Mission mission) throws EmptyCollectionException {

        while (mission.areEnemiesAliveInDivision(mission.getPlayer().getCurrentDivision())) {

            if (getDecision(mission)) {
                for (Enemy enemy : mission.getEnemiesInside(mission.getPlayer().getCurrentDivision())) {
                    if (enemy.isAlive()) {
                        mission.getPlayer().print("Dealt " + mission.getPlayer().getPower() + " damage to " + enemy.getName());
                        enemy.takeDamage(mission.getPlayer().getPower());
                        if (!enemy.isAlive())
                            mission.getPlayer().print("Better be dead scumbag!");
                    }
                }
            } else {
                mission.getPlayer().useItem();
            }

            for (Enemy enemy : mission.getAliveEnemies()) {
                if (enemy.isAlive() && (enemy.getCurrentDivision() != mission.getPlayer().getCurrentDivision())) {
                    enemy.moveRandomly();
                }
            }

            for (Enemy enemy : mission.getEnemies()) {
                if (enemy.isAlive() && (enemy.getCurrentDivision() == mission.getPlayer().getCurrentDivision())) {
                    mission.getPlayer().takeDamage((int) (enemy.getPower() * 0.5));
                    if (!mission.getPlayer().isAlive()) {
                        throw new PlayerDeathException("Player has died");
                    }
                }
            }
        }

    }

    /**
     * Prompts the player to make a decision during a mission turn.
     *
     * The player is presented with options based on their current inventory status.
     * If the player has items, they can choose to attack or use an item.
     * Based on the player's choice, a boolean value is returned.
     *
     * @param mission The current mission context.
     * @return true if the player chooses to attack, false if the player chooses to use an item or if no items are available.
     */
    public boolean getDecision(Mission mission) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            clearScreen();
            printBasicInfo(mission);
            divider();
            System.out.println("Options:");
            System.out.println("1. Attack");

            if (mission.getPlayer().getItemCount() > 0) {
                System.out.println("2. Use Item");
            }

            int option = getValidOption(scan);
            scan.nextLine();

            switch (option) {
                case 1 -> {
                    return true;
                }
                case 2 -> {
                    return mission.getPlayer().getItemCount() <= 0;
                }
            }
        }
    }

    /**
     * Displays a menu for the player to choose the start division.
     *
     * @param mission The current mission context.
     * @throws EmptyCollectionException If the mission's building has no exit entries.
     */
    public void playerScreen1(Mission mission) throws EmptyCollectionException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            clearScreen();
            divider();
            System.out.println("Chose Start Division: ");
            divider();
            for (int i = 0; i < mission.getBuilding().getNumExitsEntrys(); i++) {
                String division = mission.getBuilding().getExitEntryNames().get(i);
                System.out.println(i + 1 + ". " + division);
            }
            divider();
            System.out.println("0. Back");

            int option = getValidOption(scan);
            scan.nextLine();

            if (option == 0) return;

            if (option <= mission.getBuilding().getNumExitsEntrys() + 1) {
                mission.setStartDivision(mission.getBuilding().getExitEntryNames().get(option - 1));
                processTurn(mission);
                return;
            }
        }
    }

    /**
     * Displays the turn options for the player in a loop.
     *
     * The options are:
     * <ul>
     * <li>Move to Another Division: the player can choose a new division to move to.</li>
     * <li>Use Item: the player can choose to use an item from their inventory.</li>
     * <li>View Mission Map: the player can view the mission map.</li>
     * <li>Exit Game: the player can choose to exit the game.</li>
     * </ul>
     *
     * The loop continues until the mission is over or the player chooses to exit the game.
     *
     * @param mission The current mission context.
     * @throws EmptyCollectionException If the mission's building has no exit entries.
     */
    public void displayTurnOptions(Mission mission) throws EmptyCollectionException {

        Scanner scan = new Scanner(System.in);

        while (true) {
            if (mission.isGameOver()) {
                return;
            }
            clearScreen();
            printBasicInfo(mission);
            divider();
            System.out.println("Turn Options:");
            System.out.println("1. Move to Another Division");
            System.out.println("2. Use Item (" + mission.getPlayer().getInventory().size() + " items)");
            System.out.println("3. View Mission Map");
            divider();
            System.out.println("0. Exit Game");
            divider();

            int choice = getValidOption(scan);
            scan.nextLine();

            switch (choice) {
                case 1 -> {
                    Division division = chooseDivision(mission);
                    if (division == null) break;
                    mission.getPlayer().moveTo(division);
                    mission.nextTurn();
                    print("Turn: " + mission.getTurnCount());
                    pause(scan);
                }
                case 2 -> {
                    mission.getPlayer().useItem();
                    mission.nextTurn();
                    print("Turn: " + mission.getTurnCount());
                    pause(scan);
                }
                case 3 -> {
                    mission.displayMissionMap();
                    pause(scan);
                }
                case 0 -> {
                      mission.getPlayer().setHealth(0);
                      return;
                }
            }
        }
    }

    /**
     * Displays a menu for the player to choose a division to move to.
     *
     * The menu displays the name of each division that is a neighbour of the player's current division.
     * The player can choose a division by entering the corresponding number.
     * If the player chooses 0, the method returns null.
     *
     * @param mission The current mission context.
     * @return The chosen division, or null if the player chose 0.
     * @throws EmptyCollectionException If the mission's building has no exit entries.
     */
    public Division chooseDivision(Mission mission) throws EmptyCollectionException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            clearScreen();
            printBasicInfo(mission);
            divider();
            System.out.println("Chose Division: ");
            for (int i = 0; i < mission.getPlayer().getCurrentDivision().getNeighbours().size(); i++) {
                String division = mission.getPlayer().getCurrentDivision().getNeighbours().get(i).getName();
                System.out.println(i + 1 + ". " + division);
            }

            divider();
            System.out.println("0. Back");

            int option = getValidOption(scan);
            scan.nextLine();

            if (option == 0)
                return null;

            if (option <= mission.getPlayer().getCurrentDivision().getNeighbours().size() + 1) {
                return (mission.getPlayer().getCurrentDivision().getNeighbours().get(option - 1));
            }
        }
    }

    /*
    System.out.println("Health: " + mission.getPlayer().getHealth() + " ♥");
            System.out.println("Power: " + mission.getPlayer().getPower() + " ★");
     */

    /**
     * Retrieves a list of completed missions.
     *
     * This method creates a new ArrayList containing all missions that have been
     * marked as completed and stored in the `missionsCompleted` collection.
     *
     * @return An ArrayList of completed Mission objects.
     */
    public ArrayList<Mission> getCompletedMissions() {
        ArrayList<Mission> completedMissions = new ArrayList<>();
        for (Mission m : missionsCompleted) {
            completedMissions.add(m);
        }
        return completedMissions;
    }

    /**
     * Generates a global report on the missions that have been completed.
     *
     * The report includes the total number of missions simulated, the number of
     * successful missions, the success rate, the number of player deaths, the
     * average number of turns per mission, the total number of enemies eliminated
     * and the enemy defeat rate.
     *
     * @throws EmptyCollectionException If there are no completed missions.
     */
    public void generateGlobalReport() throws EmptyCollectionException {
        ArrayList<Mission> missionsCompleted = getCompletedMissions();
        int totalMissions = missionsCompleted.size();
        int successfulMissions = 0;
        int playerDeaths = 0;
        int totalTurns = 0;
        int totalEnemiesEliminated = 0;
        int totalEnemies = 0;

        for (Mission mission : missionsCompleted) {
            if (mission.isGameOver() && mission.getPlayer().isAlive()) {
                successfulMissions++;
            }

            if (!mission.getPlayer().isAlive()) {
                playerDeaths++;
            }

            totalTurns += mission.getTurnCount();

            totalEnemiesEliminated += mission.getEnemies().size() - mission.getAliveEnemies().size();

            totalEnemies += mission.getEnemies().size();
        }

        double successRate = (double) successfulMissions / totalMissions * 100;
        double avgTurnsPerMission = totalMissions > 0 ? (double) totalTurns / totalMissions : 0;
        double enemyDefeatRate = totalEnemies > 0 ? (double) totalEnemiesEliminated / totalEnemies * 100 : 0;

        divider();
        print("Missões Total Simuladas: " + totalMissions);
        print("Missões Completadas com Sucesso: " + successfulMissions + " (" + String.format("%.2f", successRate) + "%)");
        print("Jogador Morto: " + playerDeaths + " vezes");
        print("Número Médio de Turnos por Missão: " + String.format("%.2f", avgTurnsPerMission));
        print("Inimigos Eliminados: " + totalEnemiesEliminated);
        print("Percentagem de Inimigos Derrotados: " + String.format("%.2f", enemyDefeatRate) + "%");
        divider();

    }

}
