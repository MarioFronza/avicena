package br.udesc.ceavi.progii.avicena.shared.domain;

/** Base of every vertical's invalid-data exception, so shared code can raise or catch them uniformly. */
public abstract class InvalidDataException extends RuntimeException {

    protected InvalidDataException(String message) {
        super(message);
    }

    protected InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
