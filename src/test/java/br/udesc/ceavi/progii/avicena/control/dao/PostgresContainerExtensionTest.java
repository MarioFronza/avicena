package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientJpaRepository;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.support.Database;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PostgresContainerExtensionTest {

    @RepeatedTest(2)
    void everyTestStartsWithNoPeople() {
        assertEquals(0, Database.count("SELECT COUNT(*) FROM people"));

        new PatientJpaRepository()
                .save(new Patient("Leak Test", "91234567890", "48900000000", null, MaritalStatus.SINGLE));

        assertEquals(1, Database.count("SELECT COUNT(*) FROM people"));
    }

    @ParameterizedTest
    @CsvSource({"marital_statuses, 5", "urgency_statuses, 4", "departments, 1", "roles, 4"})
    void keepsSeededRowsAcrossTruncation(String table, int seededRows) {
        assertEquals(seededRows, Database.count("SELECT COUNT(*) FROM " + table));
    }
}
