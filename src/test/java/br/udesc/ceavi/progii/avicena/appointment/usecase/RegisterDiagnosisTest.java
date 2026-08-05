package br.udesc.ceavi.progii.avicena.appointment.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import org.junit.jupiter.api.Test;

class RegisterDiagnosisTest {

    @Test
    void savesTheDiagnosisThroughTheRepository() {
        InMemoryDiagnosisRepository repository = new InMemoryDiagnosisRepository();
        RegisterDiagnosis useCase = new RegisterDiagnosis(repository);
        Diagnosis diagnosis =
                new Diagnosis(1L, 120f, 37, 170, 70, "Padrão", "Gripe", "Repouso", "Exame padrão", "Padrão");

        Diagnosis registered = useCase.register(diagnosis);

        assertEquals(diagnosis, registered);
        assertTrue(repository.findAll().contains(diagnosis));
    }
}
