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
public class UnsupportedDataTypeException extends RuntimeException {

    public static String NOT_COMPARABLE = "\n -O elemento não é comparavel";

    /**
     * Cria uma nova instancia  <code>UnsupportedTypeException</code> sem uma mensagem
     * detalhada
     */
    public UnsupportedDataTypeException() {
    }

    /**
     * Cria uma nova instancia  <code>UnsupportedTypeException</code> com uma mensagem
     * detalhada
     *
     * @param message mensagem detalhada
     */
    public UnsupportedDataTypeException(String message) {
        super(message);
    }

}
