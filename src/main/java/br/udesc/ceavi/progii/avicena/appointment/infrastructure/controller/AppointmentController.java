package br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.InvalidAppointmentDataException;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteAppointment;
import br.udesc.ceavi.progii.avicena.appointment.usecase.ListAppointments;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterAppointment;
import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.ListDoctors;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.ListNurses;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.usecase.FindPatientByCpf;
import br.udesc.ceavi.progii.avicena.patient.usecase.ListPatients;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /** One line of the appointment list; people names are null when not assigned. */
    public record Row(
            Long id, LocalDate date, LocalTime time, String patientName, String doctorName, String nurseName) {}

    /** What the prescription PDF prints, already checked for completeness. */
    public record Prescription(
            String doctorName,
            String doctorStreet,
            String doctorNeighborhood,
            String crm,
            String patientName,
            String patientStreet,
            String patientNeighborhood) {}

    private final RegisterAppointment registerAppointment;
    private final DeleteAppointment deleteAppointment;
    private final ListAppointments listAppointments;
    private final FindPatientByCpf findPatientByCpf;
    private final ListPatients listPatients;
    private final ListDoctors listDoctors;
    private final ListNurses listNurses;

    public AppointmentController(
            RegisterAppointment registerAppointment,
            DeleteAppointment deleteAppointment,
            ListAppointments listAppointments,
            FindPatientByCpf findPatientByCpf,
            ListPatients listPatients,
            ListDoctors listDoctors,
            ListNurses listNurses) {
        this.registerAppointment = registerAppointment;
        this.deleteAppointment = deleteAppointment;
        this.listAppointments = listAppointments;
        this.findPatientByCpf = findPatientByCpf;
        this.listPatients = listPatients;
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

    public List<Row> list() {
        Map<Long, Patient> patients = byId(listPatients.list(), Patient::getId);
        Map<Long, Doctor> doctors = byId(listDoctors.list(), Doctor::getId);
        Map<Long, Nurse> nurses = byId(listNurses.list(), Nurse::getId);
        return listAppointments.list().stream()
                .map(appointment -> new Row(
                        appointment.getId(),
                        appointment.getDate(),
                        appointment.getTime(),
                        nameOf(patients.get(appointment.getPatientId()), Patient::getName),
                        nameOf(doctors.get(appointment.getDoctorId()), Doctor::getName),
                        nameOf(nurses.get(appointment.getNurseId()), Nurse::getName)))
                .toList();
    }

    public Prescription prescription(Long appointmentId) {
        Appointment appointment = listAppointments.list().stream()
                .filter(candidate -> candidate.getId().equals(appointmentId))
                .findFirst()
                .orElseThrow(() -> new InvalidAppointmentDataException("Select a valid appointment"));
        Doctor doctor = byId(listDoctors.list(), Doctor::getId).get(appointment.getDoctorId());
        if (doctor == null || doctor.getAddress() == null) {
            throw new InvalidAppointmentDataException("Doctor or doctor's address not registered");
        }
        Patient patient = byId(listPatients.list(), Patient::getId).get(appointment.getPatientId());
        if (patient == null || patient.getAddress() == null) {
            throw new InvalidAppointmentDataException("Patient or patient's address not registered");
        }
        return new Prescription(
                doctor.getName(),
                doctor.getAddress().getStreet(),
                doctor.getAddress().getNeighborhood(),
                doctor.getCrm(),
                patient.getName(),
                patient.getAddress().getStreet(),
                patient.getAddress().getNeighborhood());
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

    private static <T> Map<Long, T> byId(List<T> items, Function<T, Long> id) {
        return items.stream().collect(Collectors.toMap(id, item -> item));
    }

    private static <T> String nameOf(T person, Function<T, String> name) {
        return person == null ? null : name.apply(person);
    }
}
