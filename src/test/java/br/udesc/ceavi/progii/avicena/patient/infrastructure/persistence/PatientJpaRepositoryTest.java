package br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PatientJpaRepositoryTest {

    @Test
    void savePersistsAPatientWithAFreshlyEnteredAddress() {
        Address address = new Address(100, "Apt 2", "88000000", "Main St", "Downtown", "Florianopolis");
        Patient patient = new Patient("Endereco Teste", "77777777777", "48977770000", address, MaritalStatus.SINGLE);
        PatientJpaRepository repository = new PatientJpaRepository();

        Patient saved = repository.save(patient);

        assertNotNull(saved.getId());
        Optional<Patient> found = repository.findAll().stream()
                .filter(p -> p.getCpf().equals("77777777777"))
                .findFirst();
        assertTrue(found.isPresent());
        assertNotNull(found.get().getAddress());
        assertEquals("Main St", found.get().getAddress().getStreet());
    }

    @Test
    void savePersistsAPatientThatIsRetrievableAfterward() {
        Patient patient = new Patient("Maria Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE);
        PatientJpaRepository repository = new PatientJpaRepository();

        Patient saved = repository.save(patient);

        assertNotNull(saved.getId());
        Optional<Patient> found = repository.findAll().stream()
                .filter(p -> p.getCpf().equals("12345678900"))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals("Maria Teste", found.get().getName());
    }

    @Test
    void deleteRemovesAPreviouslyPersistedPatient() {
        Patient patient = new Patient("Joao Teste", "98765432100", "48988880000", null, MaritalStatus.SINGLE);
        PatientJpaRepository repository = new PatientJpaRepository();
        Patient saved = repository.save(patient);

        repository.delete(saved);

        boolean stillPresent =
                repository.findAll().stream().anyMatch(p -> p.getCpf().equals("98765432100"));
        assertFalse(stillPresent);
    }
}
