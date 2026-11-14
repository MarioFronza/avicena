package br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.usecase.ListAppointmentsByPatient;
import java.util.List;

public class PatientHistoryController {

    private final ListAppointmentsByPatient listAppointmentsByPatient;

    public PatientHistoryController(ListAppointmentsByPatient listAppointmentsByPatient) {
        this.listAppointmentsByPatient = listAppointmentsByPatient;
    }

    public List<Appointment> findByPatientCpf(String cpf) {
        return listAppointmentsByPatient.list(cpf);
    }
}
