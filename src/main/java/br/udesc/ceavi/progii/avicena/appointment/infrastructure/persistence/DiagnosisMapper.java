package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;

final class DiagnosisMapper {

    private DiagnosisMapper() {}

    static DiagnosisEntity toEntity(Diagnosis diagnosis, AppointmentEntity appointment) {
        FinalDiagnosisEntity finalDiagnosis = new FinalDiagnosisEntity(
                null,
                diagnosis.getDisease(),
                diagnosis.getMedications(),
                diagnosis.getFinalDescription(),
                diagnosis.getExamNotes());
        return new DiagnosisEntity(
                diagnosis.getId(),
                diagnosis.getBloodPressure(),
                diagnosis.getTemperature(),
                diagnosis.getHeight(),
                diagnosis.getWeight(),
                diagnosis.getNotes(),
                appointment,
                finalDiagnosis);
    }

    static Diagnosis toDomain(DiagnosisEntity entity) {
        FinalDiagnosisEntity finalDiagnosis = entity.getFinalDiagnosis();
        return new Diagnosis(
                entity.getId(),
                entity.getAppointment() == null ? null : entity.getAppointment().getId(),
                entity.getBloodPressure(),
                entity.getTemperature(),
                entity.getHeight(),
                entity.getWeight(),
                entity.getMedicationHistory(),
                finalDiagnosis == null ? null : finalDiagnosis.getDisease(),
                finalDiagnosis == null ? null : finalDiagnosis.getMedications(),
                finalDiagnosis == null ? null : finalDiagnosis.getExamNotes(),
                finalDiagnosis == null ? null : finalDiagnosis.getDescription());
    }
}
