package br.udesc.ceavi.progii.avicena.nurse.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import org.junit.jupiter.api.Test;

class RegisterNurseTest {

    @Test
    void savesTheNurseThroughTheRepository() {
        InMemoryNurseRepository repository = new InMemoryNurseRepository();
        RegisterNurse useCase = new RegisterNurse(repository);
        Nurse nurse =
                new Nurse("Joana Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "Tecnico", 1200);

        Nurse registered = useCase.register(nurse);

        assertEquals(nurse, registered);
        assertTrue(repository.findAll().contains(nurse));
    }
}
