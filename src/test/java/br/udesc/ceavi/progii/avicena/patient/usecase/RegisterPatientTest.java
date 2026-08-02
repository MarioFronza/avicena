package br.udesc.ceavi.progii.avicena.patient.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import org.junit.jupiter.api.Test;

class RegisterPatientTest {

    @Test
    void savesThePatientThroughTheRepository() {
        InMemoryPatientRepository repository = new InMemoryPatientRepository();
        RegisterPatient useCase = new RegisterPatient(repository);
        Patient patient = new Patient("Maria Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE);

        Patient registered = useCase.register(patient);

        assertEquals(patient, registered);
        assertTrue(repository.findAll().contains(patient));
    }
}
