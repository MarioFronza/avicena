package br.udesc.ceavi.progii.avicena.nurse.domain;

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

class NurseTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsABlankName(String name) {
        assertThrows(InvalidNurseDataException.class, () -> nurseWith("name", name));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsABlankCpf(String cpf) {
        assertThrows(InvalidNurseDataException.class, () -> nurseWith("cpf", cpf));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsABlankPhone(String phone) {
        assertThrows(InvalidNurseDataException.class, () -> nurseWith("phone", phone));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsABlankFormation(String formation) {
        assertThrows(InvalidNurseDataException.class, () -> nurseWith("formation", formation));
    }

    @Test
    void rejectsANegativeHoursCompleted() {
        assertThrows(InvalidNurseDataException.class, () -> nurseWithNumber("hoursCompleted", -1));
    }

    @Test
    void acceptsAZeroHoursCompleted() {
        assertDoesNotThrow(() -> nurseWithNumber("hoursCompleted", 0));
    }

    @Test
    void exposesFieldsAfterValidConstruction() {
        Address address = Fixtures.address();

        Nurse nurse = new Nurse(
                "Joana Test", "12345678900", "48999990000", address, MaritalStatus.SINGLE, "Technician", 1200);

        assertEquals("Joana Test", nurse.getName());
        assertEquals("12345678900", nurse.getCpf());
        assertEquals("48999990000", nurse.getPhone());
        assertEquals(address, nurse.getAddress());
        assertEquals(MaritalStatus.SINGLE, nurse.getMaritalStatus());
        assertEquals("Technician", nurse.getFormation());
        assertEquals(1200, nurse.getHoursCompleted());
    }

    private static Nurse nurseWith(String field, String value) {
        Map<String, String> values = new HashMap<>(
                Map.of("name", "Joana Test", "cpf", "12345678900", "phone", "48999990000", "formation", "Technician"));
        values.put(field, value);
        return new Nurse(
                values.get("name"),
                values.get("cpf"),
                values.get("phone"),
                null,
                MaritalStatus.SINGLE,
                values.get("formation"),
                1200);
    }

    private static Nurse nurseWithNumber(String field, int value) {
        return new Nurse("Joana Test", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "Technician", value);
    }
}
