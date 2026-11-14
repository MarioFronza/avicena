package br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.Database;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class DoctorJpaRepositoryTest {

    @Test
    void savePersistsADoctorThatIsRetrievableAfterward() {
        Doctor doctor = new Doctor(
                "Joao Test", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "12345", "Cardiology");
        DoctorJpaRepository repository = new DoctorJpaRepository();

        Doctor saved = repository.save(doctor);

        assertNotNull(saved.getId());
        Optional<Doctor> found = repository.findAll().stream()
                .filter(d -> d.getCpf().equals("12345678900"))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals("Joao Test", found.get().getName());
        assertEquals("12345", found.get().getCrm());
        assertEquals("Cardiology", found.get().getSpecialty());
    }

    @Test
    void savePersistsADoctorWithAMaritalStatusThatRoundTripsCorrectly() {
        Doctor doctor = new Doctor(
                "Married Test", "33333333333", "48966660000", null, MaritalStatus.MARRIED, "99999", "Neurology");
        DoctorJpaRepository repository = new DoctorJpaRepository();

        repository.save(doctor);

        Optional<Doctor> found = repository.findAll().stream()
                .filter(d -> d.getCpf().equals("33333333333"))
                .findFirst();
        assertTrue(found.isPresent());
        assertEquals(MaritalStatus.MARRIED, found.get().getMaritalStatus());
    }

    @Test
    void reusesTheSameSpecialtyRowWhenTwoDoctorsShareASpecialtyName() {
        Doctor first = new Doctor(
                "First Test", "44444444444", "48911110000", null, MaritalStatus.SINGLE, "11111", "Dermatology");
        Doctor second = new Doctor(
                "Second Test", "55555555555", "48922220000", null, MaritalStatus.SINGLE, "22222", "Dermatology");
        DoctorJpaRepository repository = new DoctorJpaRepository();

        repository.save(first);
        repository.save(second);

        assertEquals(1, Database.count("SELECT COUNT(*) FROM specialties WHERE name = 'Dermatology'"));
    }

    @Test
    void deleteRemovesAPreviouslyPersistedDoctor() {
        Doctor doctor = new Doctor(
                "Maria Test", "98765432100", "48988880000", null, MaritalStatus.SINGLE, "54321", "Pediatrics");
        DoctorJpaRepository repository = new DoctorJpaRepository();
        Doctor saved = repository.save(doctor);

        repository.delete(saved);

        boolean stillPresent =
                repository.findAll().stream().anyMatch(d -> d.getCpf().equals("98765432100"));
        assertFalse(stillPresent);
    }
}
