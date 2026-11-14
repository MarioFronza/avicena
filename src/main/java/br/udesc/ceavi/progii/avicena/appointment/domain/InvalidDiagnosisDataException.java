package br.udesc.ceavi.progii.avicena.appointment.domain;

import br.udesc.ceavi.progii.avicena.shared.domain.InvalidDataException;

public class InvalidDiagnosisDataException extends InvalidDataException {

    public InvalidDiagnosisDataException(String message) {
        super(message);
    }

    public InvalidDiagnosisDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
