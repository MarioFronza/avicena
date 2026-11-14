package br.udesc.ceavi.progii.avicena.patient.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.InvalidPatientDataException;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.usecase.DeletePatient;
import br.udesc.ceavi.progii.avicena.patient.usecase.ListPatients;
import br.udesc.ceavi.progii.avicena.patient.usecase.RegisterPatient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientControllerTest {

    private static final Address ADDRESS =
            new Address(100, "Apt 2", "88000000", "Main St", "Downtown", "Florianopolis");

    private InMemoryPatientRepository repo;
    private PatientController ctrl;

    @BeforeEach
    void setUp() {
        repo = new InMemoryPatientRepository();
        ctrl = new PatientController(new RegisterPatient(repo), new DeletePatient(repo), new ListPatients(repo));
    }

    @Test
    void registerSavesThePatientFromTheForm() {
        Patient patient = ctrl.register(form("Patient Test"));

        assertEquals(patient, repo.findAll().get(0));
        assertEquals("12345678900", patient.getCpf());
        assertEquals(ADDRESS, patient.getAddress());
    }

    @Test
    void registerRejectsABlankName() {
        assertThrows(InvalidPatientDataException.class, () -> ctrl.register(form("")));
    }

    @Test
    void deleteRemovesThePatient() {
        Patient patient = ctrl.register(form("Patient Test"));

        ctrl.delete(patient);

        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void findByCpfReturnsTheMatchingPatient() {
        Patient patient = ctrl.register(form("Patient Test"));

        assertEquals(patient, ctrl.findByCpf("12345678900").orElseThrow());
    }

    @Test
    void findByCpfIsEmptyWhenNoPatientMatches() {
        assertTrue(ctrl.findByCpf("00000000000").isEmpty());
    }

    private static PatientController.Form form(String name) {
        return new PatientController.Form(name, "12345678900", "48999990000", ADDRESS, MaritalStatus.SINGLE);
    }
}
