/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imf.entity;

/**
 * Estrutura de Dados - 2020-2021.
 *
 * @author Mariana Ribeiro - 8190573
 * @author André Raro - 8190590
 *
 * Classe que tem como objetivo manipular mais facilmente os caminhos dos
 * ficheiros da importação bem como os de exportação, guarda tambem a extensão
 * do ficheiro.
 */
public class MyFile {

    /**
     * Representa a extensões dos ficheiros.
     */
    private static String EXTENSION = ".json";

    /**
     * Representa o caminho da importação dos ficheiros.
     */
    private static String PATH_IMPORT = "maps/";

    /**
     * Representa o caminho da exportação bem como parte do nome dos ficheiros,
     * sendo essa parte "simulation", com o objetivo de todos os ficheiros de
     * exportação possuir "simulation" no seu nome.
     */
    private static String PATH_EXPORT = "maps/simulation";

    /**
     * Retorna a extensões dos ficheiros.
     *
     * @return a extensões dos ficheiros.
     */
    public static String getEXTENSION() {
        return EXTENSION;
    }

    /**
     * Retorna o caminho da importação dos ficheiros.
     *
     * @return o caminho da importação dos ficheiros.
     */
    public static String getPATH_IMPORT() {
        return PATH_IMPORT;
    }

    /**
     * Retorna o caminho da exportação bem como parte do nome dos ficheiros.
     *
     * @return o caminho da exportação bem como parte do nome dos ficheiros.
     */
    public static String getPATH_EXPORT() {
        return PATH_EXPORT;
    }

}
