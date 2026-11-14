package br.udesc.ceavi.progii.avicena.patient.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.InvalidPatientDataException;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.usecase.DeletePatient;
import br.udesc.ceavi.progii.avicena.patient.usecase.FindPatientByCpf;
import br.udesc.ceavi.progii.avicena.patient.usecase.RegisterPatient;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryPatientRepository;
import org.junit.jupiter.api.Test;

class PatientControllerTest {

    private static final Address ADDRESS = Fixtures.address();

    private final InMemoryPatientRepository repository = new InMemoryPatientRepository();
    private final PatientController controller = new PatientController(
            new RegisterPatient(repository), new DeletePatient(repository), new FindPatientByCpf(repository));

    @Test
    void registerSavesThePatientFromTheForm() {
        Patient patient = controller.register(form("Patient Test", "12345678900"));

        assertEquals(patient, repository.findById(patient.getId()).orElseThrow());
        assertEquals("12345678900", patient.getCpf());
        assertEquals(ADDRESS, patient.getAddress());
    }

    @Test
    void registerRejectsABlankName() {
        assertThrows(InvalidPatientDataException.class, () -> controller.register(form("", "12345678900")));
    }

    @Test
    void registerRejectsACpfAlreadyRegistered() {
        controller.register(form("Patient Test", "12345678900"));

        assertThrows(
                InvalidPatientDataException.class, () -> controller.register(form("Other Patient", "12345678900")));
    }

    @Test
    void registerWithTheIdOfASavedPatientUpdatesIt() {
        Patient saved = controller.register(form("Patient Test", "12345678900"));

        Patient updated = controller.register(withId(saved.getId(), form("Renamed", "12345678900")));

        assertEquals(saved.getId(), updated.getId());
        assertEquals("Renamed", repository.findById(saved.getId()).orElseThrow().getName());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void deleteRemovesThePatient() {
        Patient patient = controller.register(form("Patient Test", "12345678900"));

        controller.delete(patient);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void findByCpfReturnsTheMatchingPatient() {
        Patient patient = controller.register(form("Patient Test", "12345678900"));

        assertEquals(patient, controller.findByCpf("12345678900").orElseThrow());
    }

    @Test
    void findByCpfIsEmptyWhenNoPatientMatches() {
        assertTrue(controller.findByCpf("00000000000").isEmpty());
    }

    @Test
    void findByCpfIsEmptyForANullCpf() {
        assertTrue(controller.findByCpf(null).isEmpty());
    }

    private static PatientController.Form form(String name, String cpf) {
        return new PatientController.Form(name, cpf, "48999990000", ADDRESS, MaritalStatus.SINGLE);
    }

    private static PatientController.Form withId(Long id, PatientController.Form form) {
        return form.withId(id);
    }
}
