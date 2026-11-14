package br.udesc.ceavi.progii.avicena.appointment.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryAppointmentRepository;
import org.junit.jupiter.api.Test;

class DeleteAppointmentTest {

    private final InMemoryAppointmentRepository repository = new InMemoryAppointmentRepository();
    private final DeleteAppointment useCase = new DeleteAppointment(repository);

    @Test
    void deleteRemovesTheAppointment() {
        Appointment saved = repository.save(Fixtures.appointment(1L));

        useCase.delete(saved);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void deleteOfAnUnsavedAppointmentIsANoOp() {
        repository.save(Fixtures.appointment(1L));

        useCase.delete(Fixtures.appointment(1L));

        assertEquals(1, repository.findAll().size());
    }
}
