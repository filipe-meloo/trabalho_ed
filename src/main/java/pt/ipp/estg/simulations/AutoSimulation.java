/**
 * This class contains methods for the automatic simulation.
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.simulations;

import Exceptions.EmptyCollectionException;
import Structures.ArrayList;
import Structures.ArrayUnorderedList;
import Structures.Graph;
import pt.ipp.estg.classes.entities.Player;
import pt.ipp.estg.classes.mission.Division;
import pt.ipp.estg.classes.mission.Mission;
import pt.ipp.estg.exceptions.PlayerDeathException;
import pt.ipp.estg.files.Import;
import pt.ipp.estg.game.GameEngine;

import java.util.Iterator;
import java.util.Scanner;

public class AutoSimulation extends GameEngine {

    private Mission mission;

    private ArrayUnorderedList<Mission> missionsCompleted;
    private static final AutoSimulation instance = new AutoSimulation();

    private AutoSimulation() {
        this.mission = new Mission();
        this.missionsCompleted = new ArrayUnorderedList<>();
    }

    /**
     * Gets the instance of the AutoSimulation class.
     *
     * @return The instance of the AutoSimulation class.
     */
    public static AutoSimulation getInstance() {
        return instance;
    }

    /**
     * Starts an automatic simulation of a mission.
     *
     * @param filePath The path to the file containing the mission's data.
     * @throws EmptyCollectionException If the mission has no divisions.
     */
    public void startAutoSimulation(String filePath) throws EmptyCollectionException {
        Mission mission = Import.importMission(filePath);

        clearScreen();
        print("Auto Simulation started!");
        divider();

        this.mission = mission;

        runSimulation(this.mission, filePath);

        clearScreen();
        print("Auto Simulation ended!");
        divider();
    }

    /**
     * Runs the automatic simulation for a given mission, iterating through all
     * possible entry points in the mission's building and attempting to complete
     * the mission by finding the target and exiting safely.
     *
     * This method imports a new instance of the mission for each entry point,
     * initializes a player, and calculates the shortest path to the target
     * division. It processes turns while the mission is ongoing, and if the player
     * survives and finds the target, it calculates a path to the nearest exit.
     *
     * @param mission The mission to be simulated.
     * @param filePath The path to the file containing the mission's data.
     * @throws EmptyCollectionException If the mission has no divisions or exits.
     */
    public void runSimulation(Mission mission, String filePath) throws EmptyCollectionException {

        print("Starting the Auto Simulation...");
        ArrayList<Division> entries = mission.getBuilding().getExitsEntrys();

        for (int i = 0; i < entries.size(); i++) {
            mission = Import.importMission(filePath);
            Player player = new Player(100, 30);
            mission.setPlayer(player);
            player = mission.getPlayer();

            divider();
            mission.printInfo();
            divider();

            Graph<Division> buildingGraph = mission.getBuilding().getBuilding();

            print(mission.getCod_mission() + " | Entry: " + entries.get(i).getName());
            player.print("Let's do this!");

            mission.setStartDivision(entries.get(i).getName());

            Iterator<Division> shortestPath = buildingGraph.shortestPathIteratorBFS(mission.getStartDivision(), mission.getTarget().getDivision());
            player.print("Found the shortest path!");

            shortestPath.next();
            int turnCount = 0;
            while (!mission.isGameOver() && !mission.hasFoundTarget() && shortestPath.hasNext()) {
                divider();
                print("Turn: " + ++turnCount);
                mission.nextTurn();
                player.moveTo(shortestPath.next());
                try {
                    processTurn(player, mission);
                } catch (PlayerDeathException e) {
                    break;
                }
            }

            if (!player.isAlive()) {
                print(player.getName() + " died! :(");
                this.missionsCompleted.add(mission);
                continue;
            }

            mission.getTarget().find(player);
            try {
                processTurn(player, mission);
            } catch (PlayerDeathException e) {
                break;
            }


            //TODO IMPLEMENTAR METODO PARA SHORTEST PATH PARA QUALQUER SAIDA
            Division nearestExit = mission.getBuilding().findNearestExit(player.getCurrentDivision(), mission.getBuilding().getExitsEntrys());
            Iterator<Division> shortestPathBack = buildingGraph.shortestPathIteratorBFS(player.getCurrentDivision(), nearestExit);

            shortestPathBack.next();
            while (shortestPathBack.hasNext()) {
                divider();
                print("Turn: " + ++turnCount);
                mission.nextTurn();
                player.moveTo(shortestPathBack.next());

                try {
                    processTurn(player, mission);
                } catch (PlayerDeathException e) {
                    break;
                }
            }

            print(player.getName() + " successfully completed the mission!");
            clearScreen();
            this.missionsCompleted.add(mission);
        }
    }

    /**
     * Processes a turn of the game, given the player and the mission.
     *
     * If the player is in the target division, and there are enemies there, the third scenario is run.
     * If the player is not in the target division, but there are enemies in the current division, the first scenario is run.
     * If the player is not in the target division, and there are no enemies in the current division, the second scenario is run.
     *
     * If the player is in a division that is an exit of the building, and the target has been found, the method returns.
     * @param player The player
     * @param mission The mission
     */
    protected void processTurn(Player player, Mission mission) {
        if (isTargetDivision(player.getCurrentDivision(), mission)) {
            if (mission.areEnemiesAliveInDivision(player.getCurrentDivision())) {
                runThirdScenario(mission.getPlayer(), mission);
            }
        } else if (mission.areEnemiesAliveInDivision(player.getCurrentDivision())) {
            runFirstScenario(mission);
        } else {
            runSecondScenario(mission);
        }

        try {
            if (mission.getBuilding().getExitsEntrys().contains(player.getCurrentDivision()) && mission.getTarget().wasFound()) {
                return;
            }
        } catch (EmptyCollectionException ignored) {
        }
    }

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
     * successful missions, the number of times the player died, the average number
     * of turns per mission, the total number of enemies eliminated, and the
     * percentage of enemies defeated.
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

    /**
     * Generates a report on the best mission, given all the completed missions.
     *
     * The report includes the code of the mission, the initial and final health
     * of the player, the percentage of health left, the number of turns taken,
     * the number of enemies eliminated, and the path taken to complete the
     * mission.
     *
     * If there are no completed missions, the method prints a message and
     * returns.
     *
     * If no mission was successful, the method prints a message and returns.
     *
     * @throws EmptyCollectionException If there are no completed missions.
     */
    public void generateBestMissionReport() throws EmptyCollectionException {
        ArrayList<Mission> missionsCompleted = getCompletedMissions();
        Scanner scan = new Scanner(System.in);

        if (missionsCompleted.isEmpty()) {
            print("No missions completed.");
            pause(scan);
            return;
        }

        Mission bestMission = null;
        int maxHealth = -1;

        for (Mission mission : missionsCompleted) {
            if (mission.isGameOver() && mission.getPlayer().isAlive()) {
                int currentHealth = mission.getPlayer().getHealth();
                if (currentHealth > maxHealth) {
                    maxHealth = currentHealth;
                    bestMission = mission;
                }
            }
        }

        if (bestMission == null) {
            print("No mission was successful.");
            pause(scan);
            return;
        }

        int initialHealth = bestMission.getPlayer().getMaxHealth();
        int finalHealth = bestMission.getPlayer().getHealth();
        int enemiesEliminated = bestMission.getEnemies().size() - bestMission.getAliveEnemies().size();

        divider();
        print("Best Mission Report");
        divider();

        print("Code: " + bestMission.getCod_mission());
        print("Initial Health: " + initialHealth + " ♥");
        print("Final Health: " + finalHealth + " ♥");
        print("Health % Left: " +
                String.format("%.2f", ((double)finalHealth / initialHealth * 100)) + "%");

        print("Turns Number: " + bestMission.getTurnCount());
        print("Killed Enemies: " + enemiesEliminated);

        if (bestMission.getPlayer().getPath() != null && !bestMission.getPlayer().getPath().isEmpty()) {

            StringBuilder path = new StringBuilder();
            for (int i = 0; i < bestMission.getPlayer().getPath().size(); i++) {
                try {
                    Division division = bestMission.getPlayer().getPath().pop();
                    path.append(" → " + division.getName());
                } catch (EmptyCollectionException ignored) {
                    Division division = bestMission.getPlayer().getPath().peek();
                    path.append(" → " + division.getName());
                }
            }

            System.out.println("Path: " + path.toString());
        } else {
            print("Path info unavailable.");
        }

        divider();
        pause(scan);
    }
}
