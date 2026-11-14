package br.udesc.ceavi.progii.avicena.nurse.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import org.junit.jupiter.api.Test;

class NurseTest {

    @Test
    void rejectsBlankName() {
        assertThrows(
                InvalidNurseDataException.class,
                () -> new Nurse("", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "Technician", 1200));
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
}
