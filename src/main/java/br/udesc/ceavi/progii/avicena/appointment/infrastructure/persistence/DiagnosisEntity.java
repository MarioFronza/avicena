package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.AuditedEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "diagnoses")
public class DiagnosisEntity extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diagnoses_seq")
    @SequenceGenerator(name = "diagnoses_seq", sequenceName = "diagnoses_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;

    @Column(name = "blood_pressure")
    private float bloodPressure;

    @Column(name = "temperature")
    private BigDecimal temperature;

    @Column(name = "height")
    private int height;

    @Column(name = "weight")
    private int weight;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private AppointmentEntity appointment;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "final_diagnosis_id")
    private FinalDiagnosisEntity finalDiagnosis;

    protected DiagnosisEntity() {}

    public DiagnosisEntity(
            Long id,
            float bloodPressure,
            BigDecimal temperature,
            int height,
            int weight,
            String notes,
            AppointmentEntity appointment,
            FinalDiagnosisEntity finalDiagnosis) {
        this.id = id;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
        this.height = height;
        this.weight = weight;
        this.notes = notes;
        this.appointment = appointment;
        this.finalDiagnosis = finalDiagnosis;
    }

    public void update(
            float bloodPressure,
            BigDecimal temperature,
            int height,
            int weight,
            String notes,
            AppointmentEntity appointment) {
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
        this.height = height;
        this.weight = weight;
        this.notes = notes;
        this.appointment = appointment;
    }

    public Long getId() {
        return id;
    }

    public float getBloodPressure() {
        return bloodPressure;
    }

    public BigDecimal getTemperature() {
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

    public AppointmentEntity getAppointment() {
        return appointment;
    }

    public FinalDiagnosisEntity getFinalDiagnosis() {
        return finalDiagnosis;
    }
}
