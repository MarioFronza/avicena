package br.udesc.ceavi.progii.avicena.patient.usecase;

import br.udesc.ceavi.progii.avicena.patient.domain.InvalidPatientDataException;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.domain.PatientRepository;
import java.util.Objects;

public class RegisterPatient {

    private final PatientRepository repository;

    public RegisterPatient(PatientRepository repository) {
        this.repository = repository;
    }

    public Patient register(Patient patient) {
        repository
                .findByCpf(patient.getCpf())
                .filter(existing -> !Objects.equals(existing.getId(), patient.getId()))
                .ifPresent(existing -> {
                    throw new InvalidPatientDataException("Patient cpf " + patient.getCpf() + " is already registered");
                });
        return repository.save(patient);
    }
}
