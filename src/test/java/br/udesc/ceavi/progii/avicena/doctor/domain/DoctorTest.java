package br.udesc.ceavi.progii.avicena.doctor.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import org.junit.jupiter.api.Test;

class DoctorTest {

    @Test
    void rejectsBlankName() {
        assertThrows(
                InvalidDoctorDataException.class,
                () -> new Doctor("", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "12345", "Cardiology"));
    }

    @Test
    void exposesFieldsAfterValidConstruction() {
        Address address = new Address(100, "Apt 2", "88000000", "Main St", "Downtown", "Florianopolis");

        Doctor doctor = new Doctor(
                "Joao Teste", "12345678900", "48999990000", address, MaritalStatus.SINGLE, "12345", "Cardiology");

        assertEquals("Joao Teste", doctor.getName());
        assertEquals("12345678900", doctor.getCpf());
        assertEquals("48999990000", doctor.getPhone());
        assertEquals(address, doctor.getAddress());
        assertEquals(MaritalStatus.SINGLE, doctor.getMaritalStatus());
        assertEquals("12345", doctor.getCrm());
        assertEquals("Cardiology", doctor.getSpecialty());
    }
}
