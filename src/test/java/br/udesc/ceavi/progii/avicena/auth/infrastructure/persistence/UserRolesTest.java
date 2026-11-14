package br.udesc.ceavi.progii.avicena.auth.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

class UserRolesTest {

    @Test
    void assigningARoleToAUserRoundTripsTheUserRolesJoin() {
        EntityManager entityManager = PersistenceConfig.entityManagerFactory().createEntityManager();
        try {
            PersonEntity person = new PersonEntity(null, "Role Test", "20202020202", "48900000001", null, null);
            UserEntity user = new UserEntity(null, person, "role.test", "hashed-password", true);

            entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.persist(user);
            RoleEntity doctorRole = entityManager.find(RoleEntity.class, 2L);
            user.getRoles().add(doctorRole);
            entityManager.getTransaction().commit();

            entityManager.clear();
            UserEntity reloaded = entityManager.find(UserEntity.class, user.getId());
            assertTrue(reloaded.getRoles().stream().anyMatch(r -> r.getName().equals("DOCTOR")));
        } finally {
            entityManager.close();
        }
    }
}
