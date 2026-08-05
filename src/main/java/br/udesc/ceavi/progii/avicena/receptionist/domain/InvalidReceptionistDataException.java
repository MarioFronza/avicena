package br.udesc.ceavi.progii.avicena.receptionist.domain;

public class InvalidReceptionistDataException extends RuntimeException {

    public InvalidReceptionistDataException(String message) {
        super(message);
    }
}
