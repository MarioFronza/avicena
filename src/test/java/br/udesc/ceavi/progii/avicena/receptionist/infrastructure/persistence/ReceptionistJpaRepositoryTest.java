package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ReceptionistJpaRepositoryTest {

    @Test
    void savePersistsAReceptionistThatIsRetrievableAfterward() {
        Receptionist receptionist = new Receptionist(
                "Ana Test", "12345678900", "48999990000", null, MaritalStatus.SINGLE, 40, 3000f, 5, 123456);
        ReceptionistJpaRepository repository = new ReceptionistJpaRepository();

        Receptionist saved = repository.save(receptionist);

        assertNotNull(saved.getId());
        Optional<Receptionist> found = repository.findAll().stream()
                .filter(r -> r.getCpf().equals("12345678900"))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals("Ana Test", found.get().getName());
        assertEquals(40, found.get().getWorkHours());
        assertEquals(3000f, found.get().getSalary());
        assertEquals(5, found.get().getOvertimeHours());
        assertEquals(123456, found.get().getLaborCardNumber());
    }

    @Test
    void savePersistsAReceptionistWithAMaritalStatusThatRoundTripsCorrectly() {
        Receptionist receptionist = new Receptionist(
                "Married Test", "77777777777", "48966660000", null, MaritalStatus.MARRIED, 40, 3200f, 2, 987654);
        ReceptionistJpaRepository repository = new ReceptionistJpaRepository();

        repository.save(receptionist);

        Optional<Receptionist> found = repository.findAll().stream()
                .filter(r -> r.getCpf().equals("77777777777"))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals(MaritalStatus.MARRIED, found.get().getMaritalStatus());
    }

    @Test
    void deleteRemovesAPreviouslyPersistedReceptionist() {
        Receptionist receptionist = new Receptionist(
                "Bruna Test", "98765432100", "48988880000", null, MaritalStatus.SINGLE, 30, 2500f, 0, 654321);
        ReceptionistJpaRepository repository = new ReceptionistJpaRepository();
        Receptionist saved = repository.save(receptionist);

        repository.delete(saved);

        boolean stillPresent =
                repository.findAll().stream().anyMatch(r -> r.getCpf().equals("98765432100"));
        assertFalse(stillPresent);
    }

    @Test
    void findByIdReturnsTheSavedReceptionist() {
        ReceptionistJpaRepository repository = new ReceptionistJpaRepository();
        Receptionist saved = repository.save(receptionist("10000000001"));

        assertEquals(
                "10000000001", repository.findById(saved.getId()).orElseThrow().getCpf());
    }

    @Test
    void findByIdIsEmptyForAnUnknownId() {
        assertTrue(new ReceptionistJpaRepository().findById(999_999L).isEmpty());
    }

    @Test
    void findByCpfReturnsTheSavedReceptionist() {
        ReceptionistJpaRepository repository = new ReceptionistJpaRepository();
        Receptionist saved = repository.save(receptionist("10000000002"));

        assertEquals(
                saved.getId(), repository.findByCpf("10000000002").orElseThrow().getId());
    }

    @Test
    void findByCpfIsEmptyForAnUnknownCpf() {
        assertTrue(new ReceptionistJpaRepository().findByCpf("00000000000").isEmpty());
    }

    @Test
    void findAllIsOrderedById() {
        ReceptionistJpaRepository repository = new ReceptionistJpaRepository();
        Receptionist first = repository.save(receptionist("10000000003"));
        Receptionist second = repository.save(receptionist("10000000004"));

        assertEquals(
                List.of(first.getId(), second.getId()),
                repository.findAll().stream().map(Receptionist::getId).toList());
    }

    @Test
    void deleteOfAMissingReceptionistIsANoOp() {
        assertDoesNotThrow(() -> new ReceptionistJpaRepository().delete(receptionist(999_999L, "10000000005")));
    }

    private static Receptionist receptionist(String cpf) {
        return receptionist(null, cpf);
    }

    private static Receptionist receptionist(Long id, String cpf) {
        return new Receptionist(
                id, "Receptionist Test", cpf, "48999990000", null, MaritalStatus.SINGLE, 40, 3000f, 5, 123);
    }
}
