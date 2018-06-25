/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 * Esta Classe contem os atributos e métodos para todo Paciente
 * @author Mário, Adroan, Vini e Raphael
 * @since 24/03/2018
 * @version 1.0
 */
@Entity
@Table(name = "paciente")
public class Paciente extends Pessoa{
    
    
    public Paciente() {
        super();
    }

    public Paciente(String nome, String cpf, long codigo, String telefone, Endereco endereco, EstadoCivil estadoCivil) {
        super(nome, cpf, codigo, telefone, endereco, estadoCivil);
    }

    public void setEstadoCivil(String estado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
