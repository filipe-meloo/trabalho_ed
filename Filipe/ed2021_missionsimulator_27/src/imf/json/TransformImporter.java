/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template fileName, choose Tools | Templates
 * and open the template in the editor.
 */
package imf.json;

import com.google.gson.*;
import java.io.*;

import graph.NetworkMatrix;
import imf.entity.*;
import imf.exception.*;
import listOrderedUnordered.ArrayUnorderedList;

/**
 * Estrutura de Dados - 2020-2021.
 *
 * @author Mariana Ribeiro - 8190573
 * @author André Raro - 8190590
 *
 * Classe TransformImporter extende a classe do Importere que faz a manipulção
 * do Json para as classes mais adqueadas.
 */
public final class TransformImporter extends Importer {

    private String fileName;
    /**
     * Array de diviões que representam o edificio
     */
    private Division[] building;

    /**
     * Array de inimigos
     */
    private Enemy[] enemies;

    /**
     * Array Ordenado que representam as entradas e saidas do edificio
     */
    private ArrayUnorderedList<Division> exitEntry;

    /**
     * Grafo pesado que contem as divisões e as ligaçoes destas
     */
    private NetworkMatrix<Division> buildingNet;

    /**
     * Construtor da Classe TransformImporter. Este construtor ira atribuir as
     * variaveis da classe aos valores lidos na classe {@link imf.entity.Target}
     * e posteriormente ira criar o grafo pesado.
     *
     * @param path, caminho do ficheiro para leitura
     * @throws InvalidFileException caso nao seja possivel ler o ficheiro, ou
     * caso este seja invalido
     */
    public TransformImporter(String path) throws InvalidFileException {
        super(path);
        this.fileName = path;
        this.enemies = readJsonEnemy();
        this.building = readJsonBuilding(this.enemies);
        this.buildingNet = new NetworkMatrix<>();
        createGraph();
        this.exitEntry = new ArrayUnorderedList<>();
        createArrayListExitEntry();
    }

    /**
     * Cria uma missao, validando-a e posteriormente adiciona-lhe a lista de
     * entradas e saidas bem como o grafo pesado.
     *
     * @return a missão com o codigo da missão, a versão, o alvo, o grafo pesado
     * e o array ordenado de entradas e saidas
     * @throws InvalidFileException caso nao consiga ler a missão do ficheiro
     * json, ou caso esta seja invalida apos a sua leitura
     */
    public Mission createMission() throws InvalidFileException {
        Mission missonJson = readJsonMission();

        if (!isTargetDivisionValid(missonJson)) {
            throw new InvalidFileException(InvalidFileException.DIVISION_TARGET);
        } else if (!isExitEntryDivisionValid()) {
            throw new InvalidFileException(InvalidFileException.DIVISION_EXIT_ENTRY);
        } else if (!isDivisionEdgesValid()) {
            throw new InvalidFileException(InvalidFileException.DIVISION_EDGES);
        }
        Mission mission = new Mission(missonJson.getCod(), missonJson.getVersion(), missonJson.getTarget(), exitEntry, buildingNet);

        return readJsonLeaderboard(mission);
    }

    public Mission readJsonLeaderboard(Mission mission) throws InvalidFileException {
        String path = MyFile.getPATH_EXPORT() + this.fileName + MyFile.getEXTENSION();
        File f = new File(path);
        if (f.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
                JsonElement element = JsonParser.parseReader(bufferedReader);
                element = element.getAsJsonArray();

                JsonArray simulationJson = (JsonArray) element;
                SimulationManual[] simulation = new SimulationManual[simulationJson.size()];

                for (int i = 0; i < simulationJson.size(); i++) {
                    simulation[i] = new Gson().fromJson(simulationJson.get(i), SimulationManual.class);
                    mission.addSimulation(simulation[i]);
                }

            } catch (FileNotFoundException | NullException ex) {
                throw new InvalidFileException(ex.getMessage());
            }
        }
        return mission;

    }

    /**
     * Verifica se todas as ligações existem no edificio
     *
     * @return verdadeiro caso todas as diviso~es identificadas nas ligações
     * existam no edificio, falso caso contrario
     * @throws InvalidFileException caso nao consiga ler as ligações do ficheiro
     * json, ou caso estas sejam invalidas apos a sua leitura.
     */
    public boolean isDivisionEdgesValid() throws InvalidFileException {
        String[][] edges = readJsonEdges();
        boolean send = true;
        for (int i = 0; i < edges.length; i++) {
            int find = 0;
            for (int j = 0; j < building.length; j++) {
                if ((building[j].getName().compareTo(edges[i][0].toString()) == 0) || (building[j].getName().compareTo(edges[i][1].toString()) == 0)) {
                    find++;
                }
            }
            if (find != 2) {
                send = false;
                break;
            }
        }
        return send;
    }

    /**
     * Verifica se existe pelo menos uma entrada e saida que efetivamente existe
     * no edificio.
     *
     * @return verdadeiro caso exista pelo menos uma entrada e saida no
     * edificio, falso caso contrario
     */
    public boolean isExitEntryDivisionValid() {
        int divInExitEntry = 0;

        for (int i = 0; i < building.length; i++) {
            if (this.exitEntry.contains(building[i])) {
                divInExitEntry++;
                break;
            }
        }
        return (divInExitEntry != 0);
    }

    /**
     * Verifica se a divisão alvo existe no edificio
     *
     * @param m, missão a verificar
     * @return verdadeiro caso a divisão alvo exista no edificio, falso caso
     * contrario
     */
    public boolean isTargetDivisionValid(Mission m) {
        int findTarget = 0;

        for (int i = 0; i < building.length; i++) {
            if (m.getTarget().getDivision().equals(building[i].getName())) {
                findTarget++;
                break;
            }
        }
        return (findTarget != 0);
    }

    /**
     * Cria o grafo pesado. Adicionando as divisões como vertice ao grafo e
     * posteriormente as suas edges.
     *
     * @throws InvalidFileException caso nao consiga ler as ligações do ficheiro
     * json, ou caso estas sejam invalidas apos a sua leitura
     */
    private void createGraph() throws InvalidFileException {

        for (int i = 0; i < building.length; i++) {
            buildingNet.addVertex(building[i]);

        }
        this.createConnections();
    }

    /**
     * Adiciona as edges ao grafo pesado.
     *
     * @throws InvalidFileException caso nao consiga ler as ligações do ficheiro
     * json, ou caso estas sejam invalidas apos a sua leitura
     */
    private void createConnections() throws InvalidFileException {

        ArrayUnorderedList<Division> connection;
        String[][] edges = readJsonEdges();

        for (int i = 0; i < building.length; i++) {
            for (int j = 0; j < edges.length; j++) {
                addWeight(i, j, building, edges);
            }
        }
    }

    /**
     * Adiciona as edges que possui um peso, calculando esse peso em função do
     * numero de inimigos
     *
     * @param i posição corrente do array de divisões
     * @param k posição corrente da matrix das ligaçoes
     * @param building array de divisões
     * @param edge matrix das ligações
     */
    private void addWeight(int i, int k, Division[] building, String[][] edge) {

        if (building[i].getName().equals(edge[k][0])) {
            for (int j = 0; j < building.length; j++) {
                if (building[j].getName().equals(edge[k][1])) {
                    buildingNet.addEdge(building[i], building[j], building[j].getEnemiesPower());
                    buildingNet.addEdge(building[j], building[i], building[i].getEnemiesPower());
                    this.building[j].getEdges().addToRear(edge[k][0]);
                    this.building[i].getEdges().addToRear(edge[k][1]);
                }
            }
        }
    }

    /**
     * Cria um array ordenado com as entradas e saidas do edificio.
     *
     * @throws InvalidFileException caso nao consiga ler do ficheiro
     */
    private void createArrayListExitEntry() throws InvalidFileException {
        String[] exitEntryArray = this.readJsonExitEntry();
        int tam = exitEntryArray.length;
        int tamAux = 0;

        for (int i = 0; i < this.building.length; i++) {
            for (int j = 0; j < tam; j++) {
                if (exitEntryArray[j].equals(building[i].getName())) {
                    this.exitEntry.addToRear(building[i]);
                    tamAux++;
                }
                if (tamAux == tam) {
                    break;
                }
            }
        }
    }

    /**
     * Retorna o grafo pesado que contem as divisões e as ligações desta
     *
     * @return o grafo pesado que contem as divisões e as ligações desta
     */
    public NetworkMatrix<Division> getBuildingNet() {
        return buildingNet;
    }

}
