/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imf.simulation;

import imf.entity.Mission;
import imf.entity.MyFile;
import imf.entity.SimulationManual;
import imf.exception.InvalidFileException;
import imf.json.Exporter;
import imf.json.TransformImporter;
import java.io.File;
import java.util.Iterator;
import java.util.Scanner;
import listOrderedUnordered.ArrayOrderedList;

/**
 * Estrutura de Dados - 2020-2021.
 *
 * @author Mariana Ribeiro - 8190573
 * @author André Raro - 8190590
 *
 * Classe que apresenta um menu onde o To Cruz pode escolher o tipo de simulação
 * que deseja testar a sua missão
 */
public class Menu extends MyFile {

    /**
     * String que representa o caminho do ficheiro
     */
    public String file;
    /**
     * String utilizada para tornar o output mais perceptível
     */
    private static String S_AUX = "\n*-*-*-*-*-*-*--*-*-*-*--*-*-*-*--*-*-*--*-*-*--*-*-*-*--*-*-*-*-*-*-*\n";
    /**
     * Variável do tipo mission utilizada para guardar informações básicas da
     * missão
     */
    private Mission mission;

    /**
     * Utilizado para escolher o mapa e apresentar o resto do menu
     */
    public Menu() {
        this.mission = new Mission();
        chooseFile();
        menu1();
    }

    /**
     * Primeiro comando que aparece no output e pede ao utilizador para escolher
     * um mapa
     */
    public void chooseFile() {

        Scanner scan = new Scanner(System.in, "ISO-8859-1");
        String option = "-1";
        boolean valid = false;
        boolean validInput = false;
        do {
            System.out.println("\t\t IMF SIMULATOR");
            System.out.print(S_AUX);
            File folder = new File("maps");
            File[] listOfFiles = folder.listFiles();

            System.out.println("Missões Disponiveis: ");
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    boolean isFound = listOfFiles[i].getName().contains("simulation");
                    if (!isFound) {
                        System.out.println(" - " + listOfFiles[i].getName().replace(".json", ""));
                    }
                }
            }
            String opt;
            do {
                System.out.println("\nEscreva o nome do missão que deseja usar para as simulações: ");
                opt = scan.nextLine();
                int pos = -1;
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (opt.equalsIgnoreCase(listOfFiles[i].getName().replace(".json", ""))) {
                        pos = i;
                        validInput = true;
                    }
                }
            } while (!validInput);

            this.file = opt;

            try {
                TransformImporter t = new TransformImporter(file);
                this.mission = t.createMission();
                valid = true;
            } catch (InvalidFileException ex) {
                System.out.println(ex.getMessage());
            }

        } while (!valid);
        System.out.print("O ficheiro foi reconhecido e validado com sucesso.\n" + S_AUX);

    }

    /**
     * Utilizado para fazer um print do menu para o utilizador perceber as
     * escolhas disponíveis
     */
    public void menu1() {

        Scanner scan = new Scanner(System.in, "ISO-8859-1");
        String opt = "0";
        do {

            System.out.print(S_AUX);
            System.out.println("\t1 -> Visualização do mapa");
            System.out.println("\t2 -> Simulação Automática");
            System.out.println("\t3 -> Simulação Manual");
            System.out.println("\t4 -> Resultados da Simulação Manual");
            System.out.println("\t5 -> Mudar de Mapa");
            System.out.println("\t6 -> Sair");
            System.out.print(S_AUX);
            System.out.print("\n Opção: ");
            opt = scan.nextLine();

            switch (opt) {
                case "1":
                    System.out.println(mission.toString());
                    outOption();
                    break;
                case "2":
                    Automatic auto = new Automatic(this.mission);
                    auto.automatic();
                    outOption();
                    break;
                case "3":
                    option3();
                    outOption();
                    break;
                case "4":
                    option4();
                    outOption();
                    break;
                case "5":
                    Exporter.exportToJson(mission.getSimulation(), this.file);
                    chooseFile();
                    break;
                case "6":
                    Exporter.exportToJson(mission.getSimulation(), this.file);
                    System.out.println("\n\n\n\n\n\t\tIMF SIMULATOR" + S_AUX);
                    System.out.println("Muito Obrigado Por Utilizar O Nosso Simulador!\nTrabalho Realizado Por:\n\t\tMariana Ribeiro 8190573\n\t\tAndré Raro      8190590\n" + S_AUX);
                    break;
                default:
                    System.out.println("\n Escolha uma opção válida");
                    break;
            }
        } while (!opt.equals("6"));
    }
/**
 * Função criada para o utilizador avançar em cada opção do menu
 */
    private void outOption() {
        System.out.print("Pressione qualquer tecla para voltar ao menu anterior:    ");
        Scanner scan = new Scanner(System.in, "ISO-8859-1");
        String optOut = scan.nextLine();

    }
/**
 * Simulação Manual
 */
    private void option3() {
        Manual m = new Manual(this.mission);
        try {
            m.start();
        } catch (InvalidFileException ex) {
            System.out.println(ex.getMessage());
        }
    }
/**
 * Resultados da Simulação Manual
 */
    private void option4() {
        ArrayOrderedList<SimulationManual> simulation = mission.getSimulation();
        Iterator<SimulationManual> it = simulation.iterator();
        System.out.println(S_AUX + "Simulações: ");
        if (simulation.isEmpty()) {
            System.out.println("\tAinda Não Existem Simulações\n\tFaça A Sua Primeira Simulação!");
        } else {
            while (it.hasNext()) {
                System.out.println(it.next());

            }
        }
        System.out.println(S_AUX);

    }

}
