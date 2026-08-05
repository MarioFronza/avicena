package br.udesc.ceavi.progii.avicena.appointment.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import org.junit.jupiter.api.Test;

class ListAppointmentsTest {

    @Test
    void listsAppointmentsFromTheRepository() {
        InMemoryAppointmentRepository repository = new InMemoryAppointmentRepository();
        Appointment appointment = new Appointment("05/08/2026", "14:00", "Febre", 1L, 2L, 3L, UrgencyStatus.NOT_URGENT);
        repository.save(appointment);
        ListAppointments useCase = new ListAppointments(repository);

        var appointments = useCase.list();

        assertEquals(1, appointments.size());
        assertTrue(appointments.contains(appointment));
    }
}
