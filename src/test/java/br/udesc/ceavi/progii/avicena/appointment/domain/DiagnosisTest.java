package br.udesc.ceavi.progii.avicena.appointment.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class DiagnosisTest {

    @Test
    void rejectsANullAppointmentId() {
        assertThrows(InvalidDiagnosisDataException.class, () -> diagnosis(null, 120f, new BigDecimal("37"), 170, 70));
    }

    @Test
    void rejectsANegativeBloodPressure() {
        assertThrows(InvalidDiagnosisDataException.class, () -> diagnosis(1L, -1f, new BigDecimal("37"), 170, 70));
    }

    @Test
    void rejectsANegativeTemperature() {
        assertThrows(InvalidDiagnosisDataException.class, () -> diagnosis(1L, 120f, new BigDecimal("-1"), 170, 70));
    }

    @Test
    void rejectsANegativeHeight() {
        assertThrows(InvalidDiagnosisDataException.class, () -> diagnosis(1L, 120f, new BigDecimal("37"), -1, 70));
    }

    @Test
    void rejectsANegativeWeight() {
        assertThrows(InvalidDiagnosisDataException.class, () -> diagnosis(1L, 120f, new BigDecimal("37"), 170, -1));
    }

    @Test
    void acceptsZeroVitals() {
        assertDoesNotThrow(() -> diagnosis(1L, 0f, new BigDecimal("0"), 0, 0));
    }

    @Test
    void acceptsNoNotesOrDescriptions() {
        assertDoesNotThrow(() -> new Diagnosis(1L, 120f, new BigDecimal("37"), 170, 70, null, null, null, null, null));
    }

    @Test
    void exposesFieldsAfterValidConstruction() {
        Diagnosis diagnosis =
                new Diagnosis(1L, 120f, new BigDecimal("37"), 170, 70, "Notes", "Flu", "Rest", "Exam notes", "Final");

        assertEquals(1L, diagnosis.getAppointmentId());
        assertEquals(120f, diagnosis.getBloodPressure());
        assertEquals(new BigDecimal("37"), diagnosis.getTemperature());
        assertEquals(170, diagnosis.getHeight());
        assertEquals(70, diagnosis.getWeight());
        assertEquals("Notes", diagnosis.getNotes());
        assertEquals("Flu", diagnosis.getDisease());
        assertEquals("Rest", diagnosis.getMedications());
        assertEquals("Exam notes", diagnosis.getExamNotes());
        assertEquals("Final", diagnosis.getFinalDescription());
    }

    private static Diagnosis diagnosis(
            Long appointmentId, float bloodPressure, BigDecimal temperature, int height, int weight) {
        return new Diagnosis(
                appointmentId, bloodPressure, temperature, height, weight, "Notes", "Flu", "Rest", "Exam", "Final");
    }
}
