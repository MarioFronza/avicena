package br.udesc.ceavi.progii.avicena.appointment.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DiagnosisTest {

    @Test
    void rejectsNullAppointmentId() {
        assertThrows(
                InvalidDiagnosisDataException.class,
                () -> new Diagnosis(null, 120f, 37, 170, 70, "Padrão", "Gripe", "Repouso", "Exame padrão", "Padrão"));
    }

    @Test
    void exposesFieldsAfterValidConstruction() {
        Diagnosis diagnosis =
                new Diagnosis(1L, 120f, 37, 170, 70, "Padrão", "Gripe", "Repouso", "Exame padrão", "Padrão");

        assertEquals(1L, diagnosis.getAppointmentId());
        assertEquals(120f, diagnosis.getBloodPressure());
        assertEquals(37, diagnosis.getTemperature());
        assertEquals(170, diagnosis.getHeight());
        assertEquals(70, diagnosis.getWeight());
        assertEquals("Padrão", diagnosis.getNotes());
        assertEquals("Gripe", diagnosis.getDisease());
        assertEquals("Repouso", diagnosis.getMedications());
        assertEquals("Exame padrão", diagnosis.getExamNotes());
        assertEquals("Padrão", diagnosis.getFinalDescription());
    }
}
