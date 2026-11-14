package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.support.Database;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** The database enforces the domain's invariants too, so bad rows can't arrive by any other path. */
class SchemaConstraintsTest {

    @Test
    void cpfIsUniqueAcrossPeople() {
        insertPerson(1, "11111111111");

        assertRejectedBy("people_cpf_key", () -> insertPerson(2, "11111111111"));
    }

    @Test
    void crmIsUniqueAcrossDoctors() {
        insertPerson(1, "11111111111");
        insertPerson(2, "22222222222");
        Database.execute("INSERT INTO doctors (id, person_id, crm) VALUES (1, 1, 'CRM-1')");

        assertRejectedBy(
                "doctors_crm_key",
                () -> Database.execute("INSERT INTO doctors (id, person_id, crm) VALUES (2, 2, 'CRM-1')"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"patient_id", "urgency_status_id", "appointment_date", "appointment_time"})
    void appointmentRequires(String column) {
        insertPerson(1, "11111111111");
        Database.execute("INSERT INTO patients (id, person_id) VALUES (1, 1)");
        Map<String, String> values = new LinkedHashMap<>();
        values.put("patient_id", "1");
        values.put("urgency_status_id", "1");
        values.put("appointment_date", "DATE '2026-08-05'");
        values.put("appointment_time", "TIME '14:00'");
        values.put(column, "NULL");

        assertRejectedBy(
                column,
                () -> Database.execute("INSERT INTO appointments (id, " + String.join(", ", values.keySet())
                        + ") VALUES (1, " + String.join(", ", values.values()) + ")"));
    }

    @Test
    void diagnosisRequiresAnAppointment() {
        assertRejectedBy(
                "appointment_id",
                () -> Database.execute("INSERT INTO diagnoses (id, appointment_id) VALUES (1, NULL)"));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "UPDATE nurses SET training_hours = -1",
                "UPDATE receptionists SET work_hours = -1",
                "UPDATE receptionists SET overtime_hours = -1",
                "UPDATE receptionists SET salary = -1",
                "UPDATE diagnoses SET blood_pressure = -1",
                "UPDATE diagnoses SET temperature = -1",
                "UPDATE diagnoses SET height = -1",
                "UPDATE diagnoses SET weight = -1"
            })
    void numbersCannotBeNegative(String update) {
        insertPerson(1, "11111111111");
        insertPerson(2, "22222222222");
        insertPerson(3, "33333333333");
        Database.execute("INSERT INTO patients (id, person_id) VALUES (1, 3)");
        Database.execute(
                "INSERT INTO nurses (id, person_id, qualification, training_hours) VALUES (1, 1, 'Technician', 10)");
        Database.execute("INSERT INTO receptionists (id, person_id, work_hours, salary, overtime_hours,"
                + " labor_card_number) VALUES (1, 2, 40, 3000, 5, '123')");
        Database.execute("INSERT INTO appointments (id, patient_id, urgency_status_id, appointment_date,"
                + " appointment_time) VALUES (1, 1, 1, DATE '2026-08-05', TIME '14:00')");
        Database.execute("INSERT INTO diagnoses (id, appointment_id, blood_pressure, temperature, height, weight)"
                + " VALUES (1, 1, 12, 37, 170, 70)");

        assertRejectedBy("_check", () -> Database.execute(update));
    }

    @Test
    void everyForeignKeyIsIndexed() {
        List<String> unindexed = Database.strings("""
                SELECT c.conrelid::regclass || '.' || a.attname
                FROM pg_constraint c
                JOIN pg_attribute a ON a.attrelid = c.conrelid AND a.attnum = c.conkey[1]
                WHERE c.contype = 'f' AND c.connamespace = 'public'::regnamespace
                  AND NOT EXISTS (
                      SELECT 1 FROM pg_index i
                      WHERE i.indrelid = c.conrelid AND i.indkey[0] = c.conkey[1])
                ORDER BY 1
                """);

        assertEquals(List.of(), unindexed);
    }

    @Test
    void everySequenceBelongsToATable() {
        List<String> orphans = Database.strings("""
                SELECT s.sequencename FROM pg_sequences s
                WHERE s.schemaname = 'public'
                  AND NOT EXISTS (
                      SELECT 1 FROM pg_tables t
                      WHERE t.schemaname = 'public' AND t.tablename || '_seq' = s.sequencename)
                ORDER BY 1
                """);

        assertEquals(List.of(), orphans);
    }

    private static void assertRejectedBy(String constraintOrColumn, Executable statement) {
        IllegalStateException error = assertThrows(IllegalStateException.class, statement);
        assertTrue(
                error.getMessage().contains(constraintOrColumn),
                () -> "expected a violation of " + constraintOrColumn + " but got: " + error.getMessage());
    }

    private static void insertPerson(long id, String cpf) {
        Database.execute("INSERT INTO people (id, name, cpf, phone) VALUES (" + id + ", 'Person', '" + cpf
                + "', '48999990000')");
    }
}
