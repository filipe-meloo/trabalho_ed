package pt.ipp.estg.gui;

import java.util.Scanner;

import static pt.ipp.estg.gui.Menus.*;

public class InitialMenu {

    private static final Scanner scan = new Scanner(System.in);

    public static void displayMainMenu() {
        while (true) {
            clearScreen();
            printLogo();
            System.out.println("-*- Menu Principal -*-");
            divider();

            System.out.println("1. Carregar Próprio Mapa");
            System.out.println("2. Escolher Mapa Pré-Definido");
            divider();
            System.out.println("0. Sair");

            int option = getValidOption(scan);
            scan.nextLine();

            if (option == 0) break;

            switch (option) {
                case 1 -> {
                    System.out.println("opcao 1");
                }
                case 2 -> {
                    System.out.println("opcao 2");
                }
            }
        }
    }



}
