package br.udesc.ceavi.progii.avicena.appointment.usecase;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.DiagnosisRepository;

public class DeleteDiagnosis {

    private final DiagnosisRepository repository;

    public DeleteDiagnosis(DiagnosisRepository repository) {
        this.repository = repository;
    }

    public void delete(Diagnosis diagnosis) {
        repository.delete(diagnosis);
    }
}
