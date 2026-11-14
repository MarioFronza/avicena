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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NurseControllerTest {

    private static final Address ADDRESS =
            new Address(100, "Apt 2", "88000000", "Main St", "Downtown", "Florianopolis");

    private InMemoryNurseRepository repo;
    private NurseController ctrl;

    @BeforeEach
    void setUp() {
        repo = new InMemoryNurseRepository();
        ctrl = new NurseController(new RegisterNurse(repo), new DeleteNurse(repo), new ListNurses(repo));
    }

    @Test
    void registerSavesTheNurseFromTheForm() {
        Nurse nurse = ctrl.register(form("Nurse Test", "40"));

        assertEquals(nurse, repo.findAll().get(0));
        assertEquals(40, nurse.getHoursCompleted());
        assertEquals(ADDRESS, nurse.getAddress());
    }

    @Test
    void registerRejectsNonNumericHoursCompleted() {
        assertThrows(InvalidNurseDataException.class, () -> ctrl.register(form("Nurse Test", "abc")));
    }

    @Test
    void deleteRemovesTheNurse() {
        Nurse nurse = ctrl.register(form("Nurse Test", "40"));

        ctrl.delete(nurse);

        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void findByCpfReturnsTheMatchingNurse() {
        Nurse nurse = ctrl.register(form("Nurse Test", "40"));

        assertEquals(nurse, ctrl.findByCpf("12345678900").orElseThrow());
    }

    @Test
    void findByCpfIsEmptyWhenNoNurseMatches() {
        assertTrue(ctrl.findByCpf("00000000000").isEmpty());
    }

    private static NurseController.Form form(String name, String hoursCompleted) {
        return new NurseController.Form(
                name, "12345678900", "48999990000", ADDRESS, MaritalStatus.SINGLE, "Nursing", hoursCompleted);
    }
}
