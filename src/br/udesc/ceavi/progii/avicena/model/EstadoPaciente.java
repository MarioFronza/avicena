/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.model;

import javax.persistence.*;

/**
 * Esta Classe contem os estados de um paciente
 * @author Mário, Adroan, Vini e Raphael
 * @since 24/03/2018
 * @version 1.0
 */

public enum EstadoPaciente {

    EMERGENCIA ("Emergência"), 
    URGENCIA ("Urgência"),
    POUCOURGENTE ("Pouco urgente"),
    NAOURGENTE ("Não urgente");

    private int id;
    private String estadoPaciente;

    private EstadoPaciente(String estadoPaciente) {
        this.estadoPaciente = estadoPaciente;
    }

    @Override
    public String toString() {
        return estadoPaciente;
    }

    
    
    
}
