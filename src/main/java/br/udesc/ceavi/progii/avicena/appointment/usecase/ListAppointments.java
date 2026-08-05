package br.udesc.ceavi.progii.avicena.appointment.usecase;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.AppointmentRepository;
import java.util.List;

public class ListAppointments {

    private final AppointmentRepository repository;

    public ListAppointments(AppointmentRepository repository) {
        this.repository = repository;
    }

    public List<Appointment> list() {
        return repository.findAll();
    }
}
