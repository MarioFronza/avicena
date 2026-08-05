package br.udesc.ceavi.progii.avicena.receptionist.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
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

    @Test
    void exposesFieldsAfterValidConstruction() {
        Address address = new Address(100, "Apt 2", "88000000", "Main St", "Downtown", "Florianopolis");

        Receptionist receptionist = new Receptionist(
                "Ana Teste", "12345678900", "48999990000", address, MaritalStatus.SINGLE, 40, 3000f, 5, 123456);

        assertEquals("Ana Teste", receptionist.getName());
        assertEquals("12345678900", receptionist.getCpf());
        assertEquals("48999990000", receptionist.getPhone());
        assertEquals(address, receptionist.getAddress());
        assertEquals(MaritalStatus.SINGLE, receptionist.getMaritalStatus());
        assertEquals(40, receptionist.getWorkHours());
        assertEquals(3000f, receptionist.getSalary());
        assertEquals(5, receptionist.getOvertimeHours());
        assertEquals(123456, receptionist.getLaborCardNumber());
    }
}
