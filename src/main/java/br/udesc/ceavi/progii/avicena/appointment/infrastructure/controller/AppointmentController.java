package br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.InvalidAppointmentDataException;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteAppointment;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterAppointment;
import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.ListDoctors;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.ListNurses;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.usecase.FindPatientByCpf;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.BiFunction;

public class AppointmentController {

    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final String TIME_PATTERN = "HH:mm";
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern(TIME_PATTERN);

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
    private final FindPatientByCpf findPatientByCpf;
    private final ListDoctors listDoctors;
    private final ListNurses listNurses;

    public AppointmentController(
            RegisterAppointment registerAppointment,
            DeleteAppointment deleteAppointment,
            FindPatientByCpf findPatientByCpf,
            ListDoctors listDoctors,
            ListNurses listNurses) {
        this.registerAppointment = registerAppointment;
        this.deleteAppointment = deleteAppointment;
        this.findPatientByCpf = findPatientByCpf;
        this.listDoctors = listDoctors;
        this.listNurses = listNurses;
    }

    public Appointment register(Form form) {
        return registerAppointment.register(new Appointment(
                parse(form.date(), DATE_PATTERN, LocalDate::parse, "Appointment date"),
                parse(form.time(), TIME_PATTERN, LocalTime::parse, "Appointment time"),
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
        if (cpf == null || cpf.isBlank()) {
            throw new InvalidAppointmentDataException("Appointment patient CPF must not be blank");
        }
        return findPatientByCpf
                .find(cpf)
                .map(Patient::getId)
                .orElseThrow(
                        () -> new InvalidAppointmentDataException("No patient registered with CPF " + cpf.strip()));
    }

    private static <T> T parse(
            String text, String pattern, BiFunction<String, DateTimeFormatter, T> parser, String field) {
        if (text == null || text.isBlank()) return null;
        try {
            return parser.apply(text.strip(), DateTimeFormatter.ofPattern(pattern));
        } catch (DateTimeParseException e) {
            throw new InvalidAppointmentDataException(field + " must be in " + pattern + " format", e);
        }
    }
}
