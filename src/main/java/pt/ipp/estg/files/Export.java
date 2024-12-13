/**
 * This class exports a JSON file with a report of the mission.
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.files;

import Exceptions.EmptyCollectionException;
import Structures.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pt.ipp.estg.classes.mission.Division;
import pt.ipp.estg.classes.mission.Mission;
import pt.ipp.estg.simulations.AutoSimulation;
import pt.ipp.estg.simulations.ManualSimulation;

import java.io.FileWriter;
import java.io.IOException;

public class Export {

    /**
     * Exports a JSON file with a global report of the simulations.
     * The report contains the total number of missions, the number of successful missions,
     * the success rate, the number of player deaths, the average number of turns per mission,
     * the total number of enemies eliminated and the enemy defeat rate.
     */
    public static void exportMissions(boolean auto) throws EmptyCollectionException {
        ArrayList<Mission> missionsCompleted = auto ? AutoSimulation.getInstance().getCompletedMissions() : ManualSimulation.getInstance().getCompletedMissions();
        String fileName = "export" + System.currentTimeMillis() + "-auto.json";

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

        double successRate = totalMissions > 0 ? (double) successfulMissions / totalMissions * 100 : 0;
        double avgTurnsPerMission = totalMissions > 0 ? (double) totalTurns / totalMissions : 0;
        double enemyDefeatRate = totalEnemies > 0 ? (double) totalEnemiesEliminated / totalEnemies * 100 : 0;

        // Cria o objeto JSON para o relatório global
        JSONObject globalReport = new JSONObject();
        globalReport.put("totalMissions", totalMissions);
        globalReport.put("successfulMissions", successfulMissions);
        globalReport.put("successRate", String.format("%.2f", successRate) + "%");
        globalReport.put("playerDeaths", playerDeaths);
        globalReport.put("avgTurnsPerMission", String.format("%.2f", avgTurnsPerMission));
        globalReport.put("totalEnemiesEliminated", totalEnemiesEliminated);
        globalReport.put("enemyDefeatRate", String.format("%.2f", enemyDefeatRate) + "%");

        // Escreve no ficheiro JSON
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(globalReport.toJSONString());
            file.flush();
            System.out.println("Global report exported successfully to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exports a JSON file with a report of the best mission.
     * The report contains the code of the mission, the initial and final health of the player,
     * the percentage of health left, the number of turns taken and the number of enemies eliminated.
     * If the player has a path, the path is also exported as an array of strings.
     */
    public static void exportBestMissionReportToJson(boolean auto) throws EmptyCollectionException {
        ArrayList<Mission> missionsCompleted = auto ? AutoSimulation.getInstance().getCompletedMissions() : ManualSimulation.getInstance().getCompletedMissions();
        String fileName = "bestMission-export" + System.currentTimeMillis() + "-auto.json";

        if (missionsCompleted.isEmpty()) {
            System.out.println("No missions completed.");
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
            System.out.println("No mission was successful.");
            return;
        }

        int initialHealth = bestMission.getPlayer().getMaxHealth();
        int finalHealth = bestMission.getPlayer().getHealth();
        int enemiesEliminated = bestMission.getEnemies().size() - bestMission.getAliveEnemies().size();

        JSONObject bestMissionReport = new JSONObject();
        bestMissionReport.put("missionCode", bestMission.getCod_mission());
        bestMissionReport.put("initialHealth", initialHealth);
        bestMissionReport.put("finalHealth", finalHealth);
        bestMissionReport.put("healthPercentageLeft", String.format("%.2f", ((double) finalHealth / initialHealth * 100)) + "%");
        bestMissionReport.put("turnCount", bestMission.getTurnCount());
        bestMissionReport.put("enemiesEliminated", enemiesEliminated);

        JSONArray pathArray = new JSONArray();
        if (bestMission.getPlayer().getPath() != null && !bestMission.getPlayer().getPath().isEmpty()) {
            try {
                while (!bestMission.getPlayer().getPath().isEmpty()) {
                    Division division = bestMission.getPlayer().getPath().pop();
                    pathArray.add(division.getName());
                }
            } catch (EmptyCollectionException ignored) {
            }
        }

        bestMissionReport.put("path", pathArray);

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(bestMissionReport.toJSONString());
            file.flush();
            System.out.println("Best mission report exported successfully to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
