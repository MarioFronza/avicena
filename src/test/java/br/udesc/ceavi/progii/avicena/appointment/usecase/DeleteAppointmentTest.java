package br.udesc.ceavi.progii.avicena.appointment.usecase;

import static org.junit.jupiter.api.Assertions.assertFalse;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import org.junit.jupiter.api.Test;

class DeleteAppointmentTest {

    @Test
    void deletesTheAppointmentThroughTheRepository() {
        InMemoryAppointmentRepository repository = new InMemoryAppointmentRepository();
        Appointment appointment =
                new Appointment("05/08/2026", "14:00", "Febre", 1L, 2L, 3L, UrgencyStatus.NOT_URGENT);
        repository.save(appointment);
        DeleteAppointment useCase = new DeleteAppointment(repository);

        useCase.delete(appointment);

        assertFalse(repository.findAll().contains(appointment));
    }
}
