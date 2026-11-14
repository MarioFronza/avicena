package br.udesc.ceavi.progii.avicena.shared.domain;

import java.util.function.Function;

/** Constructor checks shared by every domain class; each caller supplies its own exception. */
public final class Invariants {

    private Invariants() {}

    public static String notBlank(String value, String description, Function<String, InvalidDataException> error) {
        if (value == null || value.isBlank()) throw error.apply(description + " must not be blank");
        return value;
    }

    public static <T> T notNull(T value, String description, Function<String, InvalidDataException> error) {
        if (value == null) throw error.apply(description + " must not be null");
        return value;
    }

    public static <N extends Number> N notNegative(
            N value, String description, Function<String, InvalidDataException> error) {
        if (value.doubleValue() < 0) throw error.apply(description + " must not be negative");
        return value;
    }
}
