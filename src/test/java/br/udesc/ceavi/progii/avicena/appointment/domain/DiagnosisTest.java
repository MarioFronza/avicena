package br.udesc.ceavi.progii.avicena.appointment.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DiagnosisTest {

    @Test
    void rejectsNullAppointmentId() {
        assertThrows(
                InvalidDiagnosisDataException.class,
                () -> new Diagnosis(
                        null, 120f, 37, 170, 70, "Padrão", "Gripe", "Repouso", "Exame padrão", "Padrão"));
    }
}
