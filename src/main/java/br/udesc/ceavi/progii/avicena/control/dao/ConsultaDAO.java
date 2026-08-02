/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.dao;

import br.udesc.ceavi.avicena.control.exceptions.ValorIncorretoException;
import br.udesc.ceavi.progii.avicena.model.Consulta;
import br.udesc.ceavi.progii.avicena.model.Medico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Esta classe define as operações do tipo DAO para os objetos do tipo Consulta.
 * @author Adroan, Mário, Rapha, Vini
 * @since 29/05/2018
 * @version 1.0
 */
public class ConsultaDAO implements DAO<Consulta>{

    private static EntityManagerFactory emf;
    private static EntityManager em;

    public ConsultaDAO() {
        super();
        emf = javax.persistence.Persistence.createEntityManagerFactory("AvicenaBD");
        em = emf.createEntityManager();
    
    }
    
    
    
    @Override
    public boolean inserir(Consulta obj) throws ValorIncorretoException {
        
        if(obj.getPaciente().getCpf().length() < 11)
            throw new ValorIncorretoException("Valor incorreto cpf");

        return true;
    }

    @Override
    public boolean atualizar(Consulta obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletar(Consulta obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Consulta pesquisarPorId(Class tipo, long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Consulta> getList() {
        Query query = em.createQuery("SELECT e FROM Consulta e");
        List<Consulta> consultas = query.getResultList();
        return consultas;
    }
    
}
