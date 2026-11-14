package br.udesc.ceavi.progii.avicena.nurse.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.nurse.domain.InvalidNurseDataException;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryNurseRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegisterNurseTest {

    private final InMemoryNurseRepository repository = new InMemoryNurseRepository();
    private final RegisterNurse useCase = new RegisterNurse(repository);

    @Test
    void registerSavesTheNurseAndAssignsAnId() {
        Nurse registered = useCase.register(Fixtures.nurse("12345678900"));

        assertNotNull(registered.getId());
        assertEquals(List.of(registered), repository.findAll());
    }

    @Test
    void registerRejectsACpfAnotherNurseAlreadyUses() {
        useCase.register(Fixtures.nurse("12345678900"));

        assertThrows(InvalidNurseDataException.class, () -> useCase.register(Fixtures.nurse("12345678900")));
    }

    @Test
    void registerAcceptsTheSameNurseAgain() {
        Nurse registered = useCase.register(Fixtures.nurse("12345678900"));

        assertDoesNotThrow(() -> useCase.register(registered));
        assertEquals(1, repository.findAll().size());
    }
}
