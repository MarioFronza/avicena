package br.udesc.ceavi.progii.avicena.auth.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;

class UserEntityTest {

    @Test
    void persistsAUserForAnExistingPersonAndRoundTripsItsFields() {
        EntityManagerFactory entityManagerFactory = PersistenceConfig.createEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            PersonEntity person = new PersonEntity(null, "Login Teste", "10101010101", "48900000000", null, null);
            UserEntity user = new UserEntity(null, person, "login.teste", "hashed-password", true);

            entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.persist(user);
            entityManager.getTransaction().commit();

            UserEntity found = entityManager.find(UserEntity.class, user.getId());
            assertNotNull(found);
            assertEquals("login.teste", found.getUsername());
            assertEquals("hashed-password", found.getPasswordHash());
            assertTrue(found.isActive());
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
