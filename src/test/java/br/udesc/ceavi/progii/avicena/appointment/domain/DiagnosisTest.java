package br.udesc.ceavi.progii.avicena.appointment.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DiagnosisTest {

    @Test
    void rejectsNullAppointmentId() {
        assertThrows(
                InvalidDiagnosisDataException.class,
                () -> new Diagnosis(null, 120f, 37, 170, 70, "Default", "Flu", "Rest", "Default exam", "Default"));
    }

    @Test
    void exposesFieldsAfterValidConstruction() {
        Diagnosis diagnosis = new Diagnosis(1L, 120f, 37, 170, 70, "Default", "Flu", "Rest", "Default exam", "Default");

        assertEquals(1L, diagnosis.getAppointmentId());
        assertEquals(120f, diagnosis.getBloodPressure());
        assertEquals(37, diagnosis.getTemperature());
        assertEquals(170, diagnosis.getHeight());
        assertEquals(70, diagnosis.getWeight());
        assertEquals("Default", diagnosis.getNotes());
        assertEquals("Flu", diagnosis.getDisease());
        assertEquals("Rest", diagnosis.getMedications());
        assertEquals("Default exam", diagnosis.getExamNotes());
        assertEquals("Default", diagnosis.getFinalDescription());
    }
}
