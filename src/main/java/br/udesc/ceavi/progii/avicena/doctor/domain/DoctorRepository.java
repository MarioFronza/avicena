package br.udesc.ceavi.progii.avicena.doctor.domain;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository {

    Doctor save(Doctor doctor);

    List<Doctor> findAll();

    Optional<Doctor> findById(Long id);

    Optional<Doctor> findByCpf(String cpf);

    void delete(Doctor doctor);
}
