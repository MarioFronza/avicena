package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorEntity;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence.NurseEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

final class AppointmentMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private AppointmentMapper() {}

    static AppointmentEntity toEntity(
            Appointment appointment,
            PatientEntity patient,
            DoctorEntity doctor,
            NurseEntity nurse,
            UrgencyStatusEntity urgencyStatus) {
        LocalDate date = appointment.getDate() == null ? null : LocalDate.parse(appointment.getDate(), DATE_FORMATTER);
        LocalTime time = appointment.getTime() == null ? null : LocalTime.parse(appointment.getTime(), TIME_FORMATTER);
        return new AppointmentEntity(
                appointment.getId(), date, time, appointment.getSymptoms(), patient, doctor, nurse, urgencyStatus);
    }

    static Appointment toDomain(AppointmentEntity entity) {
        return new Appointment(
                entity.getId(),
                entity.getDate(),
                entity.getTime(),
                entity.getSymptoms(),
                entity.getPatient() == null ? null : entity.getPatient().getId(),
                entity.getDoctor() == null ? null : entity.getDoctor().getId(),
                entity.getNurse() == null ? null : entity.getNurse().getId(),
                entity.getUrgencyStatus() == null
                        ? null
                        : UrgencyStatus.valueOf(entity.getUrgencyStatus().getCode()));
    }
}
