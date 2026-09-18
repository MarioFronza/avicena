package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "diagnoses")
public class DiagnosisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "blood_pressure")
    private float bloodPressure;

    @Column(name = "temperature")
    private int temperature;

    @Column(name = "height")
    private int height;

    @Column(name = "weight")
    private int weight;

    @Column(name = "medication_history")
    private String medicationHistory;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private AppointmentEntity appointment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "final_diagnosis_id")
    private FinalDiagnosisEntity finalDiagnosis;

    protected DiagnosisEntity() {}

    public DiagnosisEntity(
            Long id,
            float bloodPressure,
            int temperature,
            int height,
            int weight,
            String medicationHistory,
            AppointmentEntity appointment,
            FinalDiagnosisEntity finalDiagnosis) {
        this.id = id;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
        this.height = height;
        this.weight = weight;
        this.medicationHistory = medicationHistory;
        this.appointment = appointment;
        this.finalDiagnosis = finalDiagnosis;
    }

    public Long getId() {
        return id;
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

    public String getMedicationHistory() {
        return medicationHistory;
    }

    public AppointmentEntity getAppointment() {
        return appointment;
    }

    public FinalDiagnosisEntity getFinalDiagnosis() {
        return finalDiagnosis;
    }
}
