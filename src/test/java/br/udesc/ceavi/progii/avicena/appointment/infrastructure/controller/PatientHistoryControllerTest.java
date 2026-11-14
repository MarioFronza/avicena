package br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.usecase.ListAppointmentsByPatient;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryAppointmentRepository;
import br.udesc.ceavi.progii.avicena.support.InMemoryPatientRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class PatientHistoryControllerTest {

    private final InMemoryPatientRepository patients = new InMemoryPatientRepository();
    private final InMemoryAppointmentRepository appointments = new InMemoryAppointmentRepository();
    private final PatientHistoryController controller =
            new PatientHistoryController(new ListAppointmentsByPatient(patients, appointments));

    @Test
    void findByPatientCpfReturnsThatPatientsAppointments() {
        Patient patient = patients.save(Fixtures.patient("11111111111"));
        Patient other = patients.save(Fixtures.patient("22222222222"));
        Appointment appointment = appointments.save(Fixtures.appointment(patient.getId()));
        appointments.save(Fixtures.appointment(other.getId()));

        assertEquals(List.of(appointment), controller.findByPatientCpf("11111111111"));
    }

    @Test
    void findByPatientCpfIsEmptyForAnUnknownCpf() {
        assertTrue(controller.findByPatientCpf("00000000000").isEmpty());
    }
}
