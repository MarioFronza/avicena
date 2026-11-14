package br.udesc.ceavi.progii.avicena.appointment.domain;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {

    Appointment save(Appointment appointment);

    List<Appointment> findAll();

    Optional<Appointment> findById(Long id);

    void delete(Appointment appointment);
}
