package br.udesc.ceavi.progii.avicena.support;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.DiagnosisRepository;
import java.util.ArrayList;
import java.util.List;

public class InMemoryDiagnosisRepository implements DiagnosisRepository {

    private final List<Diagnosis> diagnoses = new ArrayList<>();

    @Override
    public Diagnosis save(Diagnosis diagnosis) {
        diagnoses.add(diagnosis);
        return diagnosis;
    }

    @Override
    public void delete(Diagnosis diagnosis) {
        diagnoses.remove(diagnosis);
    }

    public List<Diagnosis> findAll() {
        return diagnoses;
    }
}
