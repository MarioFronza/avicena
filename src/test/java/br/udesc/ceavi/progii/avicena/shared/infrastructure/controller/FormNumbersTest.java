package br.udesc.ceavi.progii.avicena.shared.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.shared.domain.InvalidDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class FormNumbersTest {

    @Test
    void parsesAnInteger() {
        int hours = FormNumbers.parse("8", "Hours", Integer::parseInt, TestDataException::new);

        assertEquals(8, hours);
    }

    @Test
    void parsesADecimal() {
        float salary = FormNumbers.parse("1.5", "Salary", Float::parseFloat, TestDataException::new);

        assertEquals(1.5f, salary);
    }

    @Test
    void ignoresSurroundingWhitespaceForIntegersAndDecimals() {
        int hours = FormNumbers.parse(" 8 ", "Hours", Integer::parseInt, TestDataException::new);
        float salary = FormNumbers.parse(" 1.5 ", "Salary", Float::parseFloat, TestDataException::new);

        assertEquals(8, hours);
        assertEquals(1.5f, salary);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "abc", "12,5", "1.5.2"})
    void rejectsTextThatIsNotAnInteger(String text) {
        TestDataException error = assertThrows(
                TestDataException.class,
                () -> FormNumbers.parse(text, "Hours", Integer::parseInt, TestDataException::new));

        assertEquals("Hours must be a number", error.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "abc", "12,5", "NaN", "Infinity", "-Infinity"})
    void rejectsTextThatIsNotAFiniteDecimal(String text) {
        assertThrows(
                TestDataException.class,
                () -> FormNumbers.parse(text, "Salary", Float::parseFloat, TestDataException::new));
    }

    @Test
    void keepsTheParseFailureAsTheCause() {
        TestDataException error = assertThrows(
                TestDataException.class,
                () -> FormNumbers.parse("abc", "Hours", Integer::parseInt, TestDataException::new));

        assertInstanceOf(NumberFormatException.class, error.getCause());
    }

    private static class TestDataException extends InvalidDataException {
        TestDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
