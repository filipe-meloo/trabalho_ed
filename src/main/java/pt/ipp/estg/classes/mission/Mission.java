package pt.ipp.estg.classes.mission;

import Exceptions.EmptyCollectionException;
import Structures.ArrayList;
import pt.ipp.estg.classes.entities.Enemy;
import pt.ipp.estg.classes.entities.Player;

import java.util.Iterator;

public class Mission {

    private String cod_mission;
    private int version;
    private Target target;
    private Building building;
    private ArrayList<Enemy> enemies;

    private Division startDivision;

    private Player player;

    private int turnCount;

    public Mission() {

    }

    public Mission(String cod_mission, int version, Target target, Building building, ArrayList<Enemy> enemies) {
        this.cod_mission = cod_mission;
        this.version = version;
        this.target = target;
        this.building = building;
        this.enemies = enemies;
        this.turnCount = 0;
    }

    /*
     *  Construtor que cria uma Mission a partir de uma Mission já existente
     */
    public Mission(Mission mission) {
        this.cod_mission = mission.cod_mission;
        this.version = mission.version;
        this.target = mission.target;
        this.building = mission.building;
        this.enemies = mission.enemies;
        this.player = mission.player;
        this.startDivision = mission.startDivision;
        this.turnCount = mission.turnCount;
    }

    /**
     * @return the cod_mission
     */
    public String getCod_mission() {
        return cod_mission;
    }

    /**
     * Returns the version of this mission.
     *
     * @return the version of the mission
     */
    public int getVersion() {
        return version;
    }

    /**
     * Returns the target of the mission.
     *
     * @return the target of the mission
     */
    public Target getTarget() {
        return target;
    }

    /**
     * Sets the target of this mission.
     *
     * @param target the target to set
     */
    public void setTarget(Target target) {
        this.target = target;
    }

    /**
     * Returns the building of the mission.
     *
     * @return the building of the mission
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * Sets the building of this mission.
     *
     * @param building the building to set
     */
    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * Retrieves the list of enemies associated with this mission.
     *
     * @return an ArrayList of Enemy objects representing the enemies present in this mission
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Retrieves the list of enemies associated with a given division in this mission.
     *
     * @param division the division for which to retrieve the enemies
     * @return an ArrayList of Enemy objects representing the enemies present in the given division
     */
    public ArrayList<Enemy> getEnemiesInside(Division division) {
        ArrayList<Enemy> enemiesInside = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (enemy.getCurrentDivision().equals(division)) {
                enemiesInside.add(enemy);
            }
        }
        return enemiesInside;
    }

    /**
     * Returns true if there are any alive enemies in the given division.
     *
     * @param division the division to check
     * @return true if there are alive enemies in the given division, false otherwise
     */
    public boolean areEnemiesAliveInDivision(Division division) {
        for (Enemy enemy : enemies) {
            if (enemy.getCurrentDivision().equals(division) && enemy.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the starting division for this mission.
     *
     * @param startDivision the name of the division to set as the starting division
     * @throws EmptyCollectionException if the building is empty
     */
    public void setStartDivision(String startDivision) throws EmptyCollectionException {
        this.startDivision = building.getDivision(startDivision);
        this.player.moveTo(building.getDivision(startDivision));
    }

    /**
     * Returns the starting division for this mission.
     *
     * @return the starting Division of the mission
     */
    public Division getStartDivision() {
        return startDivision;
    }

    /**
     * Returns the player of the mission.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player of this mission.
     * <p>
     * This method is used to set the player of the mission when the mission is started.
     * <p>
     * @param player the player of the mission
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Checks if the player has found the target of the mission.
     * <p>
     * This method will return true if the player has found the target of the mission, false otherwise.
     * <p>
     * @return true if the player has found the target, false otherwise
     */
    public boolean hasFoundTarget() {
        return target.wasFound();
    }

    /**
     * Prints detailed information about the mission.
     *
     * This includes the mission code, version, target type,
     * number of divisions, number of enemies, and number of
     * exit-entry points in the building.
     */
    public void printInfo() {
        System.out.println("Mission: " + cod_mission);
        System.out.println("Version: " + version);
        System.out.println("Target: " + target.getType().toString());
        System.out.println("Num. Divisions: " + building.getNumDivisions());
        System.out.println("Num. Enemies: " + enemies.size());
        System.out.println("Num. Exits Entrys: " + building.getNumExitsEntrys());
    }

    /**
     * Checks if the game is over.
     * <p>
     * The game is over if the player is dead or if the target has been found and the player is in an exit-entry division.
     * <p>
     * @return true if the game is over, false otherwise
     * @throws EmptyCollectionException if the building is empty
     */
    public boolean isGameOver() throws EmptyCollectionException {
        if (!this.player.isAlive()) {
            return true;
        }

        return this.target.wasFound() && this.building.getExitsEntrys().contains(this.player.getCurrentDivision());
    }

    /**
     * Returns a list of enemies that are currently alive.
     *
     * @return an ArrayList of Enemy objects representing the alive enemies
     */
    public ArrayList<Enemy> getAliveEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (Enemy enemy : this.enemies) {
            if (enemy.isAlive()) {
                enemies.add(enemy);
            }
        }
        return enemies;
    }

    /**
     * Returns the current turn count.
     *
     * @return the current turn count
     */
    public int getTurnCount() {
        return turnCount;
    }

    /**
     * Increments the turn count.
     * <p>
     * This method is called after each turn in the mission.
     */
    public void nextTurn() {
        turnCount++;
    }

    /**
     * Displays the mission map by iterating over the building's divisions.
     *
     * This method constructs a visual representation of the building's divisions
     * and their connections using a breadth-first search (BFS) traversal.
     * It begins by checking if the building graph is empty and then proceeds
     * to build and print the header and rows that represent each division and
     * its connections. Each division is displayed with its name and index,
     * along with indicators of its connections to other divisions.
     *
     * @throws EmptyCollectionException if the building graph is empty
     */
    public void displayMissionMap() throws EmptyCollectionException {
        StringBuilder s = new StringBuilder();

        var buildingGraph = this.getBuilding().getBuilding();
        int size = buildingGraph.size();

        if (buildingGraph.isEmpty()) {
            System.out.println("\nEmpty Map.\n");
            return;
        }

        buildHeader(s, size);

        Iterator<Division> buildingIterator = buildingGraph.iteratorBFS(buildingGraph.getFirstVertex());
        boolean[][] matrix = buildingGraph.getEdge();

        int rowIndex = 0;
        while (buildingIterator.hasNext()) {
            Division currentDivision = buildingIterator.next();
            buildRow(s, currentDivision, rowIndex, matrix[rowIndex], size);
            rowIndex++;
        }

        System.out.println(s);
    }

    /**
     * Builds the header for the mission map display.
     *
     * This method appends a formatted header to the provided StringBuilder,
     * which includes column labels for divisions and their indices. The header
     * is constructed based on the size of the building graph.
     *
     * @param s the StringBuilder to append the header to
     * @param size the number of divisions in the building graph
     */
    private void buildHeader(StringBuilder s, int size) {
        s.append("\nDivision\t\tIndex\t\t");
        for (int i = 0; i < size; i++) {
            s.append(String.format(" %2d", i));
        }
        s.append("\n\n");
    }

    /**
     * Builds a row for the mission map display.
     *
     * This method appends a formatted row to the provided StringBuilder,
     * which includes the name of the division, its index, and the connections
     * to other divisions in the row. The method takes the division to be
     * displayed, its row index in the matrix, the boolean array of connections
     * for the row, and the number of divisions in the building graph.
     *
     * @param s the StringBuilder to append the row to
     * @param division the division to be displayed
     * @param rowIndex the index of the division in the matrix
     * @param rowConnections the boolean array of connections for the row
     * @param size the number of divisions in the building graph
     */
    private void buildRow(StringBuilder s, Division division, int rowIndex, boolean[] rowConnections, int size) {
        s.append(String.format("%-20s     %-4d", division.getName(), rowIndex));

        for (int j = 0; j < size; j++) {
            String connection = rowConnections[j] ? " * " : "   ";
            s.append(String.format("%2s", connection));
        }
        s.append("\n");
    }

}
