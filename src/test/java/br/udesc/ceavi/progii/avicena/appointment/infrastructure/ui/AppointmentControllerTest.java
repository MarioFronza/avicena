package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

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
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.usecase.ListPatients;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppointmentControllerTest {

    private InMemoryAppointmentRepository appointments;
    private InMemoryPatientRepository patients;
    private InMemoryDoctorRepository doctors;
    private InMemoryNurseRepository nurses;
    private AppointmentController ctrl;

    @BeforeEach
    void setUp() {
        appointments = new InMemoryAppointmentRepository();
        patients = new InMemoryPatientRepository();
        doctors = new InMemoryDoctorRepository();
        nurses = new InMemoryNurseRepository();
        ctrl = new AppointmentController(
                new RegisterAppointment(appointments),
                new DeleteAppointment(appointments),
                new ListPatients(patients),
                new ListDoctors(doctors),
                new ListNurses(nurses));
        patients.save(new Patient(7L, "Patient Test", "11111111111", "48999990000", null, MaritalStatus.SINGLE));
    }

    @Test
    void registerSavesTheAppointmentForThePatientWithTheTypedCpf() {
        Appointment appointment = ctrl.register(form("11111111111"));

        assertEquals(appointment, appointments.findAll().get(0));
        assertEquals(7L, appointment.getPatientId());
        assertEquals(3L, appointment.getDoctorId());
        assertEquals(5L, appointment.getNurseId());
    }

    @Test
    void registerRejectsACpfWithNoPatient() {
        assertThrows(InvalidAppointmentDataException.class, () -> ctrl.register(form("00000000000")));
    }

    @Test
    void deleteRemovesTheAppointment() {
        Appointment appointment = ctrl.register(form("11111111111"));

        ctrl.delete(appointment);

        assertTrue(appointments.findAll().isEmpty());
    }

    @Test
    void listsDoctorsAndNursesFromTheInjectedUseCases() {
        Doctor doctor =
                new Doctor(3L, "Doctor Test", "22222222222", "48999990001", null, MaritalStatus.SINGLE, "123", "GP");
        Nurse nurse =
                new Nurse(5L, "Nurse Test", "33333333333", "48999990002", null, MaritalStatus.SINGLE, "Nursing", 100);
        doctors.save(doctor);
        nurses.save(nurse);

        assertEquals(List.of(doctor), ctrl.doctors());
        assertEquals(List.of(nurse), ctrl.nurses());
    }

    private static AppointmentController.Form form(String patientCpf) {
        return new AppointmentController.Form("01/01/2026", "10:00", "Fever", patientCpf, 3L, 5L, UrgencyStatus.URGENT);
    }
}
