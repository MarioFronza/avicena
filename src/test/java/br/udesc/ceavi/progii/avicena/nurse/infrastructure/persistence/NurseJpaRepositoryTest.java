package br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class NurseJpaRepositoryTest {

    @Test
    void savePersistsANurseThatIsRetrievableAfterward() {
        Nurse nurse =
                new Nurse("Joana Test", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "Technician", 1200);
        NurseJpaRepository repository = new NurseJpaRepository();

        Nurse saved = repository.save(nurse);

        assertNotNull(saved.getId());
        Optional<Nurse> found = repository.findAll().stream()
                .filter(n -> n.getCpf().equals("12345678900"))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals("Joana Test", found.get().getName());
        assertEquals("Technician", found.get().getFormation());
        assertEquals(1200, found.get().getHoursCompleted());
    }

    @Test
    void savePersistsANurseWithAMaritalStatusThatRoundTripsCorrectly() {
        Nurse nurse =
                new Nurse("Married Test", "44444444444", "48966660000", null, MaritalStatus.MARRIED, "Technician", 900);
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
        Nurse nurse = new Nurse("Maria Test", "98765432100", "48988880000", null, MaritalStatus.SINGLE, "Nursing", 800);
        NurseJpaRepository repository = new NurseJpaRepository();
        Nurse saved = repository.save(nurse);

        repository.delete(saved);

        boolean stillPresent =
                repository.findAll().stream().anyMatch(n -> n.getCpf().equals("98765432100"));
        assertFalse(stillPresent);
    }

    @Test
    void findByIdReturnsTheSavedNurse() {
        NurseJpaRepository repository = new NurseJpaRepository();
        Nurse saved = repository.save(nurse("10000000001"));

        assertEquals(
                "10000000001", repository.findById(saved.getId()).orElseThrow().getCpf());
    }

    @Test
    void findByIdIsEmptyForAnUnknownId() {
        assertTrue(new NurseJpaRepository().findById(999_999L).isEmpty());
    }

    @Test
    void findByCpfReturnsTheSavedNurse() {
        NurseJpaRepository repository = new NurseJpaRepository();
        Nurse saved = repository.save(nurse("10000000002"));

        assertEquals(
                saved.getId(), repository.findByCpf("10000000002").orElseThrow().getId());
    }

    @Test
    void findByCpfIsEmptyForAnUnknownCpf() {
        assertTrue(new NurseJpaRepository().findByCpf("00000000000").isEmpty());
    }

    @Test
    void findAllIsOrderedById() {
        NurseJpaRepository repository = new NurseJpaRepository();
        Nurse first = repository.save(nurse("10000000003"));
        Nurse second = repository.save(nurse("10000000004"));

        assertEquals(
                List.of(first.getId(), second.getId()),
                repository.findAll().stream().map(Nurse::getId).toList());
    }

    @Test
    void deleteOfAMissingNurseIsANoOp() {
        assertDoesNotThrow(() -> new NurseJpaRepository().delete(nurse(999_999L, "10000000005")));
    }

    private static Nurse nurse(String cpf) {
        return nurse(null, cpf);
    }

    private static Nurse nurse(Long id, String cpf) {
        return new Nurse(id, "Nurse Test", cpf, "48999990000", null, MaritalStatus.SINGLE, "Technician", 100);
    }
}
