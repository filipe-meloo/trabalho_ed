/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imf.exception;

/**
 *
 * Estrutura de Dados - 2020-2021.
 *
 * @author Mariana Ribeiro - 8190573
 * @author André Raro - 8190590
 */
public class InvalidFileException extends Exception {

    public static String FILE_NULL = "\n -O caminho do ficheiro é null";
    public static String MISSING_DATA = "\n -O ficheiro não possui todos os dados necessarios para reconhecer uma missão";
    public static String ENEMY = "\n -Não é possivel validar o ficheiro, pois o array de inimigos não é valido";
    public static String ENEMY_NOT_READ = "\n -Não é possivel adicionar os inimigos a divisão pois ao ler o ficheiro é encontrado o seguinte erro: ";
    public static String DIVISION = "\n -Não é possivel validar o ficheiro, pois as divisões não são validas";
    public static String EDGES = "\n -Não é possivel validar o ficheiro, pois as ligações são invalidas";
    public static String EXIT_ENTRY = "\n -Não é possivel validar o ficheiro, pois as entradas e saidas são invalida";
    public static String DIVISION_TARGET = "\n -Não é possivel validar o ficheiro, pois a divisão alvo não existe no edificio";
    public static String DIVISION_EXIT_ENTRY = "\n -Não é possivel validar o ficheiro, pois este não possui pelo menos uma saida e entrada no edificio";
    public static String DIVISION_EDGES = "\n -Não é possivel validar o ficheiro, pois este não possui ligações validas";

    /**
     * Cria uma nova instancia  <code>InvalidFileException</code> sem uma
     * mensagem detalhada
     *
     */
    public InvalidFileException() {
    }

    /**
     * Cria uma nova instancia <code>InvalidFileException</code> com uma
     * mensagem detalhada
     *
     * @param message mensagem detalhada
     */
    public InvalidFileException(String message) {
        super(message);
    }
}
