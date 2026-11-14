package br.udesc.ceavi.progii.avicena.shared.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.shared.domain.InvalidDataException;
import java.util.function.BiFunction;
import java.util.function.Function;

/** Turns a raw form value into a number, raising the caller's domain exception when it isn't one. */
public final class FormNumbers {

    private FormNumbers() {}

    public static <N extends Number> N parse(
            String text,
            String field,
            Function<String, N> parser,
            BiFunction<String, Throwable, ? extends InvalidDataException> error) {
        String message = field + " must be a number";
        try {
            N value = parser.apply(text == null ? "" : text.strip());
            if (Double.isNaN(value.doubleValue()) || Double.isInfinite(value.doubleValue())) {
                throw error.apply(message, null);
            }
            return value;
        } catch (NumberFormatException e) {
            throw error.apply(message, e);
        }
    }
}
