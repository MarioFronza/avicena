package br.udesc.ceavi.progii.avicena.nurse.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryNurseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class FindNurseByCpfTest {

    private final InMemoryNurseRepository repository = new InMemoryNurseRepository();
    private final FindNurseByCpf useCase = new FindNurseByCpf(repository);

    @Test
    void findReturnsTheNurseWithThatCpf() {
        Nurse saved = repository.save(Fixtures.nurse("12345678900"));

        assertEquals(saved, useCase.find("12345678900").orElseThrow());
    }

    @Test
    void findIgnoresSurroundingWhitespace() {
        Nurse saved = repository.save(Fixtures.nurse("12345678900"));

        assertEquals(saved, useCase.find("  12345678900 ").orElseThrow());
    }

    @Test
    void findIsEmptyForAnUnknownCpf() {
        repository.save(Fixtures.nurse("12345678900"));

        assertTrue(useCase.find("00000000000").isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void findIsEmptyForABlankCpf(String cpf) {
        assertTrue(useCase.find(cpf).isEmpty());
    }
}
