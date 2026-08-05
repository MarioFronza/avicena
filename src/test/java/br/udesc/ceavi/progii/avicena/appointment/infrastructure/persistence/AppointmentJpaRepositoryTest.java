package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorJpaRepository;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence.NurseJpaRepository;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientJpaRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AppointmentJpaRepositoryTest {

    @Test
    void savePersistsAnAppointmentThatIsRetrievableAfterward() {
        Patient patient = new PatientJpaRepository()
                .save(new Patient("Paciente Teste", "11111111111", "480000000", null, MaritalStatus.SINGLE));
        Doctor doctor = new DoctorJpaRepository()
                .save(new Doctor(
                        "Doutor Teste",
                        "22222222222",
                        "480000001",
                        null,
                        MaritalStatus.SINGLE,
                        "CRM-1",
                        "Clinica Geral"));
        Nurse nurse = new NurseJpaRepository()
                .save(new Nurse(
                        "Enfermeira Teste", "33333333333", "480000002", null, MaritalStatus.SINGLE, "Tecnico", 1200));
        Appointment appointment = new Appointment(
                "05/08/2026",
                "14:00",
                "Febre",
                patient.getId(),
                doctor.getId(),
                nurse.getId(),
                UrgencyStatus.NOT_URGENT);
        AppointmentJpaRepository repository = new AppointmentJpaRepository();

        Appointment saved = repository.save(appointment);

        assertNotNull(saved.getId());
        Optional<Appointment> found = repository.findAll().stream()
                .filter(a -> a.getId().equals(saved.getId()))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals("Febre", found.get().getSymptoms());
        assertEquals(patient.getId(), found.get().getPatientId());
        assertEquals(doctor.getId(), found.get().getDoctorId());
        assertEquals(nurse.getId(), found.get().getNurseId());
    }

    @Test
    void deleteRemovesAPreviouslyPersistedAppointment() {
        Patient patient = new PatientJpaRepository()
                .save(new Patient("Paciente Dois", "44444444444", "480000003", null, MaritalStatus.SINGLE));
        Appointment appointment =
                new Appointment("05/08/2026", "15:00", "Tosse", patient.getId(), null, null, UrgencyStatus.NOT_URGENT);
        AppointmentJpaRepository repository = new AppointmentJpaRepository();
        Appointment saved = repository.save(appointment);

        repository.delete(saved);

        boolean stillPresent =
                repository.findAll().stream().anyMatch(a -> a.getId().equals(saved.getId()));
        assertFalse(stillPresent);
    }
}
