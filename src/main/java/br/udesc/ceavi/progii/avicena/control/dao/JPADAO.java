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
    private static final EntityManagerFactory emf = PersistenceConfig.createEntityManagerFactory();

    @Override
    public boolean inserir(Object obj) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        } finally{
            em.close();
        }

        return false;
    }

    @Override
    public boolean atualizar(Object obj) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return false;
    }

    @Override
    public boolean deletar(Object obj) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(obj);
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return false;
    }

    @Override
    public Object pesquisarPorId(Class tipo, long id) throws Exception {
        EntityManager em = emf.createEntityManager();
        Object object = null;

        try {
            object = em.find(tipo, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return object;
    }

    @Override
    public List getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
