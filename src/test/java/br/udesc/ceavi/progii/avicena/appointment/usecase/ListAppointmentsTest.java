package br.udesc.ceavi.progii.avicena.appointment.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryAppointmentRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class ListAppointmentsTest {

    private final InMemoryAppointmentRepository repository = new InMemoryAppointmentRepository();
    private final ListAppointments useCase = new ListAppointments(repository);

    @Test
    void listReturnsNothingForAnEmptyRepository() {
        assertTrue(useCase.list().isEmpty());
    }

    @Test
    void listReturnsEveryAppointmentInTheOrderTheyWereSaved() {
        Appointment first = repository.save(Fixtures.appointment(1L));
        Appointment second = repository.save(Fixtures.appointment(2L));

        assertEquals(List.of(first, second), useCase.list());
    }
}
