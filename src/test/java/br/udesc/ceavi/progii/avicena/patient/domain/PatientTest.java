package br.udesc.ceavi.progii.avicena.patient.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PatientTest {

    @Test
    void rejectsBlankName() {
        assertThrows(
                InvalidPatientDataException.class,
                () -> new Patient("", "12345678900", "48999990000", null, MaritalStatus.SINGLE));
    }
}
