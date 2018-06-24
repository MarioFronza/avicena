/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.avicena.control.exceptions;

/**
 * Classe responsavel pela exceção de um valor nulo
 * @author Mário, Vini, Adroan, Raphael
 * @version 1.0
 * @since 28/05/2018
 */
public class ValorNuloException extends Exception{
    
    public ValorNuloException(String message) {
        super(message);
    }

    public ValorNuloException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValorNuloException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
