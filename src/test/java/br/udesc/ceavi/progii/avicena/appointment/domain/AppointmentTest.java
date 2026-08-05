package br.udesc.ceavi.progii.avicena.appointment.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AppointmentTest {

    @Test
    void rejectsNullPatientId() {
        assertThrows(
                InvalidAppointmentDataException.class,
                () -> new Appointment("05/08/2026", "14:00", "Febre", null, 1L, 1L, UrgencyStatus.NOT_URGENT));
    }

    @Test
    void exposesFieldsAfterValidConstruction() {
        Appointment appointment = new Appointment("05/08/2026", "14:00", "Febre", 1L, 2L, 3L, UrgencyStatus.NOT_URGENT);

        assertEquals("05/08/2026", appointment.getDate());
        assertEquals("14:00", appointment.getTime());
        assertEquals("Febre", appointment.getSymptoms());
        assertEquals(1L, appointment.getPatientId());
        assertEquals(2L, appointment.getDoctorId());
        assertEquals(3L, appointment.getNurseId());
        assertEquals(UrgencyStatus.NOT_URGENT, appointment.getUrgencyStatus());
    }
}
