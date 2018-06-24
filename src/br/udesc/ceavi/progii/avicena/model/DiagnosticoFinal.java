/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.model;

import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 * Esta Classe contem os atributos e métodos para todo Diagnostico Final
 * @author Mário, Adroan, Vini e Raphael
 * @since 24/03/2018
 * @version 1.0
 */
@Entity
@Table(name = "diagnostico_final")
public class DiagnosticoFinal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "codigo")
    private long codigo;
    @Column(name = "doenca")
    private String doenca;
    @Column(name = "remedios")
    private String remedios;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "exame")
    private String exames;
    

    public DiagnosticoFinal() {
        super();
    }

    public DiagnosticoFinal(String doenca, String remedios, String descricao) {
        super();
        this.doenca = doenca;
        this.remedios = remedios;
        this.descricao = descricao;
    }

    public String getDoenca() {
        return doenca;
    }

    public void setDoenca(String doenca) {
        this.doenca = doenca;
    }

    public String getRemedios() {
        return remedios;
    }

    public void setRemedios(String remedios) {
        this.remedios = remedios;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getExames() {
        return exames;
    }

    public void setExames(String exames) {
        this.exames = exames;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.doenca);
        hash = 71 * hash + Objects.hashCode(this.remedios);
        hash = 71 * hash + Objects.hashCode(this.descricao);
        hash = 71 * hash + Objects.hashCode(this.exames);
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
        final DiagnosticoFinal other = (DiagnosticoFinal) obj;
        if (!Objects.equals(this.doenca, other.doenca)) {
            return false;
        }
        if (!Objects.equals(this.remedios, other.remedios)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.exames, other.exames)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DiagnosticoFinal{" + "doenca=" + doenca + ", remedios=" + remedios + ", descricao=" + descricao + ", exames=" + exames + '}';
    }
    

   
}
