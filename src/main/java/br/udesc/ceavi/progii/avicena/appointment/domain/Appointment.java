package br.udesc.ceavi.progii.avicena.appointment.domain;

public class Appointment {

    private final Long id;
    private final String date;
    private final String time;
    private final String symptoms;
    private final Long patientId;
    private final Long doctorId;
    private final Long nurseId;
    private final UrgencyStatus urgencyStatus;

    public Appointment(
            String date,
            String time,
            String symptoms,
            Long patientId,
            Long doctorId,
            Long nurseId,
            UrgencyStatus urgencyStatus) {
        this(null, date, time, symptoms, patientId, doctorId, nurseId, urgencyStatus);
    }

    public Appointment(
            Long id,
            String date,
            String time,
            String symptoms,
            Long patientId,
            Long doctorId,
            Long nurseId,
            UrgencyStatus urgencyStatus) {
        if (patientId == null) {
            throw new InvalidAppointmentDataException("Appointment patientId must not be null");
        }
        this.id = id;
        this.date = date;
        this.time = time;
        this.symptoms = symptoms;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.nurseId = nurseId;
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
