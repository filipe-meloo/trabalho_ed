/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imf.json;

import com.google.gson.*;
import java.io.*;
import imf.entity.Division;
import imf.entity.Enemy;
import imf.entity.Mission;
import imf.entity.MyFile;

import imf.exception.InvalidFileException;
import imf.exception.InvalidTypeException;
import imf.exception.NullException;

/**
 * Estrutura de Dados - 2020-2021.
 *
 * @author Mariana Ribeiro - 8190573
 * @author André Raro - 8190590
 *
 * Classe que faz a importação do Json para serem posteriormente manipuladas.
 *
 */
class Importer extends MyFile {

    /**
     * Caminho do ficheiro que se deseja ler
     */
    private final String fileName;

    /**
     * Objeto json que conteudo o conteudo do ficheiro
     */
    private JsonObject json;

    /**
     * Construtor que guarda o nome do ficheiro numa variavel de classe e
     * posteriormente le o ficheiro guardado o seu conteudo tambem numa varivel
     * de classe.
     *
     * @param path caminho do ficheiro
     * @throws InvalidFileException caso o ficheiro seja invalido, ou seja
     * possua algum erro de sintaxe ou do objetivo do programa
     */
    public Importer(String fileName) throws InvalidFileException {
        this.fileName = MyFile.getPATH_IMPORT() + fileName + MyFile.getEXTENSION();
        this.json = readJson();
    }

    /**
     * Lê o ficheiro Json e transforma-o num objeto Json para o conteudo do
     * ficheiro ser facilmente manipulado.
     *
     * @return um objeto Json com o conteudo do ficheiro
     * @throws InvalidFileException caso o ficheiro seja null, nao seja
     * encontrado ou esteja vazio...
     */
    private JsonObject readJson() throws InvalidFileException {
        if (this.fileName == null) {
            throw new InvalidFileException(InvalidFileException.FILE_NULL);
        }
        try {
            //lé o ficheiro
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            //Transforma o ficheiro num elemento atraves do Parser
            JsonElement element = JsonParser.parseReader(bufferedReader);

            //Retorna um objeto do tipo Json que possui o nosso ficheiro
            return element.getAsJsonObject();

        } catch (FileNotFoundException ex) {
            throw new InvalidFileException(ex.getMessage());
        }
    }

    /**
     *
     * Lê as divisões que representa o edificio, para um array de divisões onde
     * cada posição representa uma instancia de {@link imf.entity.Division}.
     * Posteriormente ira usar o metodo que lê o array das entradas e saidas na
     * para modificar a varivavel de classe booleana(relativamente a divisao ser
     * de saida ou entrada) verdadeira ou falsa conforme o caso. Por ultimo
     * adiciona os inimigos as divisões aonde estes pertencem.
     *
     * @param enemies, array de inimigos para verificar e adicionar as divisões
     * @return array de divisões onde cada posição é uma instancia de
     * {@link imf.entity.Division}
     * @throws InvalidFileException InvalidFileException caso não tenha sido
     * possivel ler os inimigos para posteriormente adiciona-los, ou estes sejam
     * invalidos, ou caso o array de divisões seja invalido
     */
    public Division[] readJsonBuilding(Enemy[] enemies) throws InvalidFileException {

        JsonArray stringJson = (JsonArray) json.get("edificio");
        String[] division = new String[stringJson.size()];
        Division[] edificio = new Division[stringJson.size()];
        for (int i = 0; i < stringJson.size(); i++) {
            division[i] = new Gson().toJson(stringJson.get(i));
            String result = division[i].replaceAll("[+.^:,\"]", "");
            edificio[i] = new Division(result);
        }

        manipulateExitEntry(edificio, readJsonExitEntry());
        try {
            readJsonEnemyDivision(enemies, edificio);
        } catch (NullException | InvalidTypeException ex) {
            throw new InvalidFileException(InvalidFileException.ENEMY_NOT_READ + "\n -" + ex.getMessage());
        }

        if (!Division.isValid(edificio)) {
            throw new InvalidFileException(InvalidFileException.DIVISION);
        }

        return edificio;
    }

    /**
     * Lê o array que contem o nome das divisões que representam uma entrada ou
     * saida do edificio armazenando estas num array de String's.
     *
     * @return um array de String que representa o array de entradas e saidas do
     * edificio
     * @throws InvalidFileException caso alguma instancia seja invalida a
     * leitura do ficheiro torna-se simultaneamente invalida.
     */
    protected String[] readJsonExitEntry() throws InvalidFileException {
        JsonArray stringJson = (JsonArray) json.get("entradas-saidas");
        int tam = stringJson.size();

        if (tam <= 0) {
            throw new InvalidFileException(InvalidFileException.EXIT_ENTRY);
        }

        String[] exitEntry = new String[tam];
        for (int i = 0; i < tam; i++) {
            exitEntry[i] = new Gson().toJson(stringJson.get(i)).replaceAll("[+.^:,\"]", "");
            if (exitEntry[i] == null || exitEntry[i].isBlank()) {
                throw new InvalidFileException(InvalidFileException.EXIT_ENTRY);
            }
        }

        return exitEntry;
    }

    /**
     * Manipula o array de Divisões {@link imf.entity.Division} em função do
     * array de Strings de saidas entradas, ou seja modifica o array de divisões
     * tornando a variavel de classe booleana(relativamente a divisao ser de
     * saida ou entrada) verdadeira ou falsa, conforme o caso
     *
     * @param edificio array de Divisões instancia de
     * {@link imf.entity.Division} a ser manipulado
     * @param exitEntry array de Strings que contem o nome das divisões que
     * representam uma entrada ou saida do edificio
     */
    protected void manipulateExitEntry(Division[] edificio, String[] exitEntry) {

        for (int i = 0; i < edificio.length; i++) {
            for (int j = 0; j < exitEntry.length; j++) {
                if (edificio[i].getName().compareTo((exitEntry[j])) == 0) {
                    edificio[i].setEntryExit();
                }
            }
        }
    }

    /**
     * Lê o array de inimigos onde cada posição representa uma instancia de
     * {@link imf.entity.Enemy}
     *
     * @return um array de inimigos onde cada instancia é
     * {@link imf.entity.Enemy}
     * @throws InvalidFileException caso apos a leitura do json o array de
     * inimigos esteja seja invalido
     */
    public Enemy[] readJsonEnemy() throws InvalidFileException {

        JsonArray enemyJson = (JsonArray) json.get("inimigos");
        int tam = enemyJson.size();

        Enemy[] enemies = new Enemy[tam];

        for (int i = 0; i < tam; i++) {
            enemies[i] = new Gson().fromJson(enemyJson.get(i), Enemy.class);
        }

        if (!Enemy.isValid(enemies)) {
            throw new InvalidFileException(InvalidFileException.ENEMY);
        }

        return enemies;
    }

    /**
     * Manipula o array de Divisões( onde cada posição é uma instancia de
     * {@link imf.entity.Division}) em função do array de inimigos ( onde cada
     * posição é uma instancia de {@link imf.entity.Enemy}) , ou seja modifica o
     * array de divisões adicionando os inimigos que se encontram nessa divisão.
     *
     * @param enemies array de inimigos onde cada posição é uma instancia de
     * {@link imf.entity.Enemy}
     * @param edificio array de Divisões onde cada posição é uma instancia de
     * {@link imf.entity.Division}
     * @throws NullException caso o inimigo que se esteja a adicionar seja nulo
     * @throws InvalidTypeException caso o inimigo que se queira adicionar for
     * invalido
     */
    private void readJsonEnemyDivision(Enemy[] enemies, Division[] edificio) throws NullException, InvalidTypeException {
        for (Division divisao : edificio) {
            for (Enemy enemie : enemies) {
                if (divisao.getName().compareToIgnoreCase(enemie.getDivision()) == 0) {
                    divisao.addEnemy(enemie);
                }
            }
        }
    }

    /**
     * Lê as ligações armazenando-as numa matriz de string's.
     *
     * @return uma matriz de string's que possui as ligações.
     * @throws InvalidFileException caso o tamanho das linhas seja menor ou
     * igual a zero ou cado o tamanho das colunas seja diferente de dois
     */
    protected String[][] readJsonEdges() throws InvalidFileException {

        JsonArray edgesJson = (JsonArray) json.get("ligacoes");
        int tamL = edgesJson.size();

        if (tamL <= 0) {
            throw new InvalidFileException(InvalidFileException.EDGES);
        }
        String[][] edges = new String[edgesJson.size()][2];

        Gson gson = new Gson();

        for (int i = 0; i < edgesJson.size(); i++) {
            int tamC = Integer.parseInt(gson.toJson(edgesJson.get(i).getAsJsonArray().size()));

            if (tamC <= 0 || tamC > 2) {
                throw new InvalidFileException(InvalidFileException.EDGES);
            }
            for (int j = 0; j < 2; j++) {
                String result = gson.toJson(edgesJson.get(i).getAsJsonArray().get(j)).replaceAll("[+.^:,\"]", "");
                edges[i][j] = result;
            }
        }
        return edges;
    }

    /**
     * Lê os dados de uma instancia da classe {@link imf.entity.Mission} e como
     * esta possui uma variavel de classe que é uma instancia de
     * {@link imf.entity.Target},ira ler tambem os seus dados.
     *
     * @return instancia da classe {@link imf.entity.Mission} com os dados
     * contidos no ficheiro
     * @throws InvalidFileException InvalidFileException caso a instancia de
     * {@link imf.entity.Mission} ou {@link imf.entity.Target} seja invalida
     */
    public Mission readJsonMission() throws InvalidFileException {

        JsonObject target = (JsonObject) json.getAsJsonObject();
        Mission missao = new Mission();

        try {
            missao = new Gson().fromJson(target.getAsJsonObject(), Mission.class);

            if (!missao.isValid()) {
                throw new InvalidFileException();
            }
        } catch (Exception ex) {
            throw new InvalidFileException(InvalidFileException.MISSING_DATA);
        }

        return missao;
    }

}
