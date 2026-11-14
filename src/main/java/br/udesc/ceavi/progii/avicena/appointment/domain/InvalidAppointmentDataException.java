package br.udesc.ceavi.progii.avicena.appointment.domain;

import br.udesc.ceavi.progii.avicena.shared.domain.InvalidDataException;

public class InvalidAppointmentDataException extends InvalidDataException {

    public InvalidAppointmentDataException(String message) {
        super(message);
    }

    public InvalidAppointmentDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
