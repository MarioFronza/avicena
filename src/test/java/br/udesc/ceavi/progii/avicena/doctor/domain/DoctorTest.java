package br.udesc.ceavi.progii.avicena.doctor.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import org.junit.jupiter.api.Test;

class DoctorTest {

    @Test
    void rejectsBlankName() {
        assertThrows(
                InvalidDoctorDataException.class,
                () -> new Doctor(
                        "", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "12345", "Cardiology"));
    }
}
