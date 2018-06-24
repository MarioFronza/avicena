/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.dao;

import br.udesc.ceavi.avicena.control.exceptions.ValorNuloException;
import br.udesc.ceavi.progii.avicena.model.Medico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Esta classe define as operações do tipo DAO para os objetos do tipo Médico.
 * @author Adroan, Mário, Rapha, Vini
 * @since 29/05/2018
 * @version 1.0
 */
public class MedicoDAO implements DAO<Medico>{

    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    public MedicoDAO() {
        super();
        emf = javax.persistence.Persistence.createEntityManagerFactory("AvicenaBD");
        em = emf.createEntityManager();
    }

    
    
    @Override
    public boolean inserir(Medico obj) throws ValorNuloException {
        if(obj.getNome().isEmpty())
            throw new ValorNuloException("Nome do Médico em aberto");
        
        if(obj.getCpf().isEmpty())
            throw  new ValorNuloException("Cpf em aberto");
        
        if(obj.getTelefone().isEmpty())
            throw  new ValorNuloException("Telefone em aberto");
        
        if(obj.getEspecializacao().isEmpty())
            throw  new ValorNuloException("Especialização do médico em aberto");
        
        return true;
    }

    @Override
    public boolean atualizar(Medico obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletar(Medico obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Medico pesquisarPorId(Class tipo, long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Medico> getList() {
        Query query = em.createQuery("SELECT e FROM Medico e");
        List<Medico> medicos = query.getResultList();
        return medicos;
    }

    
    
    
    
}
