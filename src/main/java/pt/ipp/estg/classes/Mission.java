package pt.ipp.estg.classes;

import Structures.ArrayList;
import Structures.Graph;

public class Mission {

    private String cod_mission;
    private int version;
    private Target target;
    private Building building2;
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

    public String getCod_mission() {
        return cod_mission;
    }

    public void setCod_mission(String cod_mission) {
        this.cod_mission = cod_mission;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Graph<Division> getBuilding() {
        return building;
    }

    public void setBuilding(Graph<Division> building) {
        this.building = building;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Division> getExitsEntrys() {
        return exitsEntrys;
    }

    public void setExitsEntrys(ArrayList<Division> exitsEntrys) {
        this.exitsEntrys = exitsEntrys;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
