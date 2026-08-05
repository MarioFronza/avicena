package br.udesc.ceavi.progii.avicena.doctor.domain;

import java.util.List;

public interface DoctorRepository {

    Doctor save(Doctor doctor);

    List<Doctor> findAll();

    void delete(Doctor doctor);
}
