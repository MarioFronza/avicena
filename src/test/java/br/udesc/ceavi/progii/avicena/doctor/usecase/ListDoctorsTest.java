package br.udesc.ceavi.progii.avicena.doctor.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryDoctorRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class ListDoctorsTest {

    private final InMemoryDoctorRepository repository = new InMemoryDoctorRepository();
    private final ListDoctors useCase = new ListDoctors(repository);

    @Test
    void listReturnsNothingForAnEmptyRepository() {
        assertTrue(useCase.list().isEmpty());
    }

    @Test
    void listReturnsEveryDoctorInTheOrderTheyWereSaved() {
        Doctor first = repository.save(Fixtures.doctor("11111111111"));
        Doctor second = repository.save(Fixtures.doctor("22222222222"));

        assertEquals(List.of(first, second), useCase.list());
    }
}
