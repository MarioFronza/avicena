/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.model;

import java.util.Objects;
import javax.persistence.*;

/**
 * Esta Classe contem os atributos e métodos para toda Diagnostico Primario
 * @author Mário, Adroan, Vini e Raphael
 * @since 24/03/2018
 * @version 1.0
 */
@Entity
@Table(name = "diagnostico_primario")
public class DiagnosticoPrimario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "codigo")
    private long codigo;
    @Column(name = "pressao")
    private float pressao;
    @Column(name = "temperatura")
    private int temperatura;
    @Column(name = "altura")
    private int altura;
    @Column(name = "peso")
    private int peso;
    @Column(name = "historico_remedio")
    private String Descricao;
    @ManyToOne
    @JoinColumn(name = "id_consulta")
    private Consulta consulta;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_diagnostico_final")
    private DiagnosticoFinal diagnosticoFinal;
    
    
    public DiagnosticoPrimario() {
        super();
    }

    public DiagnosticoPrimario(float pressao, int temperatura, int altura, int peso, String historicoRemedio) {
        super();
        this.pressao = pressao;
        this.temperatura = temperatura;
        this.altura = altura;
        this.peso = peso;
        this.Descricao = historicoRemedio;
    }

    public float getPressao() {
        return pressao;
    }

    public void setPressao(float pressao) {
        this.pressao = pressao;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getHistoricoRemedio() {
        return Descricao;
    }

    public void setHistoricoRemedio(String historicoRemedio) {
        this.Descricao = historicoRemedio;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public DiagnosticoFinal getDiagnosticoFinal() {
        return diagnosticoFinal;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public void setDiagnosticoFinal(DiagnosticoFinal diagnosticoFinal) {
        this.diagnosticoFinal = diagnosticoFinal;
    }


    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Float.floatToIntBits(this.pressao);
        hash = 47 * hash + this.temperatura;
        hash = 47 * hash + this.altura;
        hash = 47 * hash + this.peso;
        hash = 47 * hash + Objects.hashCode(this.Descricao);
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
        final DiagnosticoPrimario other = (DiagnosticoPrimario) obj;
        if (Float.floatToIntBits(this.pressao) != Float.floatToIntBits(other.pressao)) {
            return false;
        }
        if (this.temperatura != other.temperatura) {
            return false;
        }
        if (this.altura != other.altura) {
            return false;
        }
        if (this.peso != other.peso) {
            return false;
        }
        if (!Objects.equals(this.Descricao, other.Descricao)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DiagnosticoPrimario{" + "pressao=" + pressao + ", temperatura=" + temperatura + ", altura=" + altura + ", peso=" + peso + ", historicoRemedio=" + Descricao + '}';
    }
    
}
