/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.model;

import java.util.Objects;
import javax.persistence.*;

/**
 * Esta classe define as características e comportamentos para toda pessoa
 * @version 1.0
 * @since 23/03/2018
 * @author Mário, Adroan, Vinicius, Raphael
 */
@Entity
@Table(name = "Pessoa")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pessoa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "codigo")
    private long codigo;
    @Column(name = "nome" , nullable = false)
    private String nome;
    @Column(name = "cpf" , nullable = false)
    private String cpf;
    @Column(name = "telefone" , nullable = false)
    private String telefone;
    @ManyToOne
    @JoinColumn(name = "codigo_endereco")
    private Endereco endereco;
    @Column(name = "estado_civil")
    private EstadoCivil estadoCivil;

    public Pessoa() {
        super();
    }

    public Pessoa(String nome, String cpf, long codigo, String telefone, Endereco endereco, EstadoCivil estadoCivil) {
        super();
        this.nome = nome;
        this.cpf = cpf;
        this.codigo = codigo;
        this.telefone = telefone;
        this.endereco = endereco;
        this.estadoCivil = estadoCivil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.nome);
        hash = 17 * hash + Objects.hashCode(this.cpf);
        hash = 17 * hash + (int) (this.codigo ^ (this.codigo >>> 32));
        hash = 17 * hash + Objects.hashCode(this.telefone);
        hash = 17 * hash + Objects.hashCode(this.endereco);
        hash = 17 * hash + Objects.hashCode(this.estadoCivil);
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
        final Pessoa other = (Pessoa) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            return false;
        }
        if (this.estadoCivil != other.estadoCivil) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pessoa{" + "nome=" + nome + ", cpf=" + cpf + ", codigo=" + codigo + ", telefone=" + telefone + ", endereco=" + endereco + ", estadoCivil=" + estadoCivil + '}';
    }
    
    
    
    
}
