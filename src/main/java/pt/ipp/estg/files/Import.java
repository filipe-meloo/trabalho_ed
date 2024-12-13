/**
 * This class imports a mission from a JSON file, correcting any format issues and parsing its contents.
 *
 * @author Filipe Melo - 8210187
 * @author Ruben Santos - 8200492
 */
package pt.ipp.estg.files;

import Structures.ArrayList;
import Structures.Graph;
import pt.ipp.estg.classes.mission.Building;
import pt.ipp.estg.classes.mission.Division;
import pt.ipp.estg.classes.entities.Enemy;
import pt.ipp.estg.classes.mission.Mission;
import pt.ipp.estg.classes.mission.Target;
import pt.ipp.estg.classes.items.MedkitItem;
import pt.ipp.estg.classes.items.UsableAbstractItem;
import pt.ipp.estg.classes.items.VestItem;
import pt.ipp.estg.enums.TargetType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import pt.ipp.estg.exceptions.IONotRecognizedException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Import {

    /**
     * Imports a mission from a JSON file, correcting any format issues and parsing its contents.
     *
     * The function first fixes the JSON file to ensure it is correctly formatted, then reads
     * and parses the corrected JSON to extract mission details. It loads divisions, connections,
     * enemies, items, entry/exit points, and the target for the mission. If any required data
     * is missing or unrecognized, an appropriate exception is thrown.
     *
     * @param filePath the path to the JSON file containing mission data
     * @return a Mission object constructed from the parsed data in the JSON file
     * @throws IONotRecognizedException if any division, item, or target type is not recognized
     * @throws IllegalArgumentException if there is an error while loading the mission JSON file
     */
    public static Mission importMission(String filePath) {
        try {
            // Corrigir o JSON antes do parsing
            String correctedFilePath = fixJsonFile(filePath);

            // Ler e parsear o JSON corrigido
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(correctedFilePath));

            // Obter informações gerais
            String codMission = (String) jsonObject.get("cod-missao");
            long version = (long) jsonObject.get("versao");

            // Inicializar estruturas
            Building building = new Building(new Graph<>());
            ArrayList<Division> divisionsList = new ArrayList<>();
            ArrayList<Enemy> enemies = new ArrayList<>();
            ArrayList<Division> exitsEntrys = new ArrayList<>();

            // Carregar divisões
            JSONArray edificio = (JSONArray) jsonObject.get("edificio");
            for (Object divisionName : edificio) {
                Division division = new Division((String) divisionName);
                building.addDivision(division);
                divisionsList.add(division);
            }

            // Carregar ligações
            JSONArray ligacoes = (JSONArray) jsonObject.get("ligacoes");
            for (Object ligacaoObj : ligacoes) {
                JSONArray ligacao = (JSONArray) ligacaoObj;
                String fromName = (String) ligacao.get(0);
                String toName = (String) ligacao.get(1);

                Division from = building.getDivision(fromName);
                Division to = building.getDivision(toName);

                if (from != null && to != null) {
                } else if (from == null ) {
                    throw new IONotRecognizedException("This 'from' division is not present on the provided JSON File: " + fromName);
                } else if (to == null ) {
                    throw new IONotRecognizedException("The 'to' division is not present on the provided JSON File: " + toName);
                }

                building.addConnection(from, to);
            }

            // Carregar inimigos
            JSONArray inimigos = (JSONArray) jsonObject.get("inimigos");
            for (Object inimigoObj : inimigos) {
                JSONObject inimigo = (JSONObject) inimigoObj;
                String name = (String) inimigo.get("nome");
                int power = (int) (long)inimigo.get("poder");
                String divisionName = (String) inimigo.get("divisao");

                Division division = building.getDivision(divisionName);
                if (division != null) {
                    Enemy enemy = new Enemy(name, power, 100, division);
                    enemies.add(enemy);
                }
            }

            // Carregar itens
            JSONArray itens = (JSONArray) jsonObject.get("itens");
            if (itens != null) {
                for (Object itemObj : itens) {
                    JSONObject item = (JSONObject) itemObj;
                    String divisionName = (String) item.get("divisao");
                    String itemType = (String) item.get("tipo");

                    Division division = building.getDivision(divisionName);
                    if (division != null) {
                        UsableAbstractItem itemToAdd = null; // Altere para UsableAbstractItem

                        if ("kit de vida".equalsIgnoreCase(itemType)) {
                            itemToAdd = new MedkitItem(division);
                        } else if ("colete".equalsIgnoreCase(itemType)) {
                            itemToAdd = new VestItem(division);
                        } else {
                            throw new IONotRecognizedException("Item type not recognized: " + itemType);
                        }

                        if (itemToAdd != null) {
                            division.getItems().add(itemToAdd); // Adiciona à lista de itens da divisão
                        } else {
                            System.out.println("Tipo de item desconhecido: " + itemType);
                        }
                    }
                }
            }

            // Carregar entradas e saídas
            JSONArray entradasSaidas = (JSONArray) jsonObject.get("entradas-saidas");

            for (Object entryExitName : entradasSaidas) {
                Division division = building.getDivision((String) entryExitName);
                if (division != null) {
                    division.setExit(true);
                    exitsEntrys.add(division);
                }
            }
            building.setExitsEntrys(exitsEntrys);

            // Carregar alvo
            JSONObject alvo = (JSONObject) jsonObject.get("alvo");
            String targetDivision = (String) alvo.get("divisao");
            String targetType = (String) alvo.get("tipo");

            Division alvoDivision = null;

            if (building.getDivision(targetDivision) == null) {
                throw new IONotRecognizedException("The target division is not present on the provided JSON File: " + targetDivision);
            } else {
                alvoDivision = building.getDivision(targetDivision);
            }

            TargetType targetTypeEnum;
            switch (targetType) {
                case "quimico" -> targetTypeEnum = TargetType.LAB;
                case "refem" -> targetTypeEnum = TargetType.HOSTAGE;
                case "boss" -> targetTypeEnum = TargetType.BOSS;
                default -> throw new IONotRecognizedException("Target type not recognized: " + targetType);
            }

            Target target = new Target(alvoDivision, targetTypeEnum);

            //TODO VERIFICAR SE EXISTE LIGACAO PARA O ALVO

            // Criar missão
            return new Mission(codMission, (int) version, target, building, enemies);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error while loading the mission JSON file! Try again :(");
        }
    }

    private static String fixJsonFile(String filePath) throws IOException {
        String correctedFilePath = filePath.replace(".json", "_corrected.json");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             FileWriter writer = new FileWriter(correctedFilePath)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("tipo:") && !line.contains("\"tipo\"")) {
                    line = line.replace("tipo:", "\"tipo\":");
                }
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        }
        return correctedFilePath;
    }

}

