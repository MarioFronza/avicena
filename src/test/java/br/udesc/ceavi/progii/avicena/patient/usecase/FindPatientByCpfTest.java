package br.udesc.ceavi.progii.avicena.patient.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryPatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class FindPatientByCpfTest {

    private final InMemoryPatientRepository repository = new InMemoryPatientRepository();
    private final FindPatientByCpf useCase = new FindPatientByCpf(repository);

    @Test
    void findReturnsThePatientWithThatCpf() {
        Patient saved = repository.save(Fixtures.patient("12345678900"));

        assertEquals(saved, useCase.find("12345678900").orElseThrow());
    }

    @Test
    void findIgnoresSurroundingWhitespace() {
        Patient saved = repository.save(Fixtures.patient("12345678900"));

        assertEquals(saved, useCase.find("  12345678900 ").orElseThrow());
    }

    @Test
    void findIsEmptyForAnUnknownCpf() {
        repository.save(Fixtures.patient("12345678900"));

        assertTrue(useCase.find("00000000000").isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void findIsEmptyForABlankCpf(String cpf) {
        assertTrue(useCase.find(cpf).isEmpty());
    }
}
