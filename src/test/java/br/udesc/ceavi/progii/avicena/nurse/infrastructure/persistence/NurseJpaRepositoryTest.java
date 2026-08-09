package br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class NurseJpaRepositoryTest {

    @Test
    void savePersistsANurseThatIsRetrievableAfterward() {
        Nurse nurse =
                new Nurse("Joana Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "Tecnico", 1200);
        NurseJpaRepository repository = new NurseJpaRepository();

        Nurse saved = repository.save(nurse);

        assertNotNull(saved.getId());
        Optional<Nurse> found = repository.findAll().stream()
                .filter(n -> n.getCpf().equals("12345678900"))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals("Joana Teste", found.get().getName());
        assertEquals("Tecnico", found.get().getFormation());
        assertEquals(1200, found.get().getHoursCompleted());
    }

    @Test
    void savePersistsANurseWithAMaritalStatusThatRoundTripsCorrectly() {
        Nurse nurse =
                new Nurse("Casado Teste", "44444444444", "48966660000", null, MaritalStatus.MARRIED, "Tecnico", 900);
        NurseJpaRepository repository = new NurseJpaRepository();

        repository.save(nurse);

        Optional<Nurse> found = repository.findAll().stream()
                .filter(n -> n.getCpf().equals("44444444444"))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals(MaritalStatus.MARRIED, found.get().getMaritalStatus());
    }

    @Test
    void deleteRemovesAPreviouslyPersistedNurse() {
        Nurse nurse =
                new Nurse("Maria Teste", "98765432100", "48988880000", null, MaritalStatus.SINGLE, "Enfermeira", 800);
        NurseJpaRepository repository = new NurseJpaRepository();
        Nurse saved = repository.save(nurse);

        repository.delete(saved);

        boolean stillPresent =
                repository.findAll().stream().anyMatch(n -> n.getCpf().equals("98765432100"));
        assertFalse(stillPresent);
    }
}
