package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientJpaRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostgresContainerExtensionTest {

    private static final String CPF = "91234567890";

    @Test
    @Order(1)
    void insertsAPatientThatShouldNotLeakIntoTheNextTest() {
        Patient patient = new Patient("Leak Teste", CPF, "48900000000", null, MaritalStatus.SINGLE);
        Patient saved = new PatientJpaRepository().save(patient);

        assertNotNull(saved.getId());
    }

    @Test
    @Order(2)
    void doesNotSeeDataInsertedByThePreviousTest() {
        boolean stillPresent = new PatientJpaRepository()
                .findAll().stream().anyMatch(p -> p.getCpf().equals(CPF));

        assertFalse(stillPresent);
    }

    @Test
    void preservesSeededMaritalStatusRowsAcrossTruncation() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                        System.getProperty("AVICENA_DB_URL"),
                        System.getProperty("AVICENA_DB_USER"),
                        System.getProperty("AVICENA_DB_PASSWORD"));
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM marital_statuses")) {
            resultSet.next();
            assertEquals(5, resultSet.getInt(1));
        }
    }
}
