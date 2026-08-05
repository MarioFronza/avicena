package br.udesc.ceavi.progii.avicena.doctor.domain;

public class InvalidDoctorDataException extends RuntimeException {

    public InvalidDoctorDataException(String message) {
        super(message);
    }
}
