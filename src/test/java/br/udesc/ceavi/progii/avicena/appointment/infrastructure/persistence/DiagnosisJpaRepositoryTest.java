package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

class DiagnosisJpaRepositoryTest {

    @Test
    void savePersistsADiagnosisThatCascadesTheLinkedFinalDiagnosis() {
        Appointment appointment = anAppointment();
        Diagnosis diagnosis =
                new Diagnosis(appointment.getId(), 120f, 37, 170, 70, "Padrão", "Gripe", "Repouso", "Exame padrão", "Padrão");
        DiagnosisJpaRepository repository = new DiagnosisJpaRepository();

        Diagnosis saved = repository.save(diagnosis);

        assertNotNull(saved.getId());
        assertEquals("Gripe", findByCodigo(saved.getId()).getFinalDiagnosis().getDoenca());
    }

    @Test
    void deleteRemovesAPreviouslyPersistedDiagnosis() {
        Appointment appointment = anAppointment();
        Diagnosis diagnosis =
                new Diagnosis(appointment.getId(), 110f, 36, 165, 60, "Padrão", "Tosse", "Xarope", "Exame padrão", "Padrão");
        DiagnosisJpaRepository repository = new DiagnosisJpaRepository();
        Diagnosis saved = repository.save(diagnosis);

        repository.delete(saved);

        assertFalse(findByCodigoExists(saved.getId()));
    }

    private Appointment anAppointment() {
        Patient patient = new PatientJpaRepository()
                .save(new Patient("Paciente Diagnostico", "55555555555", "480000004", null, MaritalStatus.SINGLE));
        return new br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentJpaRepository()
                .save(new Appointment(
                        "05/08/2026", "16:00", "Febre", patient.getId(), null, null, UrgencyStatus.NOT_URGENT));
    }

    private DiagnosisEntity findByCodigo(Long id) {
        EntityManager entityManager =
                PersistenceConfig.createEntityManagerFactory().createEntityManager();
        try {
            return entityManager.find(DiagnosisEntity.class, id);
        } finally {
            entityManager.close();
        }
    }

    private boolean findByCodigoExists(Long id) {
        return findByCodigo(id) != null;
    }
}
