package br.udesc.ceavi.progii.avicena.receptionist.usecase;

import static org.junit.jupiter.api.Assertions.assertFalse;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import org.junit.jupiter.api.Test;

class DeleteReceptionistTest {

    @Test
    void deletesTheReceptionistThroughTheRepository() {
        InMemoryReceptionistRepository repository = new InMemoryReceptionistRepository();
        Receptionist receptionist = new Receptionist(
                "Ana Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE, 40, 3000f, 5, 123456);
        repository.save(receptionist);
        DeleteReceptionist useCase = new DeleteReceptionist(repository);

        useCase.delete(receptionist);

        assertFalse(repository.findAll().contains(receptionist));
    }
}
