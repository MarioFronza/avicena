package br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteAppointment;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterAppointment;
import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.ListDoctors;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.ListNurses;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.usecase.ListPatients;
import java.util.List;

public class AppointmentController {

    public record Form(
            String date,
            String time,
            String symptoms,
            String patientCpf,
            Long doctorId,
            Long nurseId,
            UrgencyStatus urgencyStatus) {}

    private final RegisterAppointment registerAppointment;
    private final DeleteAppointment deleteAppointment;
    private final ListPatients listPatients;
    private final ListDoctors listDoctors;
    private final ListNurses listNurses;

    public AppointmentController(
            RegisterAppointment registerAppointment,
            DeleteAppointment deleteAppointment,
            ListPatients listPatients,
            ListDoctors listDoctors,
            ListNurses listNurses) {
        this.registerAppointment = registerAppointment;
        this.deleteAppointment = deleteAppointment;
        this.listPatients = listPatients;
        this.listDoctors = listDoctors;
        this.listNurses = listNurses;
    }

    public Appointment register(Form form) {
        return registerAppointment.register(new Appointment(
                form.date(),
                form.time(),
                form.symptoms(),
                findPatientIdByCpf(form.patientCpf()),
                form.doctorId(),
                form.nurseId(),
                form.urgencyStatus()));
    }

    public void delete(Appointment appointment) {
        deleteAppointment.delete(appointment);
    }

    public List<Doctor> doctors() {
        return listDoctors.list();
    }

    public List<Nurse> nurses() {
        return listNurses.list();
    }

    private Long findPatientIdByCpf(String cpf) {
        return listPatients.list().stream()
                .filter(patient -> cpf.equals(patient.getCpf()))
                .findFirst()
                .map(Patient::getId)
                .orElse(null);
    }
}
