package pt.ipp.estg.files;

import Structures.ArrayList;
import Structures.Graph;
import pt.ipp.estg.classes.Division;
import pt.ipp.estg.classes.entities.Enemy;
import pt.ipp.estg.classes.entities.Player;
import pt.ipp.estg.classes.Mission;
import pt.ipp.estg.classes.Target;
import pt.ipp.estg.classes.items.Item;
import pt.ipp.estg.classes.items.MedkitItem;
import pt.ipp.estg.classes.items.UsableAbstractItem;
import pt.ipp.estg.classes.items.VestItem;
import pt.ipp.estg.enums.ItemType;
import pt.ipp.estg.enums.TargetType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Import {

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
            Graph<Division> building = new Graph<>();
            ArrayList<Division> divisionsList = new ArrayList<>();
            ArrayList<Enemy> enemies = new ArrayList<>();
            ArrayList<Division> exitsEntrys = new ArrayList<>();

            // Carregar divisões
            JSONArray edificio = (JSONArray) jsonObject.get("edificio");
            for (Object divisionName : edificio) {
                Division division = new Division((String) divisionName);
                building.addVertex(division);
                divisionsList.add(division);
            }

            // Carregar ligações
            JSONArray ligacoes = (JSONArray) jsonObject.get("ligacoes");
            for (Object ligacaoObj : ligacoes) {
                JSONArray ligacao = (JSONArray) ligacaoObj;
                String fromName = (String) ligacao.get(0);
                String toName = (String) ligacao.get(1);

                Division from = findDivisionByName(divisionsList, fromName);
                Division to = findDivisionByName(divisionsList, toName);
                if (from != null && to != null) {
                    building.addEdge(from, to);
                }
            }

            // Carregar inimigos
            JSONArray inimigos = (JSONArray) jsonObject.get("inimigos");
            for (Object inimigoObj : inimigos) {
                JSONObject inimigo = (JSONObject) inimigoObj;
                String name = (String) inimigo.get("nome");
                long power = (long) inimigo.get("poder");
                String divisionName = (String) inimigo.get("divisao");

                Division division = findDivisionByName(divisionsList, divisionName);
                if (division != null) {
                    Enemy enemy = new Enemy(name, (int) power, 100, division);
                    enemies.add(enemy);
                    division.getEnemies().add(enemy);
                }
            }

            // Carregar itens
            JSONArray itens = (JSONArray) jsonObject.get("itens");
            for (Object itemObj : itens) {
                JSONObject item = (JSONObject) itemObj;
                String divisionName = (String) item.get("divisao");
                String itemType = (String) item.get("tipo");

                Division division = findDivisionByName(divisionsList, divisionName);
                if (division != null) {
                    UsableAbstractItem itemToAdd = null; // Altere para UsableAbstractItem

                    if ("kit de vida".equalsIgnoreCase(itemType)) {
                        itemToAdd = new MedkitItem(division);
                    } else if ("colete".equalsIgnoreCase(itemType)) {
                        itemToAdd = new VestItem(division);
                    }

                    if (itemToAdd != null) {
                        division.getItems().add(itemToAdd); // Adiciona à lista de itens da divisão
                    } else {
                        System.out.println("Tipo de item desconhecido: " + itemType);
                    }
                }
            }


            // Carregar entradas e saídas
            JSONArray entradasSaidas = (JSONArray) jsonObject.get("entradas-saidas");

            for (Object entryExitName : entradasSaidas) {
                Division division = findDivisionByName(divisionsList, (String) entryExitName);
                if (division != null) {
                    division.setExit(true);
                    exitsEntrys.add(division);
                }
            }

            // Carregar alvo
            JSONObject alvo = (JSONObject) jsonObject.get("alvo");
            String targetDivision = (String) alvo.get("divisao");
            String targetType = (String) alvo.get("tipo");
            Target target = new Target(targetDivision, TargetType.valueOf(targetType.toUpperCase()));

            // Criar missão
            return new Mission(codMission, (int) version, target, building, enemies, exitsEntrys);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Erro ao importar a missão!");
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

    private static Division findDivisionByName(ArrayList<Division> divisionsList, String name) {
        for (Division division : divisionsList) {
            if (division.getName().equals(name)) {
                return division;
            }
        }
        return null;
    }
}

