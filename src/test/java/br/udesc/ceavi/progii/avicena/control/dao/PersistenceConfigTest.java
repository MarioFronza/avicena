package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.junit.jupiter.api.Test;

class PersistenceConfigTest {

    @Test
    void connectsToPostgresAndRunsAQuery() {
        EntityManagerFactory emf = PersistenceConfig.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT COUNT(p) FROM Paciente p");
        Object result = query.getSingleResult();

        em.close();
        emf.close();

        assertTrue(result instanceof Long);
    }
}
