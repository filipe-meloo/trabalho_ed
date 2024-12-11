package pt.ipp.estg.classes;

import Structures.ArrayList;
import Structures.Graph;

public class Mission {

    private String cod_mission;
    private int version;
    private Target target;
    private Graph<Division> building;
    private ArrayList<Enemy> enemies;
    private ArrayList<Division> exitsEntrys;
    private Player player;

    public Mission() {

    }

    public Mission(String cod_mission, int version, Target target, Graph<Division> building, ArrayList<Enemy> enemies, ArrayList<Division> exitsEntrys) {
        this.cod_mission = cod_mission;
        this.version = version;
        this.target = target;
        this.building = building;
        this.enemies = enemies;
        this.exitsEntrys = exitsEntrys;
    }

    public void printInfo() {
        System.out.println("Mission: " + cod_mission);
        System.out.println("Version: " + version);
        System.out.println("Target: " + target);
        System.out.println("Num. Divisions: " + building.size());
        System.out.println("Num. Enemies: " + enemies.size());
        System.out.println("Num. Exits Entrys: " + exitsEntrys.size());
    }


}
