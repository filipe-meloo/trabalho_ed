/**
 * This class contains the main menu for the game.
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.gui;

import Exceptions.EmptyCollectionException;
import Structures.ArrayList;
import pt.ipp.estg.classes.mission.Mission;
import pt.ipp.estg.exceptions.ExceptionManager;
import pt.ipp.estg.files.Export;
import pt.ipp.estg.files.Import;
import pt.ipp.estg.simulations.AutoSimulation;
import pt.ipp.estg.simulations.ManualSimulation;

import java.util.Scanner;

import static pt.ipp.estg.gui.Menus.*;

public class InitialMenu {

    private static final Scanner scan = new Scanner(System.in);

    private static final String MISSAO1 = "src/main/resources/mission1.json";
    private static final String MISSAO2 = "src/main/resources/mission.json";
    private static final String MISSAO3 = "src/main/resources/mission3.json";

    /**
     * Displays the main menu and handles user input to navigate to different sections of the program.
     *
     * @throws EmptyCollectionException if the collection of missions is empty
     */
    public static void displayMainMenu() throws EmptyCollectionException {
        while (true) {
            clearScreen();
            printLogo();
            System.out.println("-*- Main Menu -*-");
            divider();

            System.out.println("1. Play the game!");
            System.out.println("2. Automatic Simulation");
            divider();
            System.out.println("3. Exceptions Logs");
            divider();
            System.out.println("0. Exit");

            int option = getValidOption(scan);
            scan.nextLine();

            if (option == 0) break;

            switch (option) {
                case 1 -> {
                    displayManualSimulation();
                }
                case 2 -> {
                    displayAutomaticSimulation();
                }
                case 3 -> {
                    displayExceptionsLogs();
                }
            }
        }
    }

    /**
     * Displays the exceptions logs menu and handles user input to view the logs or return to the main menu.
     *
     * The menu displays the number of logs available and prompts the user to select one of the following options:
     *   1. View All Logs: Displays all the logs in the console and then waits for the user to press Enter to continue.
     *   0. Back: Returns to the main menu.
     */
    public static void displayExceptionsLogs() {
        while (true) {
            clearScreen();
            System.out.println("-*- Exceptions Logs -*-");
            divider();

            System.out.println("1. View All Logs (" + ExceptionManager.getExceptionsSize() + ")");
            divider();
            System.out.println("0. Back");

            int option = getValidOption(scan);
            scan.nextLine();

            if (option == 0) break;

            switch (option) {
                case 1 -> {
                    clearScreen();
                    for (String log : ExceptionManager.getLogsExceptions()) {
                        System.out.println(log);
                    }
                    pause(scan);
                }
            }
        }
    }

    /**
     * Displays the automatic simulation menu and handles user input to run a simulation, view past simulations, view the best mission report, export auto missions, or return to the main menu.
     *
     * The menu displays the following options:
     *   1. Run Automatic Simulation: Runs an automatic simulation and displays the results.
     *   2. View Past Simulations: Displays a list of past simulations and allows the user to select one to view the results.
     *   3. Best Mission Report: Displays the best mission report.
     *   4. Export Auto Missions: Exports the auto missions to a JSON file.
     *   0. Back: Returns to the main menu.
     *
     * @throws EmptyCollectionException If there are no completed missions.
     */
    public static void displayAutomaticSimulation() throws EmptyCollectionException {
        while (true) {
            clearScreen();
            System.out.println("-*- Automatic Simulation -*-");
            divider();

            System.out.println("1. Run Automatic Simulation");
            System.out.println("2. View Past Simulations");
            System.out.println("3. Best Mission Report");
            divider();
            System.out.println("4. Export Auto Missions");
            divider();
            System.out.println("0. Back");

            int option = getValidOption(scan);
            scan.nextLine();

            if (option == 0) break;

            switch (option) {
                case 1 -> {
                    try {
                        displayChooseMapAutoSimulation();
                    } catch (EmptyCollectionException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> {
                    try {
                        displayPastSimulations(true);
                    } catch (EmptyCollectionException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3 -> {
                    try {
                        AutoSimulation.getInstance().generateBestMissionReport();
                    } catch (EmptyCollectionException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 4 -> {
                    Export.exportMissions(true);
                    Export.exportBestMissionReportToJson(true);
                }
            }
        }
    }

    /**
     * Displays the choose map menu for the automatic simulation and handles user input to start a simulation.
     *
     * The menu presents the following map options:
     *   1. Olho de Águia: Starts the simulation with the "Olho de Águia" map.
     *   2. Original: Starts the simulation with the "Original" map.
     *   3. Coração de Ferro: Starts the simulation with the "Coração de Ferro" map.
     *   0. Back: Returns to the previous menu without starting a simulation.
     *
     * @throws EmptyCollectionException If there are issues accessing the maps or simulations.
     */
    private static void displayChooseMapAutoSimulation() throws EmptyCollectionException {
        while (true) {
            clearScreen();
            System.out.println("-*- Choose Map -*-");
            divider();

            System.out.println("1. Olho de Águia");
            System.out.println("2. Original");
            System.out.println("3. Coração de Ferro");

            divider();
            System.out.println("0. Back");

            int option = getValidOption(scan);
            scan.nextLine();

            if (option == 0) break;

            switch (option) {
                case 1 -> {
                    AutoSimulation.getInstance().startAutoSimulation(MISSAO1);
                }
                case 2 -> {
                    AutoSimulation.getInstance().startAutoSimulation(MISSAO2);
                }
                case 3 -> {
                    AutoSimulation.getInstance().startAutoSimulation(MISSAO3);
                }
            }
        }
    }

    /**
     * Displays the past simulations information and handles user input to navigate back.
     *
     * @param isAuto A boolean flag indicating if the report is for automatic simulations or manual simulations.
     *               If true, the automatic simulations report is generated; otherwise, the manual simulations report is generated.
     *
     * @throws EmptyCollectionException If there are no completed missions to generate a report.
     */
    private static void displayPastSimulations(boolean isAuto) throws EmptyCollectionException {
        Scanner scan = new Scanner(System.in);

        while (true) {
            clearScreen();
            System.out.println("-*- Past Simulations Info -*-");
            if (isAuto) {
                AutoSimulation.getInstance().generateGlobalReport();
            } else {
                ManualSimulation.getInstance().generateGlobalReport();
            }
//            System.out.println("1. View Specific Mission");
            divider();
            System.out.println("0. Back");

            int option = getValidOption(scan);
            scan.nextLine();

            if (option == 0) return;

//            switch (option) {
//                case 1 -> {
//                    clearScreen();
////                    for (Mission m : AutoSimulation.getMissionsCompleted()) {
////                        System.out.println(m.getCod_mission());
////                    }
//                }
//            }

            pause(scan);
        }

    }

    /**
     * Displays the manual simulation menu and handles user input to start a new game or view past runs.
     *
     * The menu presents the following options:
     *   1. New Game!: Starts a new manual simulation.
     *   2. View Past Runs: Displays the past manual simulations report.
     *   0. Back: Returns to the previous menu without starting a new simulation.
     *
     * @throws EmptyCollectionException If there are no completed missions to generate a report.
     */
    private static void displayManualSimulation() throws EmptyCollectionException {
        while (true) {
            clearScreen();
            System.out.println("-*- Manual Simulation -*-");
            divider();
            System.out.println("1. New Game!");
            System.out.println("2. View Past Runs");
            System.out.println("3. Export to JSON");
            divider();
            System.out.println("0. Back");

            int option = getValidOption(scan);
            scan.nextLine();

            if (option == 0) break;

            switch (option) {
                case 1 -> {
                    try {
                        displayChooseMapManualSimulation();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> {
                    try {
                        displayPastSimulations(false);
                    } catch (EmptyCollectionException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3 -> {
                    Export.exportMissions(false);
                    Export.exportBestMissionReportToJson(false);
                }
            }
            pause(scan);
        }
    }

    /**
     * Displays the choose map menu for the manual simulation and handles user input to start a simulation, view past simulations, view the best mission report, export auto missions, or return to the main menu.
     *
     * The menu presents the following options:
     *   1. Olho de Águia: Starts the simulation with the "Olho de Águia" map.
     *   2. Original: Starts the simulation with the "Original" map.
     *   3. Coração de Ferro: Starts the simulation with the "Coração de Ferro" map.
     *   0. Back: Returns to the previous menu without starting a simulation.
     *
     * @throws Exception if there are issues accessing the maps or simulations.
     */
    private static void displayChooseMapManualSimulation() throws Exception {
        while (true) {
            clearScreen();
            System.out.println("-*- Choose Map -*-");
            divider();
            System.out.println("1. Olho de Águia");
            System.out.println("2. Original");
            System.out.println("3. Coração de Ferro");

            divider();
            System.out.println("0. Back");

            int option = getValidOption(scan);
            scan.nextLine();

            if (option == 0) return;

            switch (option) {
                case 1 -> {
                    ManualSimulation.getInstance().startSimulation(MISSAO1);
                }
                case 2 -> {
                    ManualSimulation.getInstance().startSimulation(MISSAO2);
                }
                case 3 -> {
                    ManualSimulation.getInstance().startSimulation(MISSAO3);
                }
            }
        }
    }

}
