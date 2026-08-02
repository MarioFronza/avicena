package br.udesc.ceavi.progii.avicena.patient.usecase;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.domain.PatientRepository;

public class DeletePatient {

    private final PatientRepository repository;

    public DeletePatient(PatientRepository repository) {
        this.repository = repository;
    }

    public void delete(Patient patient) {
        repository.delete(patient);
    }
}
