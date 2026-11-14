package br.udesc.ceavi.progii.avicena.receptionist.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryReceptionistRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class ListReceptionistsTest {

    private final InMemoryReceptionistRepository repository = new InMemoryReceptionistRepository();
    private final ListReceptionists useCase = new ListReceptionists(repository);

    @Test
    void listReturnsNothingForAnEmptyRepository() {
        assertTrue(useCase.list().isEmpty());
    }

    @Test
    void listReturnsEveryReceptionistInTheOrderTheyWereSaved() {
        Receptionist first = repository.save(Fixtures.receptionist("11111111111"));
        Receptionist second = repository.save(Fixtures.receptionist("22222222222"));

        assertEquals(List.of(first, second), useCase.list());
    }
}
