package br.udesc.ceavi.progii.avicena.appointment.domain;

public class InvalidAppointmentDataException extends RuntimeException {

    public InvalidAppointmentDataException(String message) {
        super(message);
    }
}
