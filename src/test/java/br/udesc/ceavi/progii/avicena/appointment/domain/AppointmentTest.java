package br.udesc.ceavi.progii.avicena.appointment.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AppointmentTest {

    private static final LocalDate DATE = LocalDate.of(2026, 8, 5);
    private static final LocalTime TIME = LocalTime.of(14, 0);

    @Test
    void rejectsANullPatientId() {
        assertThrows(
                InvalidAppointmentDataException.class,
                () -> new Appointment(DATE, TIME, "Fever", null, 1L, 1L, UrgencyStatus.NOT_URGENT));
    }

    @Test
    void rejectsANullDate() {
        assertThrows(
                InvalidAppointmentDataException.class,
                () -> new Appointment(null, TIME, "Fever", 1L, 1L, 1L, UrgencyStatus.NOT_URGENT));
    }

    @Test
    void rejectsANullTime() {
        assertThrows(
                InvalidAppointmentDataException.class,
                () -> new Appointment(DATE, null, "Fever", 1L, 1L, 1L, UrgencyStatus.NOT_URGENT));
    }

    @Test
    void rejectsANullUrgencyStatus() {
        assertThrows(
                InvalidAppointmentDataException.class, () -> new Appointment(DATE, TIME, "Fever", 1L, 1L, 1L, null));
    }

    @Test
    void acceptsNoDoctorNurseOrSymptoms() {
        assertDoesNotThrow(() -> new Appointment(DATE, TIME, null, 1L, null, null, UrgencyStatus.NOT_URGENT));
    }

    @Test
    void exposesFieldsAfterValidConstruction() {
        Appointment appointment = new Appointment(DATE, TIME, "Fever", 1L, 2L, 3L, UrgencyStatus.NOT_URGENT);

        assertEquals(DATE, appointment.getDate());
        assertEquals(TIME, appointment.getTime());
        assertEquals("Fever", appointment.getSymptoms());
        assertEquals(1L, appointment.getPatientId());
        assertEquals(2L, appointment.getDoctorId());
        assertEquals(3L, appointment.getNurseId());
        assertEquals(UrgencyStatus.NOT_URGENT, appointment.getUrgencyStatus());
    }
}
