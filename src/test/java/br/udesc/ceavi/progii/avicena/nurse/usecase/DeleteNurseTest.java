package br.udesc.ceavi.progii.avicena.nurse.usecase;

import static org.junit.jupiter.api.Assertions.assertFalse;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import org.junit.jupiter.api.Test;

class DeleteNurseTest {

    @Test
    void deletesTheNurseThroughTheRepository() {
        InMemoryNurseRepository repository = new InMemoryNurseRepository();
        Nurse nurse =
                new Nurse("Joana Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "Tecnico", 1200);
        repository.save(nurse);
        DeleteNurse useCase = new DeleteNurse(repository);

        useCase.delete(nurse);

        assertFalse(repository.findAll().contains(nurse));
    }
}
