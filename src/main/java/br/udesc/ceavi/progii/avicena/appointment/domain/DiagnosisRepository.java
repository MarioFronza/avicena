package br.udesc.ceavi.progii.avicena.appointment.domain;

public interface DiagnosisRepository {

    Diagnosis save(Diagnosis diagnosis);

    void delete(Diagnosis diagnosis);
}
