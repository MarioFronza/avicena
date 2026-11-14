package br.udesc.ceavi.progii.avicena.receptionist.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryReceptionistRepository;
import org.junit.jupiter.api.Test;

class DeleteReceptionistTest {

    private final InMemoryReceptionistRepository repository = new InMemoryReceptionistRepository();
    private final DeleteReceptionist useCase = new DeleteReceptionist(repository);

    @Test
    void deleteRemovesTheReceptionist() {
        Receptionist saved = repository.save(Fixtures.receptionist("12345678900"));

        useCase.delete(saved);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void deleteOfAnUnsavedReceptionistIsANoOp() {
        repository.save(Fixtures.receptionist("12345678900"));

        useCase.delete(Fixtures.receptionist("98765432100"));

        assertEquals(1, repository.findAll().size());
    }
}
