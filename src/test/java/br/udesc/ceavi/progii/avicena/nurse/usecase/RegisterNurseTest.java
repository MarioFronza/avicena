package br.udesc.ceavi.progii.avicena.nurse.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.InMemoryNurseRepository;
import org.junit.jupiter.api.Test;

class RegisterNurseTest {

    @Test
    void savesTheNurseThroughTheRepository() {
        InMemoryNurseRepository repository = new InMemoryNurseRepository();
        RegisterNurse useCase = new RegisterNurse(repository);
        Nurse nurse =
                new Nurse("Joana Test", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "Technician", 1200);

        Nurse registered = useCase.register(nurse);

        assertEquals(nurse, registered);
        assertTrue(repository.findAll().contains(nurse));
    }
}
