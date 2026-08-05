package br.udesc.ceavi.progii.avicena.nurse.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import org.junit.jupiter.api.Test;

class ListNursesTest {

    @Test
    void listsNursesFromTheRepository() {
        InMemoryNurseRepository repository = new InMemoryNurseRepository();
        Nurse nurse = new Nurse(
                "Joana Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "Tecnico", 1200);
        repository.save(nurse);
        ListNurses useCase = new ListNurses(repository);

        var nurses = useCase.list();

        assertEquals(1, nurses.size());
        assertTrue(nurses.contains(nurse));
    }
}
