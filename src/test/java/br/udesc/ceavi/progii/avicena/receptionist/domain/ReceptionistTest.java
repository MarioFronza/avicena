package br.udesc.ceavi.progii.avicena.receptionist.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ReceptionistTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsABlankName(String name) {
        assertThrows(InvalidReceptionistDataException.class, () -> receptionistWith("name", name));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsABlankCpf(String cpf) {
        assertThrows(InvalidReceptionistDataException.class, () -> receptionistWith("cpf", cpf));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsABlankPhone(String phone) {
        assertThrows(InvalidReceptionistDataException.class, () -> receptionistWith("phone", phone));
    }

    @Test
    void rejectsANegativeWorkHours() {
        assertThrows(InvalidReceptionistDataException.class, () -> receptionistWithNumber("workHours", -1));
    }

    @Test
    void acceptsAZeroWorkHours() {
        assertDoesNotThrow(() -> receptionistWithNumber("workHours", 0));
    }

    @Test
    void rejectsANegativeSalary() {
        assertThrows(InvalidReceptionistDataException.class, () -> receptionistWithNumber("salary", -1));
    }

    @Test
    void acceptsAZeroSalary() {
        assertDoesNotThrow(() -> receptionistWithNumber("salary", 0));
    }

    @Test
    void rejectsANegativeOvertimeHours() {
        assertThrows(InvalidReceptionistDataException.class, () -> receptionistWithNumber("overtimeHours", -1));
    }

    @Test
    void acceptsAZeroOvertimeHours() {
        assertDoesNotThrow(() -> receptionistWithNumber("overtimeHours", 0));
    }

    @Test
    void rejectsANegativeLaborCardNumber() {
        assertThrows(InvalidReceptionistDataException.class, () -> receptionistWithNumber("laborCardNumber", -1));
    }

    @Test
    void acceptsAZeroLaborCardNumber() {
        assertDoesNotThrow(() -> receptionistWithNumber("laborCardNumber", 0));
    }

    @Test
    void exposesFieldsAfterValidConstruction() {
        Address address = Fixtures.address();

        Receptionist receptionist = new Receptionist(
                "Ana Test", "12345678900", "48999990000", address, MaritalStatus.SINGLE, 40, 3000f, 5, 123456);

        assertEquals("Ana Test", receptionist.getName());
        assertEquals("12345678900", receptionist.getCpf());
        assertEquals("48999990000", receptionist.getPhone());
        assertEquals(address, receptionist.getAddress());
        assertEquals(MaritalStatus.SINGLE, receptionist.getMaritalStatus());
        assertEquals(40, receptionist.getWorkHours());
        assertEquals(3000f, receptionist.getSalary());
        assertEquals(5, receptionist.getOvertimeHours());
        assertEquals(123456, receptionist.getLaborCardNumber());
    }

    private static Receptionist receptionistWith(String field, String value) {
        Map<String, String> values =
                new HashMap<>(Map.of("name", "Ana Test", "cpf", "12345678900", "phone", "48999990000"));
        values.put(field, value);
        return new Receptionist(
                values.get("name"),
                values.get("cpf"),
                values.get("phone"),
                null,
                MaritalStatus.SINGLE,
                40,
                3000f,
                5,
                123456);
    }

    private static Receptionist receptionistWithNumber(String field, int value) {
        Map<String, Integer> values =
                new HashMap<>(Map.of("workHours", 40, "salary", 3000, "overtimeHours", 5, "laborCardNumber", 123456));
        values.put(field, value);
        return new Receptionist(
                "Ana Test",
                "12345678900",
                "48999990000",
                null,
                MaritalStatus.SINGLE,
                values.get("workHours"),
                values.get("salary"),
                values.get("overtimeHours"),
                values.get("laborCardNumber"));
    }
}
