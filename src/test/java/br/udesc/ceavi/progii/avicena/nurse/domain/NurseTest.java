package br.udesc.ceavi.progii.avicena.nurse.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import org.junit.jupiter.api.Test;

class NurseTest {

    @Test
    void rejectsBlankName() {
        assertThrows(
                InvalidNurseDataException.class,
                () -> new Nurse("", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "Tecnico", 1200));
    }

    @Test
    void exposesFieldsAfterValidConstruction() {
        Address address = new Address(100, "Apt 2", "88000000", "Main St", "Downtown", "Florianopolis");

        Nurse nurse = new Nurse(
                "Joana Teste", "12345678900", "48999990000", address, MaritalStatus.SINGLE, "Tecnico", 1200);

        assertEquals("Joana Teste", nurse.getName());
        assertEquals("12345678900", nurse.getCpf());
        assertEquals("48999990000", nurse.getPhone());
        assertEquals(address, nurse.getAddress());
        assertEquals(MaritalStatus.SINGLE, nurse.getMaritalStatus());
        assertEquals("Tecnico", nurse.getFormation());
        assertEquals(1200, nurse.getHoursCompleted());
    }
}
