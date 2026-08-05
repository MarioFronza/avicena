package br.udesc.ceavi.progii.avicena.nurse.domain;

public class InvalidNurseDataException extends RuntimeException {

    public InvalidNurseDataException(String message) {
        super(message);
    }
}
