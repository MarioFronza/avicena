package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorEntity;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence.NurseEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;

final class AppointmentMapper {

    private AppointmentMapper() {}

    static AppointmentEntity toEntity(
            Appointment appointment, PatientEntity patient, DoctorEntity doctor, NurseEntity nurse) {
        return new AppointmentEntity(
                appointment.getId(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getSymptoms(),
                patient,
                doctor,
                nurse,
                appointment.getUrgencyStatus());
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
                entity.getUrgencyStatus());
    }
}
