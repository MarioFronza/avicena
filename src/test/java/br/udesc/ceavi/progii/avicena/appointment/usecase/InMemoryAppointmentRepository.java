package br.udesc.ceavi.progii.avicena.appointment.usecase;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.AppointmentRepository;
import java.util.ArrayList;
import java.util.List;

class InMemoryAppointmentRepository implements AppointmentRepository {

    private final List<Appointment> appointments = new ArrayList<>();

    @Override
    public Appointment save(Appointment appointment) {
        appointments.add(appointment);
        return appointment;
    }

    @Override
    public List<Appointment> findAll() {
        return appointments;
    }

    @Override
    public void delete(Appointment appointment) {
        appointments.remove(appointment);
    }
}
