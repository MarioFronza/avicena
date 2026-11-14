package br.udesc.ceavi.progii.avicena.appointment.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.support.InMemoryAppointmentRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ListAppointmentsTest {

    @Test
    void listsAppointmentsFromTheRepository() {
        InMemoryAppointmentRepository repository = new InMemoryAppointmentRepository();
        Appointment appointment = new Appointment(
                LocalDate.of(2026, 8, 5), LocalTime.of(14, 0), "Fever", 1L, 2L, 3L, UrgencyStatus.NOT_URGENT);
        repository.save(appointment);
        ListAppointments useCase = new ListAppointments(repository);

        var appointments = useCase.list();

        assertEquals(1, appointments.size());
        assertTrue(appointments.contains(appointment));
    }
}
