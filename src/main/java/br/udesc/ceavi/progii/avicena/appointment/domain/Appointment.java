package br.udesc.ceavi.progii.avicena.appointment.domain;

import br.udesc.ceavi.progii.avicena.shared.domain.Invariants;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String symptoms;
    private final Long patientId;
    private final Long doctorId;
    private final Long nurseId;
    private final UrgencyStatus urgencyStatus;

    public Appointment(
            LocalDate date,
            LocalTime time,
            String symptoms,
            Long patientId,
            Long doctorId,
            Long nurseId,
            UrgencyStatus urgencyStatus) {
        this(null, date, time, symptoms, patientId, doctorId, nurseId, urgencyStatus);
    }

    public Appointment(
            Long id,
            LocalDate date,
            LocalTime time,
            String symptoms,
            Long patientId,
            Long doctorId,
            Long nurseId,
            UrgencyStatus urgencyStatus) {
        this.id = id;
        this.date = Invariants.notNull(date, "Appointment date", InvalidAppointmentDataException::new);
        this.time = Invariants.notNull(time, "Appointment time", InvalidAppointmentDataException::new);
        this.symptoms = symptoms;
        this.patientId = Invariants.notNull(patientId, "Appointment patient", InvalidAppointmentDataException::new);
        this.doctorId = doctorId;
        this.nurseId = nurseId;
        this.urgencyStatus =
                Invariants.notNull(urgencyStatus, "Appointment urgency status", InvalidAppointmentDataException::new);
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

    public Long getPatientId() {
        return patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Long getNurseId() {
        return nurseId;
    }

    public UrgencyStatus getUrgencyStatus() {
        return urgencyStatus;
    }
}
