package br.udesc.ceavi.progii.avicena.support;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.DiagnosisRepository;

public class InMemoryDiagnosisRepository extends InMemoryRepository<Diagnosis> implements DiagnosisRepository {

    @Override
    protected Long idOf(Diagnosis diagnosis) {
        return diagnosis.getId();
    }

    @Override
    protected Diagnosis withId(Diagnosis diagnosis, Long id) {
        return new Diagnosis(
                id,
                diagnosis.getAppointmentId(),
                diagnosis.getBloodPressure(),
                diagnosis.getTemperature(),
                diagnosis.getHeight(),
                diagnosis.getWeight(),
                diagnosis.getNotes(),
                diagnosis.getDisease(),
                diagnosis.getMedications(),
                diagnosis.getExamNotes(),
                diagnosis.getFinalDescription());
    }
}
