/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.dao;

import br.udesc.ceavi.avicena.control.exceptions.ValorNuloException;
import br.udesc.ceavi.progii.avicena.model.Enfermeiro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Esta classe define as operações do tipo DAO para os objetos do tipo Enfermeiro.
 * @author Adroan, Mário, Rapha, Vini
 * @since 29/05/2018
 * @version 1.0
 */
public class EnfermeiroDAO implements DAO<Enfermeiro>{

    private static EntityManagerFactory emf;
    private static EntityManager em;

    public EnfermeiroDAO() {
        super();
        emf = javax.persistence.Persistence.createEntityManagerFactory("AvicenaBD");
        em = emf.createEntityManager();
    }

    @Override
    public boolean inserir(Enfermeiro obj) throws ValorNuloException {
        if(obj.getNome().isEmpty())
            throw new ValorNuloException("Nome do enfermeiro em aberto");
        
        if(obj.getCpf().isEmpty())
            throw  new ValorNuloException("Cpf em aberto");
        
        if(obj.getTelefone().isEmpty())
            throw  new ValorNuloException("Telefone em aberto");
        
        if(obj.getFormacao().isEmpty())
            throw new ValorNuloException("Formação em aberto");
        
        
        return true;
    }

    @Override
    public boolean atualizar(Enfermeiro obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletar(Enfermeiro obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Enfermeiro pesquisarPorId(Class tipo, long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Enfermeiro> getList() {
        Query query = em.createQuery("SELECT e FROM Enfermeiro e");
        List<Enfermeiro> enfermeiros = query.getResultList();
        return enfermeiros;
    }
    
}
