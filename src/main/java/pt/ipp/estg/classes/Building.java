package pt.ipp.estg.classes;

import Exceptions.EmptyCollectionException;
import Structures.ArrayList;
import Structures.Graph;

public class Building {

    private Graph<Division> building;
    private ArrayList<Division> exitsEntrys; // Lista de divisões de entrada/saída

    //onde cada vértice representa uma divisão do edifício
    public Building(Graph<Division> building) {
        this.building = building;
        this.exitsEntrys = new ArrayList<>();
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
// Método para adicionar conexão entre divisões
    public void addConnection(Division from, Division to) throws EmptyCollectionException {
        if (from == null || to == null) {
            return;
        }

        // Adiciona conexão no grafo
        this.building.addEdge(from, to);

        // Adiciona nomes das divisões conectadas
        from.addConnectedDivision(to.getName());
        to.addConnectedDivision(from.getName());
    }

    // Método para obter nomes das divisões de entrada/saída
    public ArrayList<String> getExitEntryNames() {
        ArrayList<String> exitNames = new ArrayList<>();
        for (Division div : exitsEntrys) {
            exitNames.add(div.getName());
        }
        return exitNames;
    }

    // Método adicional para adicionar divisões de entrada/saída
    public void addExitEntry(Division division) throws EmptyCollectionException {
        if (!exitsEntrys.contains(division)) {
            exitsEntrys.add(division);
        }
    }
}