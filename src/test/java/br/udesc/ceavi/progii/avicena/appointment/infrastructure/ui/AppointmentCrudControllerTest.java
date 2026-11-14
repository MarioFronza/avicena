package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppointmentCrudControllerTest {

    private InMemoryAppointmentRepository appointments;
    private InMemoryPatientRepository patients;
    private InMemoryDoctorRepository doctors;
    private InMemoryNurseRepository nurses;
    private AppointmentRegistrationFrame frame;
    private AppointmentCrudController ctrl;

    @BeforeEach
    void setUp() {
        appointments = new InMemoryAppointmentRepository();
        patients = new InMemoryPatientRepository();
        doctors = new InMemoryDoctorRepository();
        nurses = new InMemoryNurseRepository();
        frame = new AppointmentRegistrationFrame(new ListDoctors(doctors), new ListNurses(nurses));
        ctrl = new AppointmentCrudController(
                frame,
                new RegisterAppointment(appointments),
                new DeleteAppointment(appointments),
                new ListPatients(patients));
    }

    @Test
    void registerSavesTheAppointmentForThePatientWithTheTypedCpf() {
        patients.save(new Patient(7L, "Patient Test", "11111111111", "48999990000", null, MaritalStatus.SINGLE));
        frame.getPatientCpfField().setText("11111111111");
        frame.getSymptomsField().setText("Fever");
        frame.getUrgencyComboBox().setSelectedItem(UrgencyStatus.URGENT);

        ctrl.register();

        assertEquals(7L, appointments.findAll().get(0).getPatientId());
    }

    @Test
    void registerUsesTheDoctorAndNurseSelectedFromTheInjectedLists() {
        patients.save(new Patient(7L, "Patient Test", "11111111111", "48999990000", null, MaritalStatus.SINGLE));
        doctors.save(
                new Doctor(3L, "Doctor Test", "22222222222", "48999990001", null, MaritalStatus.SINGLE, "123", "GP"));
        nurses.save(
                new Nurse(5L, "Nurse Test", "33333333333", "48999990002", null, MaritalStatus.SINGLE, "Nursing", 100));
        frame.reloadDoctorsAndNurses();
        frame.getPatientCpfField().setText("11111111111");

        ctrl.register();

        Appointment saved = appointments.findAll().get(0);
        assertEquals(3L, saved.getDoctorId());
        assertEquals(5L, saved.getNurseId());
    }

    @Test
    void deleteRemovesTheCurrentAppointment() {
        Appointment appointment = new Appointment("01/01/2026", "10:00", "Fever", 7L, null, null, UrgencyStatus.URGENT);
        appointments.save(appointment);
        ctrl.setAppointment(appointment);

        ctrl.delete();

        assertTrue(appointments.findAll().isEmpty());
    }
}
