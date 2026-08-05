package br.udesc.ceavi.progii.avicena.appointment.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AppointmentTest {

    @Test
    void rejectsNullPatientId() {
        assertThrows(
                InvalidAppointmentDataException.class,
                () -> new Appointment(
                        "05/08/2026", "14:00", "Febre", null, 1L, 1L, UrgencyStatus.NOT_URGENT));
    }
}
