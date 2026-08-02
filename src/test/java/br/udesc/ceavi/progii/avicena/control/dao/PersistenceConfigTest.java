package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersistenceConfigTest {

    private static final String URL_PROPERTY = "AVICENA_DB_URL";

    private String originalUrl;

    @BeforeEach
    void captureSystemProperties() {
        originalUrl = System.getProperty(URL_PROPERTY);
    }

    @AfterEach
    void restoreSystemProperties() {
        if (originalUrl == null) {
            System.clearProperty(URL_PROPERTY);
        } else {
            System.setProperty(URL_PROPERTY, originalUrl);
        }
    }

    @Test
    void connectsToPostgresAndRunsAQuery() {
        EntityManagerFactory emf = PersistenceConfig.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT COUNT(p) FROM PatientEntity p");
        Object result = query.getSingleResult();

        em.close();
        emf.close();

        assertTrue(result instanceof Long);
    }

    @Test
    void honorsSystemPropertyOverrideForJdbcUrl() {
        System.setProperty(URL_PROPERTY, "jdbc:postgresql://localhost:1/doesnotexist");

        assertThrows(PersistenceException.class, () -> {
            EntityManagerFactory emf = PersistenceConfig.createEntityManagerFactory();
            EntityManager em = emf.createEntityManager();
            em.createQuery("SELECT COUNT(p) FROM PatientEntity p").getSingleResult();
        });
    }
}
