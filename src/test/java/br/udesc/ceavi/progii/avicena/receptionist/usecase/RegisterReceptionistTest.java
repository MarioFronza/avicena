package br.udesc.ceavi.progii.avicena.receptionist.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import org.junit.jupiter.api.Test;

class RegisterReceptionistTest {

    @Test
    void savesTheReceptionistThroughTheRepository() {
        InMemoryReceptionistRepository repository = new InMemoryReceptionistRepository();
        RegisterReceptionist useCase = new RegisterReceptionist(repository);
        Receptionist receptionist = new Receptionist(
                "Ana Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE, 40, 3000f, 5, 123456);

        Receptionist registered = useCase.register(receptionist);

        assertEquals(receptionist, registered);
        assertTrue(repository.findAll().contains(receptionist));
    }
}
