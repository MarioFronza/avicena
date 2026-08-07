package br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class DoctorJpaRepositoryTest {

    @Test
    void savePersistsADoctorThatIsRetrievableAfterward() {
        Doctor doctor = new Doctor(
                "Joao Teste", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "12345", "Cardiology");
        DoctorJpaRepository repository = new DoctorJpaRepository();

        Doctor saved = repository.save(doctor);

        assertNotNull(saved.getId());
        Optional<Doctor> found = repository.findAll().stream()
                .filter(d -> d.getCpf().equals("12345678900"))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals("Joao Teste", found.get().getName());
        assertEquals("12345", found.get().getCrm());
        assertEquals("Cardiology", found.get().getSpecialty());
    }

    @Test
    void savePersistsADoctorWithAMaritalStatusThatRoundTripsCorrectly() {
        Doctor doctor = new Doctor(
                "Casado Teste", "33333333333", "48966660000", null, MaritalStatus.MARRIED, "99999", "Neurology");
        DoctorJpaRepository repository = new DoctorJpaRepository();

        repository.save(doctor);

        Optional<Doctor> found = repository.findAll().stream()
                .filter(d -> d.getCpf().equals("33333333333"))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals(MaritalStatus.MARRIED, found.get().getMaritalStatus());
    }

    @Test
    void deleteRemovesAPreviouslyPersistedDoctor() {
        Doctor doctor = new Doctor(
                "Maria Teste", "98765432100", "48988880000", null, MaritalStatus.SINGLE, "54321", "Pediatrics");
        DoctorJpaRepository repository = new DoctorJpaRepository();
        Doctor saved = repository.save(doctor);

        repository.delete(saved);

        boolean stillPresent =
                repository.findAll().stream().anyMatch(d -> d.getCpf().equals("98765432100"));
        assertFalse(stillPresent);
    }
}
