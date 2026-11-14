package br.udesc.ceavi.progii.avicena.doctor.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryDoctorRepository;
import org.junit.jupiter.api.Test;

class DeleteDoctorTest {

    private final InMemoryDoctorRepository repository = new InMemoryDoctorRepository();
    private final DeleteDoctor useCase = new DeleteDoctor(repository);

    @Test
    void deleteRemovesTheDoctor() {
        Doctor saved = repository.save(Fixtures.doctor("12345678900"));

        useCase.delete(saved);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void deleteOfAnUnsavedDoctorIsANoOp() {
        repository.save(Fixtures.doctor("12345678900"));

        useCase.delete(Fixtures.doctor("98765432100"));

        assertEquals(1, repository.findAll().size());
    }
}
