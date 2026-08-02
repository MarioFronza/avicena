/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.model;

import static com.itextpdf.text.pdf.PdfName.ID;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 * Esta Classe contem os atributos e métodos para todo Médico
 * @author Mário, Adroan, Vini e Raphael
 * @since 24/03/2018
 * @version 1.0
 */
@Entity
@Table(name = "medico")
public class Medico extends Pessoa{
   
    
    @Column(name = "crm")
    private String crm;
    @Column(name = "especializacao")
    private String especializacao; 

    public Medico() {
        super();
    }

    public Medico(String crm, String especializacao, List<Consulta> consultas) {
        super();
        this.crm = crm;
        this.especializacao = especializacao;
    }

    

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.especializacao);
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
        final Medico other = (Medico) obj;
        if (this.crm != other.crm) {
            return false;
        }
        if (!Objects.equals(this.especializacao, other.especializacao)) {
            return false;
        }
       
        return true;
    }

    @Override
    public String toString() {
        return "Medico{" + "crm=" + crm + ", especializacao=" + especializacao + '}';
    }

    


    

    
}
