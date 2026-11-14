package br.udesc.ceavi.progii.avicena.doctor.domain;

import br.udesc.ceavi.progii.avicena.shared.domain.InvalidDataException;

public class InvalidDoctorDataException extends InvalidDataException {

    public InvalidDoctorDataException(String message) {
        super(message);
    }

    public InvalidDoctorDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
