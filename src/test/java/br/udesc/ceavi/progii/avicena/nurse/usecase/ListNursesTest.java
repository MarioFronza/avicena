package br.udesc.ceavi.progii.avicena.nurse.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryNurseRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class ListNursesTest {

    private final InMemoryNurseRepository repository = new InMemoryNurseRepository();
    private final ListNurses useCase = new ListNurses(repository);

    @Test
    void listReturnsNothingForAnEmptyRepository() {
        assertTrue(useCase.list().isEmpty());
    }

    @Test
    void listReturnsEveryNurseInTheOrderTheyWereSaved() {
        Nurse first = repository.save(Fixtures.nurse("11111111111"));
        Nurse second = repository.save(Fixtures.nurse("22222222222"));

        assertEquals(List.of(first, second), useCase.list());
    }
}
