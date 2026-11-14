package br.udesc.ceavi.progii.avicena.patient.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.InvalidPatientDataException;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.usecase.DeletePatient;
import br.udesc.ceavi.progii.avicena.patient.usecase.ListPatients;
import br.udesc.ceavi.progii.avicena.patient.usecase.RegisterPatient;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryPatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientControllerTest {

    private static final Address ADDRESS = Fixtures.address();

    private InMemoryPatientRepository repository;
    private PatientController controller;

    @BeforeEach
    void setUp() {
        repository = new InMemoryPatientRepository();
        controller = new PatientController(
                new RegisterPatient(repository), new DeletePatient(repository), new ListPatients(repository));
    }

    @Test
    void registerSavesThePatientFromTheForm() {
        Patient patient = controller.register(form("Patient Test"));

        assertEquals(patient, repository.findAll().get(0));
        assertEquals("12345678900", patient.getCpf());
        assertEquals(ADDRESS, patient.getAddress());
    }

    @Test
    void registerRejectsABlankName() {
        assertThrows(InvalidPatientDataException.class, () -> controller.register(form("")));
    }

    @Test
    void deleteRemovesThePatient() {
        Patient patient = controller.register(form("Patient Test"));

        controller.delete(patient);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void findByCpfReturnsTheMatchingPatient() {
        Patient patient = controller.register(form("Patient Test"));

        assertEquals(patient, controller.findByCpf("12345678900").orElseThrow());
    }

    @Test
    void findByCpfIsEmptyWhenNoPatientMatches() {
        assertTrue(controller.findByCpf("00000000000").isEmpty());
    }

    private static PatientController.Form form(String name) {
        return new PatientController.Form(name, "12345678900", "48999990000", ADDRESS, MaritalStatus.SINGLE);
    }
}
