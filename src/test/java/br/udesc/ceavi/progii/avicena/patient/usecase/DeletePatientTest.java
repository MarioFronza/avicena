package br.udesc.ceavi.progii.avicena.patient.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryPatientRepository;
import org.junit.jupiter.api.Test;

class DeletePatientTest {

    private final InMemoryPatientRepository repository = new InMemoryPatientRepository();
    private final DeletePatient useCase = new DeletePatient(repository);

    @Test
    void deleteRemovesThePatient() {
        Patient saved = repository.save(Fixtures.patient("12345678900"));

        useCase.delete(saved);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void deleteOfAnUnsavedPatientIsANoOp() {
        repository.save(Fixtures.patient("12345678900"));

        useCase.delete(Fixtures.patient("98765432100"));

        assertEquals(1, repository.findAll().size());
    }
}
