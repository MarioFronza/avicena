package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientJpaRepository;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiagnosisJpaRepositoryTest {

    @Test
    void savePersistsADiagnosisThatCascadesTheLinkedFinalDiagnosis() {
        Appointment appointment = anAppointment();
        Diagnosis diagnosis = new Diagnosis(
                appointment.getId(), 120f, 37, 170, 70, "Default", "Flu", "Rest", "Default exam", "Default");
        DiagnosisJpaRepository repository = new DiagnosisJpaRepository();

        Diagnosis saved = repository.save(diagnosis);

        assertNotNull(saved.getId());
        assertEquals("Flu", findEntityById(saved.getId()).getFinalDiagnosis().getDisease());
    }

    @Test
    void deleteRemovesAPreviouslyPersistedDiagnosis() {
        Appointment appointment = anAppointment();
        Diagnosis diagnosis = new Diagnosis(
                appointment.getId(), 110f, 36, 165, 60, "Default", "Cough", "Syrup", "Default exam", "Default");
        DiagnosisJpaRepository repository = new DiagnosisJpaRepository();
        Diagnosis saved = repository.save(diagnosis);

        repository.delete(saved);

        assertFalse(existsById(saved.getId()));
    }

    private Appointment anAppointment() {
        Patient patient = new PatientJpaRepository()
                .save(new Patient("Diagnosis Patient", "55555555555", "480000004", null, MaritalStatus.SINGLE));
        return new AppointmentJpaRepository()
                .save(new Appointment(
                        LocalDate.of(2026, 8, 5),
                        LocalTime.of(16, 0),
                        "Fever",
                        patient.getId(),
                        null,
                        null,
                        UrgencyStatus.NOT_URGENT));
    }

    private DiagnosisEntity findEntityById(Long id) {
        EntityManager entityManager = PersistenceConfig.entityManagerFactory().createEntityManager();
        try {
            return entityManager.find(DiagnosisEntity.class, id);
        } finally {
            entityManager.close();
        }
    }

    private boolean existsById(Long id) {
        return findEntityById(id) != null;
    }

    @Test
    void findByIdReturnsEveryFieldOfTheSavedDiagnosis() {
        DiagnosisJpaRepository repository = new DiagnosisJpaRepository();
        Diagnosis saved = repository.save(new Diagnosis(
                anAppointment().getId(), 12.5f, 38, 180, 80, "Notes", "Flu", "Rest", "Exam notes", "Final"));

        Diagnosis found = repository.findById(saved.getId()).orElseThrow();
        assertEquals(12.5f, found.getBloodPressure());
        assertEquals(38, found.getTemperature());
        assertEquals(180, found.getHeight());
        assertEquals(80, found.getWeight());
        assertEquals("Notes", found.getNotes());
        assertEquals("Flu", found.getDisease());
        assertEquals("Rest", found.getMedications());
        assertEquals("Exam notes", found.getExamNotes());
        assertEquals("Final", found.getFinalDescription());
    }

    @Test
    void findByIdIsEmptyForAnUnknownId() {
        assertTrue(new DiagnosisJpaRepository().findById(999_999L).isEmpty());
    }

    @Test
    void findAllReturnsSavedDiagnosesOrderedById() {
        DiagnosisJpaRepository repository = new DiagnosisJpaRepository();
        Long appointmentId = anAppointment().getId();
        Diagnosis first =
                repository.save(new Diagnosis(appointmentId, 120f, 37, 170, 70, null, "Flu", null, null, null));
        Diagnosis second =
                repository.save(new Diagnosis(appointmentId, 110f, 36, 165, 60, null, "Cough", null, null, null));

        assertEquals(
                List.of(first.getId(), second.getId()),
                repository.findAll().stream().map(Diagnosis::getId).toList());
    }

    @Test
    void deleteOfAMissingDiagnosisIsANoOp() {
        Diagnosis missing = new Diagnosis(999_999L, 1L, 120f, 37, 170, 70, null, null, null, null, null);

        assertDoesNotThrow(() -> new DiagnosisJpaRepository().delete(missing));
    }
}
