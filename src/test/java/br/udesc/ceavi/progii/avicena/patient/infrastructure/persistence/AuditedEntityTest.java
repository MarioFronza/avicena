package br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.control.dao.JpaTransactions;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.support.Fixtures;
import org.junit.jupiter.api.Test;

class AuditedEntityTest {

    @Test
    void savingSetsCreatedAtAndUpdatedAt() {
        Patient saved = new PatientJpaRepository().save(Fixtures.patient("10000000041"));

        PersonEntity person = personOf(saved);

        assertNotNull(person.getCreatedAt());
        assertNotNull(person.getUpdatedAt());
    }

    @Test
    void updatingMovesUpdatedAtAndKeepsCreatedAt() throws InterruptedException {
        PatientJpaRepository repository = new PatientJpaRepository();
        Patient saved = repository.save(Fixtures.patient("10000000042"));
        PersonEntity before = personOf(saved);
        Thread.sleep(20);

        repository.save(new Patient(
                saved.getId(),
                "Renamed",
                saved.getCpf(),
                saved.getPhone(),
                saved.getAddress(),
                saved.getMaritalStatus()));

        PersonEntity after = personOf(saved);
        assertEquals(before.getCreatedAt(), after.getCreatedAt());
        assertTrue(after.getUpdatedAt().isAfter(before.getUpdatedAt()));
    }

    private static PersonEntity personOf(Patient patient) {
        return JpaTransactions.read(entityManager ->
                entityManager.find(PatientEntity.class, patient.getId()).getPerson());
    }
}
