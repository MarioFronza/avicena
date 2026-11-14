package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.receptionist.domain.InvalidReceptionistDataException;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.DeleteReceptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.ListReceptionists;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.RegisterReceptionist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReceptionistControllerTest {

    private static final Address ADDRESS =
            new Address(100, "Apt 2", "88000000", "Main St", "Downtown", "Florianopolis");

    private InMemoryReceptionistRepository repo;
    private ReceptionistController ctrl;

    @BeforeEach
    void setUp() {
        repo = new InMemoryReceptionistRepository();
        ctrl = new ReceptionistController(
                new RegisterReceptionist(repo), new DeleteReceptionist(repo), new ListReceptionists(repo));
    }

    @Test
    void registerSavesTheReceptionistFromTheForm() {
        Receptionist receptionist = ctrl.register(form("8", "2", "1500.5", "123"));

        assertEquals(receptionist, repo.findAll().get(0));
        assertEquals(1500.5f, receptionist.getSalary());
        assertEquals(ADDRESS, receptionist.getAddress());
    }

    @Test
    void registerRejectsNonNumericWorkHours() {
        assertThrows(InvalidReceptionistDataException.class, () -> ctrl.register(form("abc", "2", "1500.5", "123")));
    }

    @Test
    void registerRejectsNonNumericOvertimeHours() {
        assertThrows(InvalidReceptionistDataException.class, () -> ctrl.register(form("8", "", "1500.5", "123")));
    }

    @Test
    void registerRejectsNonNumericSalary() {
        assertThrows(InvalidReceptionistDataException.class, () -> ctrl.register(form("8", "2", "abc", "123")));
    }

    @Test
    void registerRejectsNonNumericLaborCardNumber() {
        assertThrows(InvalidReceptionistDataException.class, () -> ctrl.register(form("8", "2", "1500.5", "abc")));
    }

    @Test
    void deleteRemovesTheReceptionist() {
        Receptionist receptionist = ctrl.register(form("8", "2", "1500.5", "123"));

        ctrl.delete(receptionist);

        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void findByCpfReturnsTheMatchingReceptionist() {
        Receptionist receptionist = ctrl.register(form("8", "2", "1500.5", "123"));

        assertEquals(receptionist, ctrl.findByCpf("12345678900").orElseThrow());
    }

    @Test
    void findByCpfIsEmptyWhenNoReceptionistMatches() {
        assertTrue(ctrl.findByCpf("00000000000").isEmpty());
    }

    private static ReceptionistController.Form form(
            String workHours, String overtimeHours, String salary, String laborCardNumber) {
        return new ReceptionistController.Form(
                "Receptionist Test",
                "12345678900",
                "48999990000",
                ADDRESS,
                MaritalStatus.SINGLE,
                workHours,
                salary,
                overtimeHours,
                laborCardNumber);
    }
}
