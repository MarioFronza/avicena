/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.avicena.control.exceptions;

/**
 * Classe responsavel pela exceção de um valor alfabético
 * @author Mário, Vini, Adroan, Raphael
 * @version 1.0
 * @since 28/05/2018
 */
public class ValorIncorretoException extends Exception{
    
    public ValorIncorretoException(String message) {
        super("Valor digitado incorretamente, favor informe um valor númerico " + message);
    }

    public ValorIncorretoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValorIncorretoException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
