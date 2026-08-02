package br.udesc.ceavi.progii.avicena.patient.domain;

import java.util.List;

public interface PatientRepository {

    Patient save(Patient patient);

    List<Patient> findAll();

    void delete(Patient patient);
}
