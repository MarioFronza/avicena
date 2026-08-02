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
 * Esta Classe contem os atributos e métodos para todo Enfermeito
 * @author Mário, Adroan, Vini e Raphael
 * @since 24/03/2018
 * @version 1.0
 */
@Entity
@Table(name = "enfermeiro")
public class Enfermeiro extends Pessoa{
    
    @Column(name = "formacao" , nullable = false)
    private String formacao;
    @Column(name = "hr_cursadas" , nullable = false)
    private int hrCursadas;

    public Enfermeiro() {
        super();
    }

    public Enfermeiro(int id, String formacao, int hrCursadas, List<Consulta> consultas) {
        super();
        this.formacao = formacao;
        this.hrCursadas = hrCursadas;
    }

    public Enfermeiro(int id, String formacao, int hrCursadas, List<Consulta> consultas, int cargaHoraria, float salario, int horaExtra, int numeroCarteiraDeTrabalho) {
        this.formacao = formacao;
        this.hrCursadas = hrCursadas;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public int getHrCursadas() {
        return hrCursadas;
    }

    public void setHrCursadas(int hrCursadas) {
        this.hrCursadas = hrCursadas;
    }
    
    
 


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.formacao);
        hash = 31 * hash + this.hrCursadas;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Enfermeiro other = (Enfermeiro) obj;
        if (this.hrCursadas != other.hrCursadas) {
            return false;
        }
        if (!Objects.equals(this.formacao, other.formacao)) {
            return false;
        }
      
        return true;
    }

    @Override
    public String toString() {
        return "Enfermeiro{" + "formacao=" + formacao + ", hrCursadas=" + hrCursadas + '}';
    }

    

    
    
    

    
    
}
