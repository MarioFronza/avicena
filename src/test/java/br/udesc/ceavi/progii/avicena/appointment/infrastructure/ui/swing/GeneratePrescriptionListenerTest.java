package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.UrgencyStatusEntity;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorEntity;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.SpecialtyEntity;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.AddressEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class GeneratePrescriptionListenerTest {

    @Test
    void rejectsAnAppointmentWhoseDoctorHasNoAddress() {
        DoctorEntity doctor = newDoctor("Dr Test", "22222222222", "480000001", null, MaritalStatus.SINGLE);
        AppointmentEntity appointment = appointment(doctor, patient());

        assertThrows(
                IllegalStateException.class, () -> GeneratePrescriptionListener.buildPrescriptionLines(appointment));
    }

    @Test
    void rejectsAnAppointmentWhoseDoctorIsMissing() {
        AppointmentEntity appointment = appointment(null, patient());

        assertThrows(
                IllegalStateException.class, () -> GeneratePrescriptionListener.buildPrescriptionLines(appointment));
    }

    @Test
    void rejectsAnAppointmentWhosePatientHasNoAddress() {
        PatientEntity patient = newPatient("Patient Test", "11111111111", "480000000", null, MaritalStatus.SINGLE);
        AppointmentEntity appointment = appointment(doctor(), patient);

        assertThrows(
                IllegalStateException.class, () -> GeneratePrescriptionListener.buildPrescriptionLines(appointment));
    }

    @Test
    void buildsPrescriptionLinesForAFullyPopulatedAppointment() {
        AppointmentEntity appointment = appointment(doctor(), patient());

        List<String> lines = GeneratePrescriptionListener.buildPrescriptionLines(appointment);

        assertFalse(lines.isEmpty());
    }

    private AppointmentEntity appointment(DoctorEntity doctor, PatientEntity patient) {
        return new AppointmentEntity(
                null,
                LocalDate.of(2026, 8, 5),
                LocalTime.of(14, 0),
                "Fever",
                patient,
                doctor,
                null,
                new UrgencyStatusEntity(4L, "NOT_URGENT", "Not urgent"));
    }

    private DoctorEntity doctor() {
        AddressEntity address =
                new AddressEntity(null, 1, "Room 1", "88000000", "Doctor Street", "Downtown", "Florianopolis");
        return newDoctor("Dr Test", "22222222222", "480000001", address, MaritalStatus.SINGLE);
    }

    private static DoctorEntity newDoctor(
            String name, String cpf, String phone, AddressEntity address, MaritalStatus maritalStatus) {
        PersonEntity person = new PersonEntity(
                null,
                name,
                cpf,
                phone,
                address,
                new MaritalStatusEntity(1L, maritalStatus.name(), maritalStatus.name()));
        return new DoctorEntity(null, person, "CRM-1", new SpecialtyEntity(1L, "General Practice"));
    }

    private PatientEntity patient() {
        AddressEntity address =
                new AddressEntity(null, 2, "House", "88000001", "Patient Street", "Downtown", "Florianopolis");
        return newPatient("Patient Test", "11111111111", "480000000", address, MaritalStatus.SINGLE);
    }

    private static PatientEntity newPatient(
            String name, String cpf, String phone, AddressEntity address, MaritalStatus maritalStatus) {
        PersonEntity person = new PersonEntity(
                null,
                name,
                cpf,
                phone,
                address,
                new MaritalStatusEntity(1L, maritalStatus.name(), maritalStatus.name()));
        return new PatientEntity(null, person);
    }
}
