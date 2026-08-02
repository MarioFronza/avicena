package br.udesc.ceavi.progii.avicena.patient.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PatientTest {

    @Test
    void rejectsBlankName() {
        assertThrows(
                InvalidPatientDataException.class,
                () -> new Patient("", "12345678900", "48999990000", null, MaritalStatus.SINGLE));
    }

    @Test
    void rejectsBlankCpf() {
        assertThrows(
                InvalidPatientDataException.class,
                () -> new Patient("Maria Teste", "", "48999990000", null, MaritalStatus.SINGLE));
    }

    @Test
    void rejectsBlankPhone() {
        assertThrows(
                InvalidPatientDataException.class,
                () -> new Patient("Maria Teste", "12345678900", "", null, MaritalStatus.SINGLE));
    }

    @Test
    void exposesFieldsAfterValidConstruction() {
        Address address = new Address(100, "Apt 2", "88000000", "Main St", "Downtown", "Florianopolis");

        Patient patient = new Patient("Maria Teste", "12345678900", "48999990000", address, MaritalStatus.SINGLE);

        assertEquals("Maria Teste", patient.getName());
        assertEquals("12345678900", patient.getCpf());
        assertEquals("48999990000", patient.getPhone());
        assertEquals(address, patient.getAddress());
        assertEquals(MaritalStatus.SINGLE, patient.getMaritalStatus());
    }
}
