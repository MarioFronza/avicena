package br.udesc.ceavi.progii.avicena.appointment.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryAppointmentRepository;
import br.udesc.ceavi.progii.avicena.support.InMemoryPatientRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ListAppointmentsByPatientTest {

    private final InMemoryPatientRepository patients = new InMemoryPatientRepository();
    private final InMemoryAppointmentRepository appointments = new InMemoryAppointmentRepository();
    private final ListAppointmentsByPatient useCase = new ListAppointmentsByPatient(patients, appointments);

    @Test
    void listReturnsOnlyThatPatientsAppointments() {
        Patient patient = patients.save(Fixtures.patient("11111111111"));
        Patient other = patients.save(Fixtures.patient("22222222222"));
        Appointment first = appointments.save(Fixtures.appointment(patient.getId()));
        appointments.save(Fixtures.appointment(other.getId()));
        Appointment second = appointments.save(Fixtures.appointment(patient.getId()));

        assertEquals(List.of(first, second), useCase.list("11111111111"));
    }

    @Test
    void listIsEmptyForAnUnknownCpf() {
        Patient patient = patients.save(Fixtures.patient("11111111111"));
        appointments.save(Fixtures.appointment(patient.getId()));

        assertTrue(useCase.list("00000000000").isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void listIsEmptyForABlankCpf(String cpf) {
        assertTrue(useCase.list(cpf).isEmpty());
    }
}
