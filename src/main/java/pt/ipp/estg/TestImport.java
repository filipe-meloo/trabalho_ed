package pt.ipp.estg;

import Structures.ArrayList;
import Structures.Graph;
import pt.ipp.estg.classes.Division;
import pt.ipp.estg.classes.entities.Enemy;
import pt.ipp.estg.classes.Mission;
import pt.ipp.estg.classes.Target;
import pt.ipp.estg.files.Import;
import pt.ipp.estg.classes.items.Item;

public class TestImport {

    public static void main(String[] args) {
        try {
            // Caminho do ficheiro JSON
            String filePath = "src/main/resources/mission.json";

            // Importar a missão
            Mission mission = Import.importMission(filePath);

            // Verificar e imprimir informações detalhadas
            if (mission != null) {
                System.out.println("========== INFORMAÇÕES DA MISSÃO ==========");
                System.out.println("Missão: " + mission.getCod_mission());
                System.out.println("Versão: " + mission.getVersion());
                Target target = mission.getTarget();
                System.out.println("Target:");
                System.out.println("  - Divisão: " + target.getDivision());
                System.out.println("  - Tipo: " + target.getType());

                System.out.println("\n========== DIVISÕES ==========");
                Graph<Division> building = mission.getBuilding();
                for (int i = 0; i < building.size(); i++) {

                    String name = building.getVertices()[i].getName();
                    ArrayList<Item> items = building.getVertices()[i].getItems();
                    ArrayList<Enemy> enemies = building.getVertices()[i].getEnemies();
                    boolean isExit = building.getVertices()[i].isExit();

                    Division division = new Division(
                            name, items, enemies, isExit
                    );

                    if (division != null) {
                        System.out.println("Divisão: " + division.getName());
                        System.out.println("  - É saída: " + (division.isExit() ? "Sim" : "Não"));

                        // Itens
                        if (division.getItems().isEmpty()) {
                            System.out.println("  - Itens: Nenhum");
                        } else {
                            System.out.println("  - Itens:");
                            for (Item item : division.getItems()) {
                                System.out.println("    * Item: " + item); // Usa o método `toString` de Item
                            }
                        }

                        // Inimigos
                        if (division.getEnemies().isEmpty()) {
                            System.out.println("  - Inimigos: Nenhum");
                        } else {
                            System.out.println("  - Inimigos:");
                            for (Enemy enemy : division.getEnemies()) {
                                System.out.println("    * Nome: " + enemy.getName());
                                System.out.println("      Poder: " + enemy.getPower());
                                System.out.println("      Vida: " + enemy.getHealth());
                                System.out.println("      Divisão inicial: " + enemy.getCurrentDivision());
                            }
                        }
                    }
                }

                System.out.println("\n========== ENTRADAS E SAÍDAS ==========");
                if (mission.getExitsEntrys().isEmpty()) {
                    System.out.println("Nenhuma entrada/saída encontrada.");
                } else {
                    for (Division entryExit : mission.getExitsEntrys()) {
                        System.out.println("  - " + entryExit.getName());
                    }
                }

                System.out.println("\n========== MISSÃO IMPORTADA COM SUCESSO ==========");
            } else {
                System.out.println("Erro ao importar a missão!");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para identificar possíveis erros
        }
    }

}
