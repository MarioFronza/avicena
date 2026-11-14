package br.udesc.ceavi.progii.avicena.appointment.domain;

import java.util.List;
import java.util.Optional;

public interface DiagnosisRepository {

    Diagnosis save(Diagnosis diagnosis);

    List<Diagnosis> findAll();

    Optional<Diagnosis> findById(Long id);

    void delete(Diagnosis diagnosis);
}
