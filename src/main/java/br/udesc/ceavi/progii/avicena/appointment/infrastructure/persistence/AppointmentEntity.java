package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.AuditedEntity;
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
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class AppointmentEntity extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "appointment_date")
    private LocalDate date;

    @Column(name = "appointment_time")
    private LocalTime time;

    @Column(name = "symptoms")
    private String symptoms;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private NurseEntity nurse;

    @ManyToOne
    @JoinColumn(name = "urgency_status_id")
    private UrgencyStatusEntity urgencyStatus;

    protected AppointmentEntity() {}

    public AppointmentEntity(
            Long id,
            LocalDate date,
            LocalTime time,
            String symptoms,
            PatientEntity patient,
            DoctorEntity doctor,
            NurseEntity nurse,
            UrgencyStatusEntity urgencyStatus) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.symptoms = symptoms;
        this.patient = patient;
        this.doctor = doctor;
        this.nurse = nurse;
        this.urgencyStatus = urgencyStatus;
    }

    public void update(
            LocalDate date,
            LocalTime time,
            String symptoms,
            PatientEntity patient,
            DoctorEntity doctor,
            NurseEntity nurse,
            UrgencyStatusEntity urgencyStatus) {
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
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

    public UrgencyStatusEntity getUrgencyStatus() {
        return urgencyStatus;
    }
}
