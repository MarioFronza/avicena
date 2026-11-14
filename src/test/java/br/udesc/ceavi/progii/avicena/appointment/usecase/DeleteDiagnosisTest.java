package br.udesc.ceavi.progii.avicena.appointment.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryDiagnosisRepository;
import org.junit.jupiter.api.Test;

class DeleteDiagnosisTest {

    private final InMemoryDiagnosisRepository repository = new InMemoryDiagnosisRepository();
    private final DeleteDiagnosis useCase = new DeleteDiagnosis(repository);

    @Test
    void deleteRemovesTheDiagnosis() {
        Diagnosis saved = repository.save(Fixtures.diagnosis(1L));

        useCase.delete(saved);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void deleteOfAnUnsavedDiagnosisIsANoOp() {
        repository.save(Fixtures.diagnosis(1L));

        useCase.delete(Fixtures.diagnosis(1L));

        assertEquals(1, repository.findAll().size());
    }
}
