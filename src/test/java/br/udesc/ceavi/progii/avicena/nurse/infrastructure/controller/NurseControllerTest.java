package br.udesc.ceavi.progii.avicena.nurse.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.nurse.domain.InvalidNurseDataException;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.DeleteNurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.ListNurses;
import br.udesc.ceavi.progii.avicena.nurse.usecase.RegisterNurse;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryNurseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NurseControllerTest {

    private static final Address ADDRESS = Fixtures.address();

    private InMemoryNurseRepository repository;
    private NurseController controller;

    @BeforeEach
    void setUp() {
        repository = new InMemoryNurseRepository();
        controller = new NurseController(
                new RegisterNurse(repository), new DeleteNurse(repository), new ListNurses(repository));
    }

    @Test
    void registerSavesTheNurseFromTheForm() {
        Nurse nurse = controller.register(form("Nurse Test", "40"));

        assertEquals(nurse, repository.findAll().get(0));
        assertEquals(40, nurse.getHoursCompleted());
        assertEquals(ADDRESS, nurse.getAddress());
    }

    @Test
    void registerRejectsNonNumericHoursCompleted() {
        assertThrows(InvalidNurseDataException.class, () -> controller.register(form("Nurse Test", "abc")));
    }

    @Test
    void deleteRemovesTheNurse() {
        Nurse nurse = controller.register(form("Nurse Test", "40"));

        controller.delete(nurse);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void findByCpfReturnsTheMatchingNurse() {
        Nurse nurse = controller.register(form("Nurse Test", "40"));

        assertEquals(nurse, controller.findByCpf("12345678900").orElseThrow());
    }

    @Test
    void findByCpfIsEmptyWhenNoNurseMatches() {
        assertTrue(controller.findByCpf("00000000000").isEmpty());
    }

    private static NurseController.Form form(String name, String hoursCompleted) {
        return new NurseController.Form(
                name, "12345678900", "48999990000", ADDRESS, MaritalStatus.SINGLE, "Nursing", hoursCompleted);
    }
}
