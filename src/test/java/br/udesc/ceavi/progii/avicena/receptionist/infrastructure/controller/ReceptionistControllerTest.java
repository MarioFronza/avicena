package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.receptionist.domain.InvalidReceptionistDataException;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.DeleteReceptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.ListReceptionists;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.RegisterReceptionist;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryReceptionistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReceptionistControllerTest {

    private static final Address ADDRESS = Fixtures.address();

    private InMemoryReceptionistRepository repository;
    private ReceptionistController controller;

    @BeforeEach
    void setUp() {
        repository = new InMemoryReceptionistRepository();
        controller = new ReceptionistController(
                new RegisterReceptionist(repository),
                new DeleteReceptionist(repository),
                new ListReceptionists(repository));
    }

    @Test
    void registerSavesTheReceptionistFromTheForm() {
        Receptionist receptionist = controller.register(form("8", "2", "1500.5", "123"));

        assertEquals(receptionist, repository.findAll().get(0));
        assertEquals(1500.5f, receptionist.getSalary());
        assertEquals(ADDRESS, receptionist.getAddress());
    }

    @Test
    void registerRejectsNonNumericWorkHours() {
        assertThrows(
                InvalidReceptionistDataException.class, () -> controller.register(form("abc", "2", "1500.5", "123")));
    }

    @Test
    void registerRejectsNonNumericOvertimeHours() {
        assertThrows(InvalidReceptionistDataException.class, () -> controller.register(form("8", "", "1500.5", "123")));
    }

    @Test
    void registerRejectsNonNumericSalary() {
        assertThrows(InvalidReceptionistDataException.class, () -> controller.register(form("8", "2", "abc", "123")));
    }

    @Test
    void registerRejectsNonNumericLaborCardNumber() {
        assertThrows(
                InvalidReceptionistDataException.class, () -> controller.register(form("8", "2", "1500.5", "abc")));
    }

    @Test
    void deleteRemovesTheReceptionist() {
        Receptionist receptionist = controller.register(form("8", "2", "1500.5", "123"));

        controller.delete(receptionist);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void findByCpfReturnsTheMatchingReceptionist() {
        Receptionist receptionist = controller.register(form("8", "2", "1500.5", "123"));

        assertEquals(receptionist, controller.findByCpf("12345678900").orElseThrow());
    }

    @Test
    void findByCpfIsEmptyWhenNoReceptionistMatches() {
        assertTrue(controller.findByCpf("00000000000").isEmpty());
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
