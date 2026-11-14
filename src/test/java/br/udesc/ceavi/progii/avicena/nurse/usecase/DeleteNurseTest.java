package br.udesc.ceavi.progii.avicena.nurse.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryNurseRepository;
import org.junit.jupiter.api.Test;

class DeleteNurseTest {

    private final InMemoryNurseRepository repository = new InMemoryNurseRepository();
    private final DeleteNurse useCase = new DeleteNurse(repository);

    @Test
    void deleteRemovesTheNurse() {
        Nurse saved = repository.save(Fixtures.nurse("12345678900"));

        useCase.delete(saved);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void deleteOfAnUnsavedNurseIsANoOp() {
        repository.save(Fixtures.nurse("12345678900"));

        useCase.delete(Fixtures.nurse("98765432100"));

        assertEquals(1, repository.findAll().size());
    }
}
