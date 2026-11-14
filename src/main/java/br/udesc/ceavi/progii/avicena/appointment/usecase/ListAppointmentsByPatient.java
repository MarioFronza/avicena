package br.udesc.ceavi.progii.avicena.appointment.usecase;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.AppointmentRepository;
import br.udesc.ceavi.progii.avicena.patient.domain.PatientRepository;
import java.util.List;

public class ListAppointmentsByPatient {

    private final PatientRepository patients;
    private final AppointmentRepository appointments;

    public ListAppointmentsByPatient(PatientRepository patients, AppointmentRepository appointments) {
        this.patients = patients;
        this.appointments = appointments;
    }

    public List<Appointment> list(String cpf) {
        if (cpf == null || cpf.isBlank()) return List.of();
        return patients.findByCpf(cpf.strip())
                .map(patient -> appointments.findAll().stream()
                        .filter(appointment -> patient.getId().equals(appointment.getPatientId()))
                        .toList())
                .orElse(List.of());
    }
}
