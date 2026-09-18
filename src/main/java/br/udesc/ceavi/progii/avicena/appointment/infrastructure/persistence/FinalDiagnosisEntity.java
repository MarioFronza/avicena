package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "final_diagnoses")
public class FinalDiagnosisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "disease")
    private String disease;

    @Column(name = "medications")
    private String medications;

    @Column(name = "description")
    private String description;

    @Column(name = "exam_notes")
    private String examNotes;

    protected FinalDiagnosisEntity() {}

    public FinalDiagnosisEntity(Long id, String disease, String medications, String description, String examNotes) {
        this.id = id;
        this.disease = disease;
        this.medications = medications;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public String getExamNotes() {
        return examNotes;
    }
}
