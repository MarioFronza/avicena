package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorEntity;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence.NurseEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "consulta")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "codigo")
    private Long id;

    @Column(name = "data")
    private String date;

    @Column(name = "hora")
    private String time;

    @Column(name = "sintomas")
    private String symptoms;

    @ManyToOne
    @JoinColumn(name = "codigo_paciente")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "codigo_medico")
    private DoctorEntity doctor;

    @ManyToOne
    @JoinColumn(name = "codigo_enfermeiro")
    private NurseEntity nurse;

    @Column(name = "estado_paciente")
    private UrgencyStatus urgencyStatus;

    protected AppointmentEntity() {}

    public AppointmentEntity(
            Long id,
            String date,
            String time,
            String symptoms,
            PatientEntity patient,
            DoctorEntity doctor,
            NurseEntity nurse,
            UrgencyStatus urgencyStatus) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.symptoms = symptoms;
        this.patient = patient;
        this.doctor = doctor;
        this.nurse = nurse;
        this.urgencyStatus = urgencyStatus;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public NurseEntity getNurse() {
        return nurse;
    }

    public UrgencyStatus getUrgencyStatus() {
        return urgencyStatus;
    }
}
