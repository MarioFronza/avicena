package br.udesc.ceavi.progii.avicena.patient.usecase;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.domain.PatientRepository;
import java.util.List;

public class ListPatients {

    private final PatientRepository repository;

    public ListPatients(PatientRepository repository) {
        this.repository = repository;
    }

    public List<Patient> list() {
        return repository.findAll();
    }
}
