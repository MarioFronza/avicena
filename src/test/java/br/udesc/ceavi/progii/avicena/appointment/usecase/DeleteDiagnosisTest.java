package br.udesc.ceavi.progii.avicena.appointment.usecase;

import static org.junit.jupiter.api.Assertions.assertFalse;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import org.junit.jupiter.api.Test;

class DeleteDiagnosisTest {

    @Test
    void deletesTheDiagnosisThroughTheRepository() {
        InMemoryDiagnosisRepository repository = new InMemoryDiagnosisRepository();
        Diagnosis diagnosis =
                new Diagnosis(1L, 120f, 37, 170, 70, "Padrão", "Gripe", "Repouso", "Exame padrão", "Padrão");
        repository.save(diagnosis);
        DeleteDiagnosis useCase = new DeleteDiagnosis(repository);

        useCase.delete(diagnosis);

        assertFalse(repository.findAll().contains(diagnosis));
    }
}
