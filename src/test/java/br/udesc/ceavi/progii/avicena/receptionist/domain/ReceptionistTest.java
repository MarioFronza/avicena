package br.udesc.ceavi.progii.avicena.receptionist.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import org.junit.jupiter.api.Test;

class ReceptionistTest {

    @Test
    void rejectsBlankName() {
        assertThrows(
                InvalidReceptionistDataException.class,
                () -> new Receptionist(
                        "", "12345678900", "48999990000", null, MaritalStatus.SINGLE, 40, 3000f, 5, 123456));
    }
}
