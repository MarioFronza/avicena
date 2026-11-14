package br.udesc.ceavi.progii.avicena.nurse.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.nurse.domain.InvalidNurseDataException;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.DeleteNurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.FindNurseByCpf;
import br.udesc.ceavi.progii.avicena.nurse.usecase.RegisterNurse;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryNurseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class NurseControllerTest {

    private static final Address ADDRESS = Fixtures.address();

    private final InMemoryNurseRepository repository = new InMemoryNurseRepository();
    private final NurseController controller = new NurseController(
            new RegisterNurse(repository), new DeleteNurse(repository), new FindNurseByCpf(repository));

    @Test
    void registerSavesTheNurseFromTheForm() {
        Nurse nurse = controller.register(form("Nurse Test", "12345678900"));

        assertEquals(nurse, repository.findById(nurse.getId()).orElseThrow());
        assertEquals("12345678900", nurse.getCpf());
        assertEquals(ADDRESS, nurse.getAddress());
    }

    @Test
    void registerRejectsABlankName() {
        assertThrows(InvalidNurseDataException.class, () -> controller.register(form("", "12345678900")));
    }

    @Test
    void registerRejectsACpfAlreadyRegistered() {
        controller.register(form("Nurse Test", "12345678900"));

        assertThrows(InvalidNurseDataException.class, () -> controller.register(form("Other Nurse", "12345678900")));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "abc", "12,5"})
    void registerRejectsANonNumericHoursCompleted(String value) {
        assertThrows(
                InvalidNurseDataException.class, () -> controller.register(formWithNumber("hoursCompleted", value)));
    }

    @Test
    void registerRejectsANegativeHoursCompleted() {
        assertThrows(
                InvalidNurseDataException.class, () -> controller.register(formWithNumber("hoursCompleted", "-1")));
    }

    @Test
    void registerAcceptsHoursCompletedWithSurroundingWhitespace() {
        assertDoesNotThrow(() -> controller.register(formWithNumber("hoursCompleted", " 40 ")));
    }

    @Test
    void registerWithTheIdOfASavedNurseUpdatesIt() {
        Nurse saved = controller.register(form("Nurse Test", "12345678900"));

        Nurse updated = controller.register(withId(saved.getId(), form("Renamed", "12345678900")));

        assertEquals(saved.getId(), updated.getId());
        assertEquals("Renamed", repository.findById(saved.getId()).orElseThrow().getName());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void deleteRemovesTheNurse() {
        Nurse nurse = controller.register(form("Nurse Test", "12345678900"));

        controller.delete(nurse);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void findByCpfReturnsTheMatchingNurse() {
        Nurse nurse = controller.register(form("Nurse Test", "12345678900"));

        assertEquals(nurse, controller.findByCpf("12345678900").orElseThrow());
    }

    @Test
    void findByCpfIsEmptyWhenNoNurseMatches() {
        assertTrue(controller.findByCpf("00000000000").isEmpty());
    }

    @Test
    void findByCpfIsEmptyForANullCpf() {
        assertTrue(controller.findByCpf(null).isEmpty());
    }

    private static NurseController.Form form(String name, String cpf) {
        return form(name, cpf, "40");
    }

    private static NurseController.Form form(String name, String cpf, String hoursCompleted) {
        return new NurseController.Form(
                name, cpf, "48999990000", ADDRESS, MaritalStatus.SINGLE, "Technician", hoursCompleted);
    }

    private static NurseController.Form formWithNumber(String field, String value) {
        return form("Nurse Test", "12345678900", value);
    }

    private static NurseController.Form withId(Long id, NurseController.Form form) {
        return form.withId(id);
    }
}
