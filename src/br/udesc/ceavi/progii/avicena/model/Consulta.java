/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 * Esta Classe contem os atributos e métodos para toda Consulta
 * @author Mário, Adroan, Vini e Raphael
 * @since 24/03/2018
 * @version 1.0
 */
@Entity
@Table(name = "consulta")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "codigo")
    private long codigo;
    @Column(name = "data")
    private String data;
    @Column(name = "hora")
    private String hora;
    @Column(name = "sintomas")
    private String sintomas;
    @ManyToOne
    @JoinColumn(name = "codigo_paciente")
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "codigo_enfermeiro")
    private Enfermeiro enfermeiro;
    @ManyToOne
    @JoinColumn(name = "codigo_medico")
    private Medico medico;
    @Column(name = "estado_paciente")
    private EstadoPaciente estadoPaciente;

    public Consulta() {
        super();
    }

    public Consulta(Date data, String hora, String sintomas, int id, Paciente paciente, Enfermeiro enfermeiro, Medico medico, DiagnosticoPrimario diagnosticoPrimario, DiagnosticoFinal diagnosticoFinal, EstadoPaciente estadoPaciente) {
        super();
        this.hora = hora;
        this.sintomas = sintomas;
        this.paciente = paciente;
        this.enfermeiro = enfermeiro;
        this.medico = medico;
        this.estadoPaciente = estadoPaciente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }


    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Enfermeiro getEnfermeiro() {
        return enfermeiro;
    }

    public void setEnfermeiro(Enfermeiro enfermeiro) {
        this.enfermeiro = enfermeiro;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }


    public EstadoPaciente getEstadoPaciente() {
        return estadoPaciente;
    }

    public void setEstadoPaciente(EstadoPaciente estadoPaciente) {
        this.estadoPaciente = estadoPaciente;
    }

     
   

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.data);
        hash = 53 * hash + Objects.hashCode(this.hora);
        hash = 53 * hash + Objects.hashCode(this.sintomas);
        hash = 53 * hash + Objects.hashCode(this.paciente);
        hash = 53 * hash + Objects.hashCode(this.enfermeiro);
        hash = 53 * hash + Objects.hashCode(this.medico);
        hash = 53 * hash + Objects.hashCode(this.estadoPaciente);
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
        final Consulta other = (Consulta) obj;
       
        if (!Objects.equals(this.hora, other.hora)) {
            return false;
        }
        if (!Objects.equals(this.sintomas, other.sintomas)) {
            return false;
        }

        if (!Objects.equals(this.paciente, other.paciente)) {
            return false;
        }
        if (!Objects.equals(this.enfermeiro, other.enfermeiro)) {
            return false;
        }
        if (!Objects.equals(this.medico, other.medico)) {
            return false;
        }
       
        if (this.estadoPaciente != other.estadoPaciente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Consulta{" + "codigo=" + codigo + ", data=" + data + ", hora=" + hora + ", sintomas=" + sintomas + ", paciente=" + paciente + ", enfermeiro=" + enfermeiro + ", medico=" + medico + ", estadoPaciente=" + estadoPaciente + '}';
    }

    
}
