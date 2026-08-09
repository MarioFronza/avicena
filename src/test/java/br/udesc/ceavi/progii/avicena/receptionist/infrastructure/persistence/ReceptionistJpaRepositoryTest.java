package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ReceptionistJpaRepositoryTest {

    @Test
    void savePersistsAReceptionistThatIsRetrievableAfterward() {
        Receptionist receptionist = new Receptionist(
                "Ana Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE, 40, 3000f, 5, 123456);
        ReceptionistJpaRepository repository = new ReceptionistJpaRepository();

        Receptionist saved = repository.save(receptionist);

        assertNotNull(saved.getId());
        Optional<Receptionist> found = repository.findAll().stream()
                .filter(r -> r.getCpf().equals("12345678900"))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals("Ana Teste", found.get().getName());
        assertEquals(40, found.get().getWorkHours());
        assertEquals(3000f, found.get().getSalary());
        assertEquals(5, found.get().getOvertimeHours());
        assertEquals(123456, found.get().getLaborCardNumber());
    }

    @Test
    void savePersistsAReceptionistWithAMaritalStatusThatRoundTripsCorrectly() {
        Receptionist receptionist = new Receptionist(
                "Casado Teste", "77777777777", "48966660000", null, MaritalStatus.MARRIED, 40, 3200f, 2, 987654);
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
                "Bruna Teste", "98765432100", "48988880000", null, MaritalStatus.SINGLE, 30, 2500f, 0, 654321);
        ReceptionistJpaRepository repository = new ReceptionistJpaRepository();
        Receptionist saved = repository.save(receptionist);

        repository.delete(saved);

        boolean stillPresent =
                repository.findAll().stream().anyMatch(r -> r.getCpf().equals("98765432100"));
        assertFalse(stillPresent);
    }
}
