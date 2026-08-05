package br.udesc.ceavi.progii.avicena.appointment.domain;

import java.util.List;

public interface AppointmentRepository {

    Appointment save(Appointment appointment);

    List<Appointment> findAll();

    void delete(Appointment appointment);
}
