/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.dao;

import br.udesc.ceavi.avicena.control.exceptions.ValorNuloException;
import br.udesc.ceavi.progii.avicena.model.Atendente;
import java.util.List;

/**
 * Esta classe define as operações do tipo DAO para os objetos do tipo Atendente.
 * @author Adroan, Mário, Rapha, Vini
 * @since 29/05/2018
 * @version 1.0
 */
public class AtendenteDAO implements DAO<Atendente>{

    @Override
    public boolean inserir(Atendente obj) throws ValorNuloException {
        if(obj.getNome().isEmpty())
            throw new ValorNuloException("Nome do enfermeiro em aberto");
        
        if(obj.getCpf().isEmpty())
            throw  new ValorNuloException("Cpf em aberto");
        
        if(obj.getTelefone().isEmpty())
            throw  new ValorNuloException("Telefone em aberto");
        
 
       return true;
    }

    @Override
    public boolean atualizar(Atendente obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletar(Atendente obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Atendente pesquisarPorId(Class tipo, long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Atendente> getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
