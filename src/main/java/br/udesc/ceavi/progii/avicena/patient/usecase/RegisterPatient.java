package br.udesc.ceavi.progii.avicena.patient.usecase;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.domain.PatientRepository;

public class RegisterPatient {

    private final PatientRepository repository;

    public RegisterPatient(PatientRepository repository) {
        this.repository = repository;
    }

    public Patient register(Patient patient) {
        return repository.save(patient);
    }
}
