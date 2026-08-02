/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.model;

import javax.persistence.*;

/**
 * Esta Classe contem os atributos e métodos para todo Atendente
 * @author Mário, Adroan, Vini e Raphael
 * @since 24/03/2018
 * @version 1.0
 */
@Entity
@Table(name = "atendente")
public class Atendente extends Pessoa{
    
    @Column(name = "carga_horaria")
    private int cargaHoraria;
    @Column(name = "salario")
    private float salario;
    @Column(name = "hora_extra")
    private int horaExtra;
    @Column(name = "numero_carteira_de_trabalho")
    private int numeroCarteiraDeTrabalho;

    public Atendente() {
        super();
    }

    public Atendente(int cargaHoraria, float salario, int horaExtra, int numeroCarteiraDeTrabalho) {
        super();
        this.cargaHoraria = cargaHoraria;
        this.salario = salario;
        this.horaExtra = horaExtra;
        this.numeroCarteiraDeTrabalho = numeroCarteiraDeTrabalho;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public int getHoraExtra() {
        return horaExtra;
    }

    public void setHoraExtra(int horaExtra) {
        this.horaExtra = horaExtra;
    }

    public int getNumeroCarteiraDeTrabalho() {
        return numeroCarteiraDeTrabalho;
    }

    public void setNumeroCarteiraDeTrabalho(int numeroCarteiraDeTrabalho) {
        this.numeroCarteiraDeTrabalho = numeroCarteiraDeTrabalho;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.cargaHoraria;
        hash = 97 * hash + Float.floatToIntBits(this.salario);
        hash = 97 * hash + this.horaExtra;
        hash = 97 * hash + this.numeroCarteiraDeTrabalho;
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
        final Atendente other = (Atendente) obj;
        if (this.cargaHoraria != other.cargaHoraria) {
            return false;
        }
        if (Float.floatToIntBits(this.salario) != Float.floatToIntBits(other.salario)) {
            return false;
        }
        if (this.horaExtra != other.horaExtra) {
            return false;
        }
        if (this.numeroCarteiraDeTrabalho != other.numeroCarteiraDeTrabalho) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Funcionario{" + "cargaHoraria=" + cargaHoraria + ", salario=" + salario + ", horaExtra=" + horaExtra + ", numeroCarteiraDeTrabalho=" + numeroCarteiraDeTrabalho + '}';
    }    
    
}
