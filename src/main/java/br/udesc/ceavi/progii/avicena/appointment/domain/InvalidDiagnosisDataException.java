package br.udesc.ceavi.progii.avicena.appointment.domain;

public class InvalidDiagnosisDataException extends RuntimeException {

    public InvalidDiagnosisDataException(String message) {
        super(message);
    }
}
