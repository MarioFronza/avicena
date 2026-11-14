package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.AuditedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "final_diagnoses")
public class FinalDiagnosisEntity extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "final_diagnoses_seq")
    @SequenceGenerator(name = "final_diagnoses_seq", sequenceName = "final_diagnoses_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;

    @Column(name = "disease")
    private String disease;

    @Column(name = "medications")
    private String medications;

    @Column(name = "final_description")
    private String finalDescription;

    @Column(name = "exam_notes")
    private String examNotes;

    protected FinalDiagnosisEntity() {}

    public FinalDiagnosisEntity(
            Long id, String disease, String medications, String finalDescription, String examNotes) {
        this.id = id;
        this.disease = disease;
        this.medications = medications;
        this.finalDescription = finalDescription;
        this.examNotes = examNotes;
    }

    public void update(String disease, String medications, String finalDescription, String examNotes) {
        this.disease = disease;
        this.medications = medications;
        this.finalDescription = finalDescription;
        this.examNotes = examNotes;
    }

    public Long getId() {
        return id;
    }

    public String getDisease() {
        return disease;
    }

    public String getMedications() {
        return medications;
    }

    public String getFinalDescription() {
        return finalDescription;
    }

    public String getExamNotes() {
        return examNotes;
    }
}
