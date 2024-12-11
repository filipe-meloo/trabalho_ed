package pt.ipp.estg.classes;

import Structures.ArrayList;
import Structures.Graph;

public class Building {

    private Graph<Division> building;
    private ArrayList<Division> exitsEntrys;

    public Building(Graph<Division> building) {
        this.building = building;
    }

    public Graph<Division> getBuilding() {
        return building;
    }

    public void setBuilding(Graph<Division> building) {
        this.building = building;
    }

    public void addDivision(Division division) {
        this.building.addVertex(division);
    }

    public void removeDivision(Division division) {
        this.building.removeVertex(division);
    }

    public void addConnection(Division from, Division to) {

        if (from == null || to == null) {
            return;
        }



        this.building.addEdge(from, to);
    }



}
