package br.udesc.ceavi.progii.avicena.patient.domain;

public class InvalidPatientDataException extends RuntimeException {

    public InvalidPatientDataException(String message) {
        super(message);
    }
}
