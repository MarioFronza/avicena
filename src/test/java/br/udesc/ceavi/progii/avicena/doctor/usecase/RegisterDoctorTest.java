package br.udesc.ceavi.progii.avicena.doctor.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.InvalidDoctorDataException;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryDoctorRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegisterDoctorTest {

    private final InMemoryDoctorRepository repository = new InMemoryDoctorRepository();
    private final RegisterDoctor useCase = new RegisterDoctor(repository);

    @Test
    void registerSavesTheDoctorAndAssignsAnId() {
        Doctor registered = useCase.register(Fixtures.doctor("12345678900"));

        assertNotNull(registered.getId());
        assertEquals(List.of(registered), repository.findAll());
    }

    @Test
    void registerRejectsACpfAnotherDoctorAlreadyUses() {
        useCase.register(Fixtures.doctor("12345678900"));

        assertThrows(InvalidDoctorDataException.class, () -> useCase.register(Fixtures.doctor("12345678900")));
    }

    @Test
    void registerAcceptsTheSameDoctorAgain() {
        Doctor registered = useCase.register(Fixtures.doctor("12345678900"));

        assertDoesNotThrow(() -> useCase.register(registered));
        assertEquals(1, repository.findAll().size());
    }
}
