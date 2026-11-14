package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.receptionist.domain.InvalidReceptionistDataException;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.DeleteReceptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.FindReceptionistByCpf;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.RegisterReceptionist;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryReceptionistRepository;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ReceptionistControllerTest {

    private static final Address ADDRESS = Fixtures.address();

    private final InMemoryReceptionistRepository repository = new InMemoryReceptionistRepository();
    private final ReceptionistController controller = new ReceptionistController(
            new RegisterReceptionist(repository),
            new DeleteReceptionist(repository),
            new FindReceptionistByCpf(repository));

    @Test
    void registerSavesTheReceptionistFromTheForm() {
        Receptionist receptionist = controller.register(form("Receptionist Test", "12345678900"));

        assertEquals(receptionist, repository.findById(receptionist.getId()).orElseThrow());
        assertEquals("12345678900", receptionist.getCpf());
        assertEquals(ADDRESS, receptionist.getAddress());
    }

    @Test
    void registerRejectsABlankName() {
        assertThrows(InvalidReceptionistDataException.class, () -> controller.register(form("", "12345678900")));
    }

    @Test
    void registerRejectsACpfAlreadyRegistered() {
        controller.register(form("Receptionist Test", "12345678900"));

        assertThrows(
                InvalidReceptionistDataException.class,
                () -> controller.register(form("Other Receptionist", "12345678900")));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "abc", "12,5"})
    void registerRejectsANonNumericWorkHours(String value) {
        assertThrows(
                InvalidReceptionistDataException.class, () -> controller.register(formWithNumber("workHours", value)));
    }

    @Test
    void registerRejectsANegativeWorkHours() {
        assertThrows(
                InvalidReceptionistDataException.class, () -> controller.register(formWithNumber("workHours", "-1")));
    }

    @Test
    void registerAcceptsWorkHoursWithSurroundingWhitespace() {
        assertDoesNotThrow(() -> controller.register(formWithNumber("workHours", " 8 ")));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "abc", "12,5"})
    void registerRejectsANonNumericSalary(String value) {
        assertThrows(
                InvalidReceptionistDataException.class, () -> controller.register(formWithNumber("salary", value)));
    }

    @Test
    void registerRejectsANegativeSalary() {
        assertThrows(InvalidReceptionistDataException.class, () -> controller.register(formWithNumber("salary", "-1")));
    }

    @Test
    void registerAcceptsSalaryWithSurroundingWhitespace() {
        assertDoesNotThrow(() -> controller.register(formWithNumber("salary", " 1500.5 ")));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "abc", "12,5"})
    void registerRejectsANonNumericOvertimeHours(String value) {
        assertThrows(
                InvalidReceptionistDataException.class,
                () -> controller.register(formWithNumber("overtimeHours", value)));
    }

    @Test
    void registerRejectsANegativeOvertimeHours() {
        assertThrows(
                InvalidReceptionistDataException.class,
                () -> controller.register(formWithNumber("overtimeHours", "-1")));
    }

    @Test
    void registerAcceptsOvertimeHoursWithSurroundingWhitespace() {
        assertDoesNotThrow(() -> controller.register(formWithNumber("overtimeHours", " 2 ")));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void registerRejectsABlankLaborCardNumber(String value) {
        assertThrows(
                InvalidReceptionistDataException.class,
                () -> controller.register(formWithNumber("laborCardNumber", value)));
    }

    @Test
    void registerKeepsLeadingZerosInTheLaborCardNumber() {
        Receptionist receptionist = controller.register(formWithNumber("laborCardNumber", "000123"));

        assertEquals("000123", receptionist.getLaborCardNumber());
    }

    @Test
    void registerWithTheIdOfASavedReceptionistUpdatesIt() {
        Receptionist saved = controller.register(form("Receptionist Test", "12345678900"));

        Receptionist updated = controller.register(withId(saved.getId(), form("Renamed", "12345678900")));

        assertEquals(saved.getId(), updated.getId());
        assertEquals("Renamed", repository.findById(saved.getId()).orElseThrow().getName());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void deleteRemovesTheReceptionist() {
        Receptionist receptionist = controller.register(form("Receptionist Test", "12345678900"));

        controller.delete(receptionist);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void findByCpfReturnsTheMatchingReceptionist() {
        Receptionist receptionist = controller.register(form("Receptionist Test", "12345678900"));

        assertEquals(receptionist, controller.findByCpf("12345678900").orElseThrow());
    }

    @Test
    void findByCpfIsEmptyWhenNoReceptionistMatches() {
        assertTrue(controller.findByCpf("00000000000").isEmpty());
    }

    @Test
    void findByCpfIsEmptyForANullCpf() {
        assertTrue(controller.findByCpf(null).isEmpty());
    }

    private static ReceptionistController.Form form(String name, String cpf) {
        return form(name, cpf, Map.of());
    }

    private static ReceptionistController.Form formWithNumber(String field, String value) {
        Map<String, String> numbers = new HashMap<>();
        numbers.put(field, value);
        return form("Receptionist Test", "12345678900", numbers);
    }

    private static ReceptionistController.Form form(String name, String cpf, Map<String, String> overrides) {
        Map<String, String> numbers = new HashMap<>(
                Map.of("workHours", "8", "salary", "1500.5", "overtimeHours", "2", "laborCardNumber", "123"));
        numbers.putAll(overrides);
        return new ReceptionistController.Form(
                name,
                cpf,
                "48999990000",
                ADDRESS,
                MaritalStatus.SINGLE,
                numbers.get("workHours"),
                numbers.get("salary"),
                numbers.get("overtimeHours"),
                numbers.get("laborCardNumber"));
    }

    private static ReceptionistController.Form withId(Long id, ReceptionistController.Form form) {
        return form.withId(id);
    }
}
