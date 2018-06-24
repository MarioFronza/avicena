/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.dao;

import br.udesc.ceavi.avicena.control.exceptions.ValorNuloException;
import br.udesc.ceavi.progii.avicena.model.Endereco;
import java.util.List;

/**
 * Esta classe define as operações do tipo DAO para os objetos do tipo Endereço.
 * @author Adroan, Mário, Rapha, Vini
 * @since 29/05/2018
 * @version 1.0
 */
public class EnderecoDAO implements DAO<Endereco> {

    @Override
    public boolean inserir(Endereco obj) throws ValorNuloException {
        if(obj.getBairro().isEmpty())
            throw new ValorNuloException("Bairro em aberto");
        
        if(obj.getCep().isEmpty())
            throw new ValorNuloException("Cep em aberto");
        
        if(obj.getCidade().isEmpty())
            throw new ValorNuloException("Cidade em aberto");
        
        if(obj.getComplemento().isEmpty())
            throw new ValorNuloException("Complemento em aberto");
        
        return true;
    }

    @Override
    public boolean atualizar(Endereco obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletar(Endereco obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Endereco> getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Endereco pesquisarPorId(Class tipo, long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
