package br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import br.udesc.ceavi.progii.avicena.support.InMemoryAppointmentRepository;
import br.udesc.ceavi.progii.avicena.support.InMemoryDoctorRepository;
import br.udesc.ceavi.progii.avicena.support.InMemoryNurseRepository;
import br.udesc.ceavi.progii.avicena.support.InMemoryPatientRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class AppointmentControllerTest {

    private final InMemoryAppointmentRepository appointments = new InMemoryAppointmentRepository();
    private final InMemoryPatientRepository patients = new InMemoryPatientRepository();
    private final InMemoryDoctorRepository doctors = new InMemoryDoctorRepository();
    private final InMemoryNurseRepository nurses = new InMemoryNurseRepository();
    private final AppointmentController controller = new AppointmentController(
            new RegisterAppointment(appointments),
            new DeleteAppointment(appointments),
            new FindPatientByCpf(patients),
            new ListDoctors(doctors),
            new ListNurses(nurses));
    private Patient patient;

    @BeforeEach
    void savePatient() {
        patient = patients.save(Fixtures.patient("11111111111"));
    }

    @Test
    void registerSavesTheAppointmentForThePatientWithTheTypedCpf() {
        Appointment appointment = controller.register(form("11111111111", "05/08/2026", "14:30"));

        assertEquals(patient.getId(), appointment.getPatientId());
        assertEquals(LocalDate.of(2026, 8, 5), appointment.getDate());
        assertEquals(LocalTime.of(14, 30), appointment.getTime());
        assertEquals(List.of(appointment), appointments.findAll());
    }

    @Test
    void registerRejectsACpfWithNoPatient() {
        InvalidAppointmentDataException error = assertThrows(
                InvalidAppointmentDataException.class,
                () -> controller.register(form("00000000000", "05/08/2026", "14:30")));

        assertEquals("No patient registered with CPF 00000000000", error.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void registerRejectsABlankPatientCpf(String cpf) {
        assertThrows(
                InvalidAppointmentDataException.class, () -> controller.register(form(cpf, "05/08/2026", "14:30")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2026-08-05", "32/08/2026", "5/8/26", "  /  /    "})
    void registerRejectsADateNotInDayMonthYearFormat(String date) {
        assertThrows(
                InvalidAppointmentDataException.class, () -> controller.register(form("11111111111", date, "14:30")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"25:00", "2pm", "  :  "})
    void registerRejectsATimeNotInHourMinuteFormat(String time) {
        assertThrows(
                InvalidAppointmentDataException.class,
                () -> controller.register(form("11111111111", "05/08/2026", time)));
    }

    @Test
    void registerRejectsAMissingDate() {
        assertThrows(
                InvalidAppointmentDataException.class, () -> controller.register(form("11111111111", "", "14:30")));
    }

    @Test
    void deleteRemovesTheAppointment() {
        Appointment appointment = controller.register(form("11111111111", "05/08/2026", "14:30"));

        controller.delete(appointment);

        assertTrue(appointments.findAll().isEmpty());
    }

    @Test
    void listsDoctorsAndNursesFromTheInjectedUseCases() {
        Doctor doctor = doctors.save(Fixtures.doctor("22222222222"));
        Nurse nurse = nurses.save(Fixtures.nurse("33333333333"));

        assertEquals(List.of(doctor), controller.doctors());
        assertEquals(List.of(nurse), controller.nurses());
    }

    private static AppointmentController.Form form(String patientCpf, String date, String time) {
        return new AppointmentController.Form(date, time, "Fever", patientCpf, null, null, UrgencyStatus.URGENT);
    }
}
