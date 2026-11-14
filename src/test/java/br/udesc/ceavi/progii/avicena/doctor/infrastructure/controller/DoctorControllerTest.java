package br.udesc.ceavi.progii.avicena.doctor.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.InvalidDoctorDataException;
import br.udesc.ceavi.progii.avicena.doctor.usecase.DeleteDoctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.FindDoctorByCpf;
import br.udesc.ceavi.progii.avicena.doctor.usecase.RegisterDoctor;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryDoctorRepository;
import org.junit.jupiter.api.Test;

class DoctorControllerTest {

    private static final Address ADDRESS = Fixtures.address();

    private final InMemoryDoctorRepository repository = new InMemoryDoctorRepository();
    private final DoctorController controller = new DoctorController(
            new RegisterDoctor(repository), new DeleteDoctor(repository), new FindDoctorByCpf(repository));

    @Test
    void registerSavesTheDoctorFromTheForm() {
        Doctor doctor = controller.register(form("Doctor Test", "12345678900"));

        assertEquals(doctor, repository.findById(doctor.getId()).orElseThrow());
        assertEquals("12345678900", doctor.getCpf());
        assertEquals(ADDRESS, doctor.getAddress());
    }

    @Test
    void registerRejectsABlankName() {
        assertThrows(InvalidDoctorDataException.class, () -> controller.register(form("", "12345678900")));
    }

    @Test
    void registerRejectsACpfAlreadyRegistered() {
        controller.register(form("Doctor Test", "12345678900"));

        assertThrows(InvalidDoctorDataException.class, () -> controller.register(form("Other Doctor", "12345678900")));
    }

    @Test
    void deleteRemovesTheDoctor() {
        Doctor doctor = controller.register(form("Doctor Test", "12345678900"));

        controller.delete(doctor);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void findByCpfReturnsTheMatchingDoctor() {
        Doctor doctor = controller.register(form("Doctor Test", "12345678900"));

        assertEquals(doctor, controller.findByCpf("12345678900").orElseThrow());
    }

    @Test
    void findByCpfIsEmptyWhenNoDoctorMatches() {
        assertTrue(controller.findByCpf("00000000000").isEmpty());
    }

    @Test
    void findByCpfIsEmptyForANullCpf() {
        assertTrue(controller.findByCpf(null).isEmpty());
    }

    private static DoctorController.Form form(String name, String cpf) {
        return new DoctorController.Form(
                name, cpf, "48999990000", ADDRESS, MaritalStatus.SINGLE, "12345", "Cardiology");
    }
}
