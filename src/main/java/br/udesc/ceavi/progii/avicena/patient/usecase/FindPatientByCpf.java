package br.udesc.ceavi.progii.avicena.patient.usecase;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.domain.PatientRepository;
import java.util.Optional;

public class FindPatientByCpf {

    private final PatientRepository repository;

    public FindPatientByCpf(PatientRepository repository) {
        this.repository = repository;
    }

    public Optional<Patient> find(String cpf) {
        if (cpf == null || cpf.isBlank()) return Optional.empty();
        return repository.findByCpf(cpf.strip());
    }
}
