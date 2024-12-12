package pt.ipp.estg.classes;

import Structures.ArrayList;
import Structures.Graph;
import Structures.LinkedQueue;
import pt.ipp.estg.classes.entities.Enemy;
import pt.ipp.estg.classes.entities.Player;
import pt.ipp.estg.classes.mission.ManualSimulation;

public class Mission {

    private String cod_mission;
    private int version;
    private Target target;
    private Building building2;
    private Graph<Division> building;
    private ArrayList<Enemy> enemies;
    private ArrayList<Division> exitsEntrys;

    private Player player;

    // private LinkedQueue<ManualSimulation> simulations;

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

    public int getVersion() {
        return version;
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

    public ArrayList<Division> getExitsEntrys() {
        return exitsEntrys;
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

    //start game
    public void start(Player player, Division startDivision) {
        this.player = player;




    }


    //TODO FUNCAO PARA REPORTAR O FIM DO JOGO
    public void report() {

    }
}
