package br.udesc.ceavi.progii.avicena.appointment.usecase;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.AppointmentRepository;

public class DeleteAppointment {

    private final AppointmentRepository repository;

    public DeleteAppointment(AppointmentRepository repository) {
        this.repository = repository;
    }

    public void delete(Appointment appointment) {
        repository.delete(appointment);
    }
}
