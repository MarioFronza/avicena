package br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import org.junit.jupiter.api.Test;

class DoctorCrudControllerTest {

    @Test
    void toDoctorBuildsADoctorFromValidFormValues() {
        Address address = new Address(100, "Apt 2", "88000000", "Main St", "Downtown", "Florianopolis");

        Doctor doctor = DoctorCrudController.toDoctor(
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
