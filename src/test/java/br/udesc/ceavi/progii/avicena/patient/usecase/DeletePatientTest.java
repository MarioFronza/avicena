package br.udesc.ceavi.progii.avicena.patient.usecase;

import static org.junit.jupiter.api.Assertions.assertFalse;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import org.junit.jupiter.api.Test;

class DeletePatientTest {

    @Test
    void removesThePatientFromTheRepository() {
        InMemoryPatientRepository repository = new InMemoryPatientRepository();
        Patient patient = new Patient("Maria Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE);
        repository.save(patient);
        DeletePatient useCase = new DeletePatient(repository);

        useCase.delete(patient);

        assertFalse(repository.findAll().contains(patient));
    }
}
