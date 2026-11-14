package br.udesc.ceavi.progii.avicena.nurse.domain;

import br.udesc.ceavi.progii.avicena.shared.domain.InvalidDataException;

public class InvalidNurseDataException extends InvalidDataException {

    public InvalidNurseDataException(String message) {
        super(message);
    }

    public InvalidNurseDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
