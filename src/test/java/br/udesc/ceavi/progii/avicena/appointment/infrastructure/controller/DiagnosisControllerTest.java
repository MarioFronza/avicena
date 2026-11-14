package br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.InvalidDiagnosisDataException;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteDiagnosis;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterDiagnosis;
import br.udesc.ceavi.progii.avicena.support.InMemoryDiagnosisRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DiagnosisControllerTest {

    private final InMemoryDiagnosisRepository repository = new InMemoryDiagnosisRepository();
    private final DiagnosisController controller =
            new DiagnosisController(new RegisterDiagnosis(repository), new DeleteDiagnosis(repository));

    @Test
    void registerSavesEveryFieldOfTheForm() {
        Diagnosis diagnosis = controller.register(form(1L, Map.of()));

        assertEquals(1L, diagnosis.getAppointmentId());
        assertEquals(12.8f, diagnosis.getBloodPressure());
        assertEquals(new BigDecimal("37"), diagnosis.getTemperature());
        assertEquals(180, diagnosis.getHeight());
        assertEquals(80, diagnosis.getWeight());
        assertEquals("Notes", diagnosis.getNotes());
        assertEquals("Flu", diagnosis.getDisease());
        assertEquals("Rest", diagnosis.getMedications());
        assertEquals("Exam notes", diagnosis.getExamNotes());
        assertEquals("Final", diagnosis.getFinalDescription());
    }

    @Test
    void registerRejectsADiagnosisWithNoAppointmentSelected() {
        assertThrows(InvalidDiagnosisDataException.class, () -> controller.register(form(null, Map.of())));
    }

    @ParameterizedTest
    @CsvSource({
        "bloodPressure, abc",
        "bloodPressure, ''",
        "bloodPressure, NaN",
        "temperature, abc",
        "temperature, ''",
        "temperature, '37,5'",
        "height, abc",
        "height, -1",
        "weight, abc",
        "weight, -1"
    })
    void registerRejectsAnInvalidNumber(String field, String value) {
        assertThrows(InvalidDiagnosisDataException.class, () -> controller.register(form(1L, Map.of(field, value))));
    }

    @Test
    void registerAcceptsADecimalTemperature() {
        Diagnosis diagnosis = controller.register(form(1L, Map.of("temperature", "37.5")));

        assertEquals(new BigDecimal("37.5"), diagnosis.getTemperature());
    }

    @Test
    void deleteRemovesTheDiagnosis() {
        Diagnosis diagnosis = controller.register(form(1L, Map.of()));

        controller.delete(diagnosis);

        assertTrue(repository.findAll().isEmpty());
    }

    private static DiagnosisController.Form form(Long appointmentId, Map<String, String> overrides) {
        Map<String, String> numbers =
                new HashMap<>(Map.of("bloodPressure", "12.8", "temperature", "37", "height", "180", "weight", "80"));
        numbers.putAll(overrides);
        return new DiagnosisController.Form(
                appointmentId,
                numbers.get("bloodPressure"),
                numbers.get("temperature"),
                numbers.get("height"),
                numbers.get("weight"),
                "Notes",
                "Flu",
                "Rest",
                "Exam notes",
                "Final");
    }
}
