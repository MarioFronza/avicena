package br.udesc.ceavi.progii.avicena.patient.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryPatientRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class ListPatientsTest {

    private final InMemoryPatientRepository repository = new InMemoryPatientRepository();
    private final ListPatients useCase = new ListPatients(repository);

    @Test
    void listReturnsNothingForAnEmptyRepository() {
        assertTrue(useCase.list().isEmpty());
    }

    @Test
    void listReturnsEveryPatientInTheOrderTheyWereSaved() {
        Patient first = repository.save(Fixtures.patient("11111111111"));
        Patient second = repository.save(Fixtures.patient("22222222222"));

        assertEquals(List.of(first, second), useCase.list());
    }
}
