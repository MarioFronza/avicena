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
    void registerWithTheIdOfASavedDoctorUpdatesIt() {
        Doctor saved = controller.register(form("Doctor Test", "12345678900"));

        Doctor updated = controller.register(withId(saved.getId(), form("Renamed", "12345678900")));

        assertEquals(saved.getId(), updated.getId());
        assertEquals("Renamed", repository.findById(saved.getId()).orElseThrow().getName());
        assertEquals(1, repository.findAll().size());
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

    private static DoctorController.Form withId(Long id, DoctorController.Form form) {
        return form.withId(id);
    }
}
