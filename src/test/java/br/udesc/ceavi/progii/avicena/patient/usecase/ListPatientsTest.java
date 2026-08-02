package br.udesc.ceavi.progii.avicena.patient.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import java.util.List;
import org.junit.jupiter.api.Test;

class ListPatientsTest {

    @Test
    void returnsAllPatientsFromTheRepository() {
        InMemoryPatientRepository repository = new InMemoryPatientRepository();
        Patient patient = new Patient("Maria Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE);
        repository.save(patient);
        ListPatients useCase = new ListPatients(repository);

        List<Patient> patients = useCase.list();

        assertEquals(List.of(patient), patients);
    }
}
