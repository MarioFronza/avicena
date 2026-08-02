/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.model;


/**
 * Esta Classe contem estados civis para toda Pessoa
 * @author Mário, Adroan, Vini e Raphael
 * @since 24/03/2018
 * @version 1.0
 */

public enum EstadoCivil {
    
    SOLTEIRO ("Solteiro"), 
    CASADO ("Casado"),
    DIVORCIADO ("Divorciado"),
    VIUVO ("Viuvo"),
    OUTRO ("Outro");

    private int id;

    private String estadoCivil;

    private EstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    @Override
    public String toString() {
        return estadoCivil;
    }

 
    
    
    
    
}
