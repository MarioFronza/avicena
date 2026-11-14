package br.udesc.ceavi.progii.avicena.patient.domain;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {

    Patient save(Patient patient);

    List<Patient> findAll();

    Optional<Patient> findById(Long id);

    Optional<Patient> findByCpf(String cpf);

    void delete(Patient patient);
}
