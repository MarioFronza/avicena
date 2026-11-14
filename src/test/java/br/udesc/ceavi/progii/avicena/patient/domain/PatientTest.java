package br.udesc.ceavi.progii.avicena.patient.domain;

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

class PatientTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsABlankName(String name) {
        assertThrows(InvalidPatientDataException.class, () -> patientWith("name", name));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsABlankCpf(String cpf) {
        assertThrows(InvalidPatientDataException.class, () -> patientWith("cpf", cpf));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejectsABlankPhone(String phone) {
        assertThrows(InvalidPatientDataException.class, () -> patientWith("phone", phone));
    }

    @Test
    void exposesFieldsAfterValidConstruction() {
        Address address = Fixtures.address();

        Patient patient = new Patient("Maria Test", "12345678900", "48999990000", address, MaritalStatus.SINGLE);

        assertEquals("Maria Test", patient.getName());
        assertEquals("12345678900", patient.getCpf());
        assertEquals("48999990000", patient.getPhone());
        assertEquals(address, patient.getAddress());
        assertEquals(MaritalStatus.SINGLE, patient.getMaritalStatus());
    }

    @Test
    void acceptsANullAddressAndMaritalStatus() {
        assertDoesNotThrow(() -> new Patient("Maria Test", "12345678900", "48999990000", null, null));
    }

    private static Patient patientWith(String field, String value) {
        Map<String, String> values =
                new HashMap<>(Map.of("name", "Maria Test", "cpf", "12345678900", "phone", "48999990000"));
        values.put(field, value);
        return new Patient(values.get("name"), values.get("cpf"), values.get("phone"), null, MaritalStatus.SINGLE);
    }
}
