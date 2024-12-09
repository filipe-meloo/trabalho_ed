/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imf.exception;

/**
 * Estrutura de Dados - 2020-2021.
 *
 * @author Mariana Ribeiro - 8190573
 * @author André Raro - 8190590
 *
 */
public class NullException extends Exception {

    public static String ENEMY_NULL = "\n -Não é possivel adicionar inimigos nulos";
    public static String SIMULATION_NULL = "\n -Não é possivel a simulação ao agente pois este é nulo";

    /**
     * Cria uma nova instancia  <code>NullException</code> sem uma mensagem
     * detalhada
     */
    public NullException() {
    }

    /**
     * Cria uma nova instancia  <code>NullException</code> com uma mensagem
     * detalhada
     *
     * @param message mensagem detalhada
     */
    public NullException(String message) {
        super(message);
    }

}
