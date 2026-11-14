package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.AddressEntity;
import br.udesc.ceavi.progii.avicena.support.Database;
import java.time.Duration;
import org.junit.jupiter.api.Test;

class JpaTransactionsTest {

    @Test
    void writeCommitsTheWorkAndReturnsItsResult() {
        String street = JpaTransactions.write(entityManager -> {
            entityManager.persist(address());
            return "Main St";
        });

        assertEquals("Main St", street);
        assertEquals(1, Database.count("SELECT COUNT(*) FROM addresses"));
    }

    @Test
    void writeRollsBackWhenTheWorkThrows() {
        assertThrows(
                IllegalStateException.class,
                () -> JpaTransactions.write(entityManager -> {
                    entityManager.persist(address());
                    entityManager.flush();
                    throw new IllegalStateException("boom");
                }));

        assertEquals(0, Database.count("SELECT COUNT(*) FROM addresses"));
    }

    @Test
    void writeReleasesTheConnectionAfterAFailure() {
        for (int attempt = 0; attempt < 25; attempt++) {
            assertThrows(
                    IllegalStateException.class,
                    () -> JpaTransactions.write(entityManager -> {
                        entityManager.persist(address());
                        entityManager.flush();
                        throw new IllegalStateException("boom");
                    }));
        }

        assertTimeoutPreemptively(
                Duration.ofSeconds(20),
                () -> JpaTransactions.write(entityManager -> {
                    entityManager.persist(address());
                    return null;
                }));
        assertEquals(1, Database.count("SELECT COUNT(*) FROM addresses"));
    }

    @Test
    void readReturnsTheWorkResult() {
        long count = JpaTransactions.read(entityManager -> entityManager
                .createQuery("SELECT COUNT(a) FROM AddressEntity a", Long.class)
                .getSingleResult());

        assertEquals(0L, count);
    }

    private static AddressEntity address() {
        return new AddressEntity(null, 100, "Apt 2", "88000000", "Main St", "Downtown", "Florianopolis");
    }
}
