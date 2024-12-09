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
public class InvalidTypeException extends Exception {

    public static String ENEMY_EQUALS = "\n -Não é possivel adicionar inimigos com o mesmo nome";
    public static String SIMULATION_INVALID= "\n -Não é possivel adicionar a sua simulação ao agente pois esta é invalida";
   
    
    /**
     * Cria uma nova instancia <code>InvalidTypeException</code> sem uma
     * mensagem detalhada
     */
    public InvalidTypeException() {
    }

    /**
     * Cria uma nova instancia  <code>InvalidTypeException</code> com uma
     * mensagem detalhada
     *
     * @param message mensagem detalhada
     */
    public InvalidTypeException(String message) {
        super(message);
    }

}
