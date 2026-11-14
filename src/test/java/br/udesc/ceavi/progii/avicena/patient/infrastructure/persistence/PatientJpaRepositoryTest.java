package br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PatientJpaRepositoryTest {

    @Test
    void savePersistsAPatientWithAFreshlyEnteredAddress() {
        Address address = Fixtures.address();
        Patient patient = new Patient("Address Test", "77777777777", "48977770000", address, MaritalStatus.SINGLE);
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
        Patient patient = new Patient("Maria Test", "12345678900", "48999990000", null, MaritalStatus.SINGLE);
        PatientJpaRepository repository = new PatientJpaRepository();

        Patient saved = repository.save(patient);

        assertNotNull(saved.getId());
        Optional<Patient> found = repository.findAll().stream()
                .filter(p -> p.getCpf().equals("12345678900"))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals("Maria Test", found.get().getName());
    }

    @Test
    void savePersistsAPatientWithAMaritalStatusThatRoundTripsCorrectly() {
        Patient patient = new Patient("Married Test", "33333333333", "48966660000", null, MaritalStatus.MARRIED);
        PatientJpaRepository repository = new PatientJpaRepository();

        repository.save(patient);

        Optional<Patient> found = repository.findAll().stream()
                .filter(p -> p.getCpf().equals("33333333333"))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals(MaritalStatus.MARRIED, found.get().getMaritalStatus());
    }

    @Test
    void deleteRemovesAPreviouslyPersistedPatient() {
        Patient patient = new Patient("Joao Test", "98765432100", "48988880000", null, MaritalStatus.SINGLE);
        PatientJpaRepository repository = new PatientJpaRepository();
        Patient saved = repository.save(patient);

        repository.delete(saved);

        boolean stillPresent =
                repository.findAll().stream().anyMatch(p -> p.getCpf().equals("98765432100"));
        assertFalse(stillPresent);
    }

    @Test
    void findByIdReturnsTheSavedPatient() {
        PatientJpaRepository repository = new PatientJpaRepository();
        Patient saved = repository.save(patient("10000000001"));

        assertEquals(
                "10000000001", repository.findById(saved.getId()).orElseThrow().getCpf());
    }

    @Test
    void findByIdIsEmptyForAnUnknownId() {
        assertTrue(new PatientJpaRepository().findById(999_999L).isEmpty());
    }

    @Test
    void findByCpfReturnsTheSavedPatient() {
        PatientJpaRepository repository = new PatientJpaRepository();
        Patient saved = repository.save(patient("10000000002"));

        assertEquals(
                saved.getId(), repository.findByCpf("10000000002").orElseThrow().getId());
    }

    @Test
    void findByCpfIsEmptyForAnUnknownCpf() {
        assertTrue(new PatientJpaRepository().findByCpf("00000000000").isEmpty());
    }

    @Test
    void findAllIsOrderedById() {
        PatientJpaRepository repository = new PatientJpaRepository();
        Patient first = repository.save(patient("10000000003"));
        Patient second = repository.save(patient("10000000004"));

        assertEquals(
                List.of(first.getId(), second.getId()),
                repository.findAll().stream().map(Patient::getId).toList());
    }

    @Test
    void deleteOfAMissingPatientIsANoOp() {
        assertDoesNotThrow(() -> new PatientJpaRepository().delete(patient(999_999L, "10000000005")));
    }

    private static Patient patient(String cpf) {
        return patient(null, cpf);
    }

    private static Patient patient(Long id, String cpf) {
        return new Patient(id, "Patient Test", cpf, "48999990000", null, MaritalStatus.SINGLE);
    }
}
