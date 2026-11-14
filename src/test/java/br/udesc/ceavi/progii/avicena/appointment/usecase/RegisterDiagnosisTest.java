package br.udesc.ceavi.progii.avicena.appointment.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryDiagnosisRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegisterDiagnosisTest {

    private final InMemoryDiagnosisRepository repository = new InMemoryDiagnosisRepository();

    @Test
    void registerSavesTheDiagnosisAndAssignsAnId() {
        Diagnosis registered = new RegisterDiagnosis(repository).register(Fixtures.diagnosis(1L));

        assertNotNull(registered.getId());
        assertEquals(List.of(registered), repository.findAll());
    }
}
