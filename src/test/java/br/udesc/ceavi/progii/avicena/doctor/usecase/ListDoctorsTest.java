package br.udesc.ceavi.progii.avicena.doctor.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import org.junit.jupiter.api.Test;

class ListDoctorsTest {

    @Test
    void listsDoctorsFromTheRepository() {
        InMemoryDoctorRepository repository = new InMemoryDoctorRepository();
        Doctor doctor = new Doctor(
                "Joao Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "12345", "Cardiology");
        repository.save(doctor);
        ListDoctors useCase = new ListDoctors(repository);

        var doctors = useCase.list();

        assertEquals(1, doctors.size());
        assertTrue(doctors.contains(doctor));
    }
}
