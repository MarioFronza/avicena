package br.udesc.ceavi.progii.avicena.doctor.usecase;

import static org.junit.jupiter.api.Assertions.assertFalse;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import org.junit.jupiter.api.Test;

class DeleteDoctorTest {

    @Test
    void deletesTheDoctorThroughTheRepository() {
        InMemoryDoctorRepository repository = new InMemoryDoctorRepository();
        Doctor doctor = new Doctor(
                "Joao Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "12345", "Cardiology");
        repository.save(doctor);
        DeleteDoctor useCase = new DeleteDoctor(repository);

        useCase.delete(doctor);

        assertFalse(repository.findAll().contains(doctor));
    }
}
