package br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.InvalidDiagnosisDataException;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteDiagnosis;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterDiagnosis;
import br.udesc.ceavi.progii.avicena.support.InMemoryDiagnosisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiagnosisControllerTest {

    private InMemoryDiagnosisRepository repository;
    private DiagnosisController controller;

    @BeforeEach
    void setUp() {
        repository = new InMemoryDiagnosisRepository();
        controller = new DiagnosisController(new RegisterDiagnosis(repository), new DeleteDiagnosis(repository));
    }

    @Test
    void registerSavesTheDiagnosisForTheSelectedAppointment() {
        Diagnosis diagnosis = controller.register(form(1L));

        assertEquals(diagnosis, repository.findAll().get(0));
        assertEquals(1L, diagnosis.getAppointmentId());
        assertEquals(12.8f, diagnosis.getBloodPressure());
        assertEquals("Flu", diagnosis.getDisease());
    }

    @Test
    void registerRejectsADiagnosisWithNoAppointmentSelected() {
        assertThrows(InvalidDiagnosisDataException.class, () -> controller.register(form(null)));
    }

    @Test
    void deleteRemovesTheDiagnosis() {
        Diagnosis diagnosis = controller.register(form(1L));

        controller.delete(diagnosis);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void registerRejectsNonNumericBloodPressure() {
        assertThrows(
                InvalidDiagnosisDataException.class, () -> controller.register(form(1L, "abc", "37", "180", "80")));
    }

    @Test
    void registerRejectsNonNumericTemperature() {
        assertThrows(InvalidDiagnosisDataException.class, () -> controller.register(form(1L, "12.8", "", "180", "80")));
    }

    @Test
    void registerRejectsNonNumericHeight() {
        assertThrows(
                InvalidDiagnosisDataException.class, () -> controller.register(form(1L, "12.8", "37", "abc", "80")));
    }

    @Test
    void registerRejectsNonNumericWeight() {
        assertThrows(
                InvalidDiagnosisDataException.class, () -> controller.register(form(1L, "12.8", "37", "180", "abc")));
    }

    private static DiagnosisController.Form form(Long appointmentId) {
        return form(appointmentId, "12.8", "37", "180", "80");
    }

    private static DiagnosisController.Form form(
            Long appointmentId, String bloodPressure, String temperature, String height, String weight) {
        return new DiagnosisController.Form(appointmentId, bloodPressure, temperature, height, weight, "Flu", "Rest");
    }
}
