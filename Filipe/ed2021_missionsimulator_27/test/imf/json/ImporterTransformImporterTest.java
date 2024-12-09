/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imf.json;

import imf.exception.InvalidFileException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * Estrutura de Dados - 2020-2021.
 *
 * @author Mariana Ribeiro - 8190573
 * @author André Raro - 8190590
 *
 * Classe que testa a importação e transformação do ficheiro
 */
public class ImporterTransformImporterTest {

    /**
     * Mensagem lançada quando o teste falha 
     */
    private static String FAIL = "A exceção esperada não foi lançada";

    /**
     * Testa a leitura de um ficheiro que possui um caminho nulo.
     */
    @Test
    public void fileNull() {
        try {
            Importer importer = new Importer(null);
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.FILE_NULL, ex.getMessage());
        }

    }

    /**
     * Testa a leitura de um ficheiro que possui um caminho que não existe.
     */
    @Test
    public void filePathDontExist() {
        try {
            Importer importer = new Importer("files/missao.json");
            fail(FAIL);
        } catch (InvalidFileException ex) {
        }

    }

    /**
     * Testa a leitura de um ficheiro que não existe.
     */
    @Test
    public void fileDontExist() {
        try {
            Importer importer = new Importer("resources/filesToTest/mission.json");
            fail(FAIL);
        } catch (InvalidFileException ex) {
        }

    }

    /**
     * Testa um ficheiro vazio que apenas possui as {} a identificar que é um
     * documento.
     */
    @Test
    public void fileEmpty() {
        try {
            Importer importer = new Importer("resources/filesToTest/empty.json");
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.FILE_NULL, ex.getMessage());
        }

    }

    /**
     * Testa um ficheiro onde faltam dados ao inimigo, nomeadamente o seu nome.
     */
    @Test
    public void missingDataEnemy() {
        try {
            Importer importer = new Importer("resources/filesToTest/missingDataEnemy.json");
            importer.readJsonEnemy();
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.ENEMY, ex.getMessage());
        }

    }

    /**
     * Testa um ficheiro que possui o array de inimigos vazio.
     */
    @Test
    public void enemiesEmpty() {
        try {
            Importer importer = new Importer("resources/filesToTest/enemiesEmpty.json");
            importer.readJsonEnemy();
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.ENEMY, ex.getMessage());
        }

    }

    /**
     * Testa um ficheiro que possui um array de ligações vazio.
     */
    @Test
    public void emptyEdges() {
        try {
            Importer importer = new Importer("resources/filesToTest/emptyEdges.json");
            importer.readJsonEdges();
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.EDGES, ex.getMessage());
        }

    }

    /**
     * Testa um ficheiro onde o array das ligações possui um tamanho diferente
     * de duas colunas.
     */
    @Test
    public void edgesSizeInvalid() {
        try {
            Importer importer = new Importer("resources/filesToTest/edgesSizeInvalid.json");
            importer.readJsonEdges();
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.EDGES, ex.getMessage());
        }

    }

    /**
     * Testa um ficheiro onde o array de entradas e saidas não possui pelo menos
     * uma divisão tambem presente no edificio.
     */
    @Test
    public void exitEntryInvalid() {
        try {
            Importer importer = new Importer("resources/filesToTest/exitEntryInvalid.json");
            importer.readJsonExitEntry();
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.EXIT_ENTRY, ex.getMessage());
        }

    }

    /**
     * Testa um ficheiro que possui o array de entradas e saidas vazio.
     */
    @Test
    public void exitEntryEmpty() {
        try {
            Importer importer = new Importer("resources/filesToTest/exitEntryEmpty.json");
            importer.readJsonExitEntry();
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.EXIT_ENTRY, ex.getMessage());
        }

    }

    /**
     * Testa um ficheiro que não possui o codigo da missão.
     */
    @Test
    public void missingCodMission() {
        try {
            Importer importer = new Importer("resources/filesToTest/missingCod.json");
            importer.readJsonMission();
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.MISSING_DATA, ex.getMessage());
        }

    }

    /**
     * Testa um ficheiro que não possui a sua versão.
     */
    @Test
    public void missingVersionMission() {
        try {
            Importer importer = new Importer("resources/filesToTest/missingVersion.json");
            importer.readJsonMission();
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.MISSING_DATA, ex.getMessage());
        }

    }

    /**
     * Testa um ficheiro onde a divisão alvo não existe.
     */
    @Test
    public void missingDivisionTarget() {
        try {
            Importer importer = new Importer("resources/filesToTest/missingDivisionTarget.json");
            importer.readJsonMission();
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.MISSING_DATA, ex.getMessage());
        }

    }

    /**
     * Testa um ficheiro onde a divisão alvo não se encontra no edificio.
     */
    @Test
    public void TargetDivisionInvalid() {
        try {
            TransformImporter importer = new TransformImporter("resources/filesToTest/targetDivisionInvalid.json");
            importer.createMission();
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.DIVISION_TARGET, ex.getMessage());
        }

    }

    /**
     * Testa um ficheiro onde as divisões de entrada e saida nao existem no
     * edificio.
     */
    @Test
    public void ExitEntryInvalidInBuilding() {
        try {
            TransformImporter importer = new TransformImporter("resources/filesToTest/exitEntryInvalidInBuilding.json");
            importer.createMission();
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.DIVISION_EXIT_ENTRY, ex.getMessage());
        }

    }

    /**
     * Testa um ficheiro onde a divisão na primeira coluna não esta presente no
     * edificio.
     */
    @Test
    public void EdgesInBuildingInvalidPos0() {
        try {
            TransformImporter importer = new TransformImporter("resources/filesToTest/edgesInBuildingInvalidPos0.json");
            importer.createMission();
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.DIVISION_EDGES, ex.getMessage());
        }

    }

    /**
     * Testa um ficheiro onde a divisão na sengunda coluna não esta presente no
     * edificio.
     */
    @Test
    public void EdgesInBuildingInvalidPos1() {
        try {
            TransformImporter importer = new TransformImporter("resources/filesToTest/edgesInBuildingInvalidPos1.json");
            importer.createMission();
            fail(FAIL);
        } catch (InvalidFileException ex) {
            assertEquals(InvalidFileException.DIVISION_EDGES, ex.getMessage());
        }

    }
}
