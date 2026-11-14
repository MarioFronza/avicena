package br.udesc.ceavi.progii.avicena.receptionist.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.receptionist.domain.InvalidReceptionistDataException;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryReceptionistRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegisterReceptionistTest {

    private final InMemoryReceptionistRepository repository = new InMemoryReceptionistRepository();
    private final RegisterReceptionist useCase = new RegisterReceptionist(repository);

    @Test
    void registerSavesTheReceptionistAndAssignsAnId() {
        Receptionist registered = useCase.register(Fixtures.receptionist("12345678900"));

        assertNotNull(registered.getId());
        assertEquals(List.of(registered), repository.findAll());
    }

    @Test
    void registerRejectsACpfAnotherReceptionistAlreadyUses() {
        useCase.register(Fixtures.receptionist("12345678900"));

        assertThrows(
                InvalidReceptionistDataException.class, () -> useCase.register(Fixtures.receptionist("12345678900")));
    }

    @Test
    void registerAcceptsTheSameReceptionistAgain() {
        Receptionist registered = useCase.register(Fixtures.receptionist("12345678900"));

        assertDoesNotThrow(() -> useCase.register(registered));
        assertEquals(1, repository.findAll().size());
    }
}
