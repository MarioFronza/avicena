package br.udesc.ceavi.progii.avicena.doctor.domain;

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

class DoctorTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsABlankName(String name) {
        assertThrows(InvalidDoctorDataException.class, () -> doctorWith("name", name));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsABlankCpf(String cpf) {
        assertThrows(InvalidDoctorDataException.class, () -> doctorWith("cpf", cpf));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsABlankPhone(String phone) {
        assertThrows(InvalidDoctorDataException.class, () -> doctorWith("phone", phone));
    }

    @Test
    void acceptsANullCrmAndSpecialty() {
        assertDoesNotThrow(() -> new Doctor("Joao Test", "12345678900", "48999990000", null, null, null, null));
    }

    @Test
    void exposesFieldsAfterValidConstruction() {
        Address address = Fixtures.address();

        Doctor doctor = new Doctor(
                "Joao Test", "12345678900", "48999990000", address, MaritalStatus.SINGLE, "12345", "Cardiology");

        assertEquals("Joao Test", doctor.getName());
        assertEquals("12345678900", doctor.getCpf());
        assertEquals("48999990000", doctor.getPhone());
        assertEquals(address, doctor.getAddress());
        assertEquals(MaritalStatus.SINGLE, doctor.getMaritalStatus());
        assertEquals("12345", doctor.getCrm());
        assertEquals("Cardiology", doctor.getSpecialty());
    }

    private static Doctor doctorWith(String field, String value) {
        Map<String, String> values =
                new HashMap<>(Map.of("name", "Joao Test", "cpf", "12345678900", "phone", "48999990000"));
        values.put(field, value);
        return new Doctor(
                values.get("name"),
                values.get("cpf"),
                values.get("phone"),
                null,
                MaritalStatus.SINGLE,
                "12345",
                "Cardiology");
    }
}
