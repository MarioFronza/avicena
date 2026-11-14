package br.udesc.ceavi.progii.avicena.appointment.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryAppointmentRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegisterAppointmentTest {

    private final InMemoryAppointmentRepository repository = new InMemoryAppointmentRepository();

    @Test
    void registerSavesTheAppointmentAndAssignsAnId() {
        Appointment registered = new RegisterAppointment(repository).register(Fixtures.appointment(1L));

        assertNotNull(registered.getId());
        assertEquals(List.of(registered), repository.findAll());
    }
}
