package br.udesc.ceavi.progii.avicena.patient.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.patient.domain.InvalidPatientDataException;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryPatientRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegisterPatientTest {

    private final InMemoryPatientRepository repository = new InMemoryPatientRepository();
    private final RegisterPatient useCase = new RegisterPatient(repository);

    @Test
    void registerSavesThePatientAndAssignsAnId() {
        Patient registered = useCase.register(Fixtures.patient("12345678900"));

        assertNotNull(registered.getId());
        assertEquals(List.of(registered), repository.findAll());
    }

    @Test
    void registerRejectsACpfAnotherPatientAlreadyUses() {
        useCase.register(Fixtures.patient("12345678900"));

        assertThrows(InvalidPatientDataException.class, () -> useCase.register(Fixtures.patient("12345678900")));
    }

    @Test
    void registerAcceptsTheSamePatientAgain() {
        Patient registered = useCase.register(Fixtures.patient("12345678900"));

        assertDoesNotThrow(() -> useCase.register(registered));
        assertEquals(1, repository.findAll().size());
    }
}
