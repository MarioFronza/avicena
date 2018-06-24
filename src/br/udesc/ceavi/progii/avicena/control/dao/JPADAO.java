/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Esta Interface define as funções básicas de inserçao, consulta, alteração e exclusão de objetos utilizando o padrão JPA
 * @author Adroan, Mário, Rapha, Vini
 * @since 29/05/2018
 * @version 1.0
 */
public class JPADAO<X> implements DAO{
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public JPADAO() {
        super();
        emf = javax.persistence.Persistence.createEntityManagerFactory("AvicenaBD");
        em = emf.createEntityManager();
    }
    
    
    
    @Override
    public boolean inserir(Object obj) throws Exception {
        em.getTransaction().begin();
        
        try {
            em.persist(obj);
            em.getTransaction().commit();
            
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            e.printStackTrace();
            em.getTransaction().rollback();
        } finally{
            em.close();
        }
        
        return false;
    }

    @Override
    public boolean atualizar(Object obj) throws Exception {
        em.getTransaction().begin();
        try {
            em.merge(obj);
            em.getTransaction().commit();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        
        return false;
    }

    @Override
    public boolean deletar(Object obj) throws Exception {
        em.getTransaction().begin();
        try {
            em.remove(obj);
            em.getTransaction().commit();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        
        return false;
    }

    @Override
    public Object pesquisarPorId(Class tipo, long id) throws Exception {
        Object object = null;
        em.getTransaction().begin();
        
        try {
            object = em.find(tipo, id);
            
            
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        
        return object;
    }

    @Override
    public List getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
