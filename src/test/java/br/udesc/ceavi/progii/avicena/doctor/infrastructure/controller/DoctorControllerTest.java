package br.udesc.ceavi.progii.avicena.doctor.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.InvalidDoctorDataException;
import br.udesc.ceavi.progii.avicena.doctor.usecase.DeleteDoctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.ListDoctors;
import br.udesc.ceavi.progii.avicena.doctor.usecase.RegisterDoctor;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryDoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoctorControllerTest {

    private static final Address ADDRESS = Fixtures.address();

    private InMemoryDoctorRepository repo;
    private DoctorController ctrl;

    @BeforeEach
    void setUp() {
        repo = new InMemoryDoctorRepository();
        ctrl = new DoctorController(new RegisterDoctor(repo), new DeleteDoctor(repo), new ListDoctors(repo));
    }

    @Test
    void registerSavesTheDoctorFromTheForm() {
        Doctor doctor = ctrl.register(form("Dr Test"));

        assertEquals(doctor, repo.findAll().get(0));
        assertEquals("12345678900", doctor.getCpf());
        assertEquals(ADDRESS, doctor.getAddress());
        assertEquals("Cardiology", doctor.getSpecialty());
    }

    @Test
    void registerRejectsABlankName() {
        assertThrows(InvalidDoctorDataException.class, () -> ctrl.register(form("")));
    }

    @Test
    void deleteRemovesTheDoctor() {
        Doctor doctor = ctrl.register(form("Dr Test"));

        ctrl.delete(doctor);

        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void findByCpfReturnsTheMatchingDoctor() {
        Doctor doctor = ctrl.register(form("Dr Test"));

        assertEquals(doctor, ctrl.findByCpf("12345678900").orElseThrow());
    }

    @Test
    void findByCpfIsEmptyWhenNoDoctorMatches() {
        assertTrue(ctrl.findByCpf("00000000000").isEmpty());
    }

    private static DoctorController.Form form(String name) {
        return new DoctorController.Form(
                name, "12345678900", "48999990000", ADDRESS, MaritalStatus.SINGLE, "12345", "Cardiology");
    }
}
