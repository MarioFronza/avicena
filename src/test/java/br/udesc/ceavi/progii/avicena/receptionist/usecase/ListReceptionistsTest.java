package br.udesc.ceavi.progii.avicena.receptionist.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import org.junit.jupiter.api.Test;

class ListReceptionistsTest {

    @Test
    void listsReceptionistsFromTheRepository() {
        InMemoryReceptionistRepository repository = new InMemoryReceptionistRepository();
        Receptionist receptionist = new Receptionist(
                "Ana Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE, 40, 3000f, 5, 123456);
        repository.save(receptionist);
        ListReceptionists useCase = new ListReceptionists(repository);

        var receptionists = useCase.list();

        assertEquals(1, receptionists.size());
        assertTrue(receptionists.contains(receptionist));
    }
}
