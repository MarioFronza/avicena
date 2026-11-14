package br.udesc.ceavi.progii.avicena.doctor.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryDoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class FindDoctorByCpfTest {

    private final InMemoryDoctorRepository repository = new InMemoryDoctorRepository();
    private final FindDoctorByCpf useCase = new FindDoctorByCpf(repository);

    @Test
    void findReturnsTheDoctorWithThatCpf() {
        Doctor saved = repository.save(Fixtures.doctor("12345678900"));

        assertEquals(saved, useCase.find("12345678900").orElseThrow());
    }

    @Test
    void findIgnoresSurroundingWhitespace() {
        Doctor saved = repository.save(Fixtures.doctor("12345678900"));

        assertEquals(saved, useCase.find("  12345678900 ").orElseThrow());
    }

    @Test
    void findIsEmptyForAnUnknownCpf() {
        repository.save(Fixtures.doctor("12345678900"));

        assertTrue(useCase.find("00000000000").isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void findIsEmptyForABlankCpf(String cpf) {
        assertTrue(useCase.find(cpf).isEmpty());
    }
}
