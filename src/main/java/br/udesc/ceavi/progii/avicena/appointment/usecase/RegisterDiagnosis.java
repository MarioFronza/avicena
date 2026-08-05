package br.udesc.ceavi.progii.avicena.appointment.usecase;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.DiagnosisRepository;

public class RegisterDiagnosis {

    private final DiagnosisRepository repository;

    public RegisterDiagnosis(DiagnosisRepository repository) {
        this.repository = repository;
    }

    public Diagnosis register(Diagnosis diagnosis) {
        return repository.save(diagnosis);
    }
}
