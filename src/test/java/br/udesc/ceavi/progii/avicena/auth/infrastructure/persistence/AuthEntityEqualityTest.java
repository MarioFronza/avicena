package br.udesc.ceavi.progii.avicena.auth.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import br.udesc.ceavi.progii.avicena.control.dao.JpaTransactions;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AuthEntityEqualityTest {

    @Test
    void theSameRoleLoadedTwiceIsEqualAndHashesTheSame() {
        RoleEntity first = JpaTransactions.read(entityManager -> entityManager.find(RoleEntity.class, 1L));
        RoleEntity second = JpaTransactions.read(entityManager -> entityManager.find(RoleEntity.class, 1L));

        assertEquals(first, second);
        assertEquals(1, Set.of(first).stream().filter(second::equals).count());
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void differentRolesAreNotEqual() {
        RoleEntity admin = JpaTransactions.read(entityManager -> entityManager.find(RoleEntity.class, 1L));
        RoleEntity doctor = JpaTransactions.read(entityManager -> entityManager.find(RoleEntity.class, 2L));

        assertNotEquals(admin, doctor);
    }

    @Test
    void unsavedPermissionsAreOnlyEqualToThemselves() {
        PermissionEntity permission = new PermissionEntity(null, "patient:read", null);

        assertEquals(permission, permission);
        assertNotEquals(permission, new PermissionEntity(null, "patient:read", null));
    }
}
