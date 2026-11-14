package br.udesc.ceavi.progii.avicena.support;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import java.time.LocalDate;
import java.time.LocalTime;

/** Valid domain objects for tests; vary only what a test cares about. */
public final class Fixtures {

    private Fixtures() {}

    public static Address address() {
        return new Address(100, "Apt 2", "88000000", "Main St", "Downtown", "Florianopolis");
    }

    public static Patient patient(String cpf) {
        return new Patient("Patient Test", cpf, "48999990000", address(), MaritalStatus.SINGLE);
    }

    public static Doctor doctor(String cpf) {
        return new Doctor(
                "Doctor Test", cpf, "48999990000", address(), MaritalStatus.SINGLE, "CRM-" + cpf, "Cardiology");
    }

    public static Nurse nurse(String cpf) {
        return new Nurse("Nurse Test", cpf, "48999990000", address(), MaritalStatus.SINGLE, "Technician", 100);
    }

    public static Receptionist receptionist(String cpf) {
        return new Receptionist(
                "Receptionist Test", cpf, "48999990000", address(), MaritalStatus.SINGLE, 40, 3000f, 5, 123);
    }

    public static Appointment appointment(Long patientId) {
        return new Appointment(
                LocalDate.of(2026, 8, 5),
                LocalTime.of(14, 0),
                "Fever",
                patientId,
                null,
                null,
                UrgencyStatus.NOT_URGENT);
    }

    public static Diagnosis diagnosis(Long appointmentId) {
        return new Diagnosis(appointmentId, 120f, 37, 170, 70, "Notes", "Flu", "Rest", "Exam notes", "Final");
    }
}
