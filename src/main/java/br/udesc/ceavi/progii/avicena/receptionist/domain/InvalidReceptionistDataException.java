package br.udesc.ceavi.progii.avicena.receptionist.domain;

import br.udesc.ceavi.progii.avicena.shared.domain.InvalidDataException;

public class InvalidReceptionistDataException extends InvalidDataException {

    public InvalidReceptionistDataException(String message) {
        super(message);
    }

    public InvalidReceptionistDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
