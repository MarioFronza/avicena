package br.udesc.ceavi.progii.avicena.appointment.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import org.junit.jupiter.api.Test;

class RegisterAppointmentTest {

    @Test
    void savesTheAppointmentThroughTheRepository() {
        InMemoryAppointmentRepository repository = new InMemoryAppointmentRepository();
        RegisterAppointment useCase = new RegisterAppointment(repository);
        Appointment appointment =
                new Appointment("05/08/2026", "14:00", "Febre", 1L, 2L, 3L, UrgencyStatus.NOT_URGENT);

        Appointment registered = useCase.register(appointment);

        assertEquals(appointment, registered);
        assertTrue(repository.findAll().contains(appointment));
    }
}
