package br.udesc.ceavi.progii.avicena.support;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.AppointmentRepository;

public class InMemoryAppointmentRepository extends InMemoryRepository<Appointment> implements AppointmentRepository {

    @Override
    protected Long idOf(Appointment appointment) {
        return appointment.getId();
    }

    @Override
    protected Appointment withId(Appointment appointment, Long id) {
        return new Appointment(
                id,
                appointment.getDate(),
                appointment.getTime(),
                appointment.getSymptoms(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getNurseId(),
                appointment.getUrgencyStatus());
    }
}
