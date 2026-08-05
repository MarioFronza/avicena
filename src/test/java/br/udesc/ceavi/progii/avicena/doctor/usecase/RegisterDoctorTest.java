package br.udesc.ceavi.progii.avicena.doctor.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import org.junit.jupiter.api.Test;

class RegisterDoctorTest {

    @Test
    void savesTheDoctorThroughTheRepository() {
        InMemoryDoctorRepository repository = new InMemoryDoctorRepository();
        RegisterDoctor useCase = new RegisterDoctor(repository);
        Doctor doctor = new Doctor(
                "Joao Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "12345", "Cardiology");

        Doctor registered = useCase.register(doctor);

        assertEquals(doctor, registered);
        assertTrue(repository.findAll().contains(doctor));
    }
}
