package br.udesc.ceavi.progii.avicena.appointment.domain;

import br.udesc.ceavi.progii.avicena.shared.domain.Invariants;

public class Diagnosis {

    private final Long id;
    private final Long appointmentId;
    private final float bloodPressure;
    private final int temperature;
    private final int height;
    private final int weight;
    private final String notes;
    private final String disease;
    private final String medications;
    private final String examNotes;
    private final String finalDescription;

    public Diagnosis(
            Long appointmentId,
            float bloodPressure,
            int temperature,
            int height,
            int weight,
            String notes,
            String disease,
            String medications,
            String examNotes,
            String finalDescription) {
        this(
                null,
                appointmentId,
                bloodPressure,
                temperature,
                height,
                weight,
                notes,
                disease,
                medications,
                examNotes,
                finalDescription);
    }

    public Diagnosis(
            Long id,
            Long appointmentId,
            float bloodPressure,
            int temperature,
            int height,
            int weight,
            String notes,
            String disease,
            String medications,
            String examNotes,
            String finalDescription) {
        this.id = id;
        this.appointmentId =
                Invariants.notNull(appointmentId, "Diagnosis appointment", InvalidDiagnosisDataException::new);
        this.bloodPressure =
                Invariants.notNegative(bloodPressure, "Diagnosis blood pressure", InvalidDiagnosisDataException::new);
        this.temperature =
                Invariants.notNegative(temperature, "Diagnosis temperature", InvalidDiagnosisDataException::new);
        this.height = Invariants.notNegative(height, "Diagnosis height", InvalidDiagnosisDataException::new);
        this.weight = Invariants.notNegative(weight, "Diagnosis weight", InvalidDiagnosisDataException::new);
        this.notes = notes;
        this.disease = disease;
        this.medications = medications;
        this.examNotes = examNotes;
        this.finalDescription = finalDescription;
    }

    public Long getId() {
        return id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public float getBloodPressure() {
        return bloodPressure;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public String getNotes() {
        return notes;
    }

    public String getDisease() {
        return disease;
    }

    public String getMedications() {
        return medications;
    }

    public String getExamNotes() {
        return examNotes;
    }

    public String getFinalDescription() {
        return finalDescription;
    }
}
