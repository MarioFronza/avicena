package br.udesc.ceavi.progii.avicena.appointment.usecase;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.AppointmentRepository;

public class RegisterAppointment {

    private final AppointmentRepository repository;

    public RegisterAppointment(AppointmentRepository repository) {
        this.repository = repository;
    }

    public Appointment register(Appointment appointment) {
        return repository.save(appointment);
    }
}
