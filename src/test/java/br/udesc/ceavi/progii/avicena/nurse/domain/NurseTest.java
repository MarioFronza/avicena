package br.udesc.ceavi.progii.avicena.nurse.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import org.junit.jupiter.api.Test;

class NurseTest {

    @Test
    void rejectsBlankName() {
        assertThrows(
                InvalidNurseDataException.class,
                () -> new Nurse("", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "Tecnico", 1200));
    }
}
