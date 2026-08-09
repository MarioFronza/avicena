package br.udesc.ceavi.progii.avicena.auth.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;

class RoleEntityTest {

    @Test
    void grantingAPermissionToASeededRoleRoundTripsTheRolePermissionsJoin() {
        EntityManagerFactory entityManagerFactory = PersistenceConfig.createEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            PermissionEntity permission = new PermissionEntity(null, "patient:write", "Create or update a patient");

            entityManager.getTransaction().begin();
            entityManager.persist(permission);
            RoleEntity admin = entityManager.find(RoleEntity.class, 1L);
            admin.getPermissions().add(permission);
            entityManager.getTransaction().commit();

            entityManager.clear();
            RoleEntity reloaded = entityManager.find(RoleEntity.class, 1L);
            assertTrue(
                    reloaded.getPermissions().stream().anyMatch(p -> p.getCode().equals("patient:write")));
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
