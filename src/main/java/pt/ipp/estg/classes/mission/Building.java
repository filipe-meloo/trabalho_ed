/**
 * This class represents a building in the game. A building is map of a mission
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.classes.mission;

import Exceptions.EmptyCollectionException;
import Structures.ArrayList;
import Structures.Graph;
import pt.ipp.estg.classes.entities.Enemy;

import java.util.Iterator;

public class Building {

    private Graph<Division> building;
    private ArrayList<Division> exitsEntrys; // Lista de divisões de entrada/saída

    //onde cada vértice representa uma divisão do edifício
    public Building(Graph<Division> building) {
        this.building = building;
        this.exitsEntrys = new ArrayList<>();
    }

    /**
     * @return the graph representing the building's divisions
     */
    public Graph<Division> getBuilding() {
        return building;
    }

    /**
     * Set the graph representing the building's divisions.
     *
     * @param building the new graph
     */
    public void setBuilding(Graph<Division> building) {
        this.building = building;
    }

    /**
     * Gets the number of divisions in the building.
     *
     * @return the number of divisions as an integer
     */
    public int getNumDivisions() {
        return this.building.size();
    }

    /**
     * Adds a division to the building.
     *
     * @param division the division to add
     */
    public void addDivision(Division division) {
        this.building.addVertex(division);
    }

    /**
     * Finds the nearest exit from a given division in the building.
     *
     * This method will return the nearest division to the given one that is an exit.
     * The nearest division is the one with the shortest path.
     *
     * @param currentDivision the division to find the nearest exit from
     * @param exits the exits in the building
     * @return the nearest exit division
     * @throws EmptyCollectionException if the exits list is empty
     */
    public Division findNearestExit(Division currentDivision, ArrayList<Division> exits) throws EmptyCollectionException {
        Division nearestExit = null;
        int shortestDistance = Integer.MAX_VALUE;

        for (Division exit : exits) {
            Iterator<Division> pathIterator = this.building.shortestPathIteratorBFS(currentDivision, exit);
            int distance = 0;

            while (pathIterator.hasNext()) {
                pathIterator.next();
                distance++;
            }

            if (distance < shortestDistance) {
                shortestDistance = distance;
                nearestExit = exit;
            }
        }

        return nearestExit;
    }


    /**
     * Finds a division in the building by name.
     *
     * This method iterates over all divisions in the building (i.e., all vertices
     * in the graph) and returns the first one with a name that matches the one
     * passed as an argument. The comparison is case-insensitive.
     *
     * If no division with the given name is found, the method returns null.
     *
     * @param name the name of the division to find
     * @return the division with the given name, or null if not found
     */
    public Division getDivision(String name) {
        //percorrer todas as divisões -- vértices do grafo -- com um iterador
        try {
            Iterable<Division> iterator = building.vertices();

            for (Division currentDivision : iterator) {
                if (currentDivision.getName().equalsIgnoreCase(name)) {
                    return currentDivision; //se nome igual return
                }
            }
        } catch (Exception ignored) {
        }

        //caso nenhuma igual
        return null;
    }

    /**
     * Adds a connection between two divisions in the building.
     *
     * If either of the divisions passed as arguments is null, the method does
     * nothing and returns immediately.
     *
     * Otherwise, the method adds an edge between the two divisions in the
     * graph, and adds the names of the connected divisions to each other's
     * lists of neighbors.
     *
     * @param from the first division to connect
     * @param to the second division to connect
     * @throws EmptyCollectionException if either division has no neighbors
     */
    public void addConnection(Division from, Division to) throws EmptyCollectionException {
        if (from == null || to == null) {
            return;
        }

        // Adiciona conexão no grafo
        this.building.addEdge(from, to);

        // Adiciona nomes das divisões conectadas
        from.addNeighbor(to);
        to.addNeighbor(from);
    }

    /**
     * Returns the number of exit-entry divisions in the building.
     *
     * @return the number of exit-entry divisions as an integer
     */
    public int getNumExitsEntrys() {
        return this.exitsEntrys.size();
    }

    /**
     * Returns a list of the names of all the exit-entry divisions in the building.
     *
     * @return a list of strings containing the names of the exit-entry divisions
     */
    public ArrayList<String> getExitEntryNames() {
        ArrayList<String> exitNames = new ArrayList<>();
        for (Division div : exitsEntrys) {
            exitNames.add(div.getName());
        }
        return exitNames;
    }

    /**
     * Adds a division to the list of exit-entry divisions in the building.
     *
     * If the division is already in the list, the method does nothing.
     *
     * @param division the division to add
     * @throws EmptyCollectionException if the list of exit-entry divisions is empty
     */
    public void addExitEntry(Division division) throws EmptyCollectionException {
        if (!exitsEntrys.contains(division)) {
            exitsEntrys.add(division);
        }
    }

    /**
     * Sets the list of exit-entry divisions in the building.
     *
     * If the argument is null, the method does nothing and the list of
     * exit-entry divisions remains unchanged.
     *
     * @param exitsEntrys the new list of exit-entry divisions
     */
    public void setExitsEntrys(ArrayList<Division> exitsEntrys) {
        this.exitsEntrys = exitsEntrys;
    }

    /**
     * Returns the list of exit-entry divisions in the building.
     *
     * This method provides access to the current list of divisions that are
     * designated as exits or entries within the building.
     *
     * @return an ArrayList containing the exit-entry divisions
     */
    public ArrayList<Division> getExitsEntrys() {
        return exitsEntrys;
    }

    /**
     * Prints a visual representation of the building's map.
     *
     * This method prints a text-based representation of the building's map,
     * showing the divisions and connections between them.
     *
     */
    public void printMap() {
        System.out.println("nao implementado");
    }

}