package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.InvalidDiagnosisDataException;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteDiagnosis;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterDiagnosis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiagnosisControllerTest {

    private InMemoryDiagnosisRepository repo;
    private DiagnosisController ctrl;

    @BeforeEach
    void setUp() {
        repo = new InMemoryDiagnosisRepository();
        ctrl = new DiagnosisController(new RegisterDiagnosis(repo), new DeleteDiagnosis(repo));
    }

    @Test
    void registerSavesTheDiagnosisForTheSelectedAppointment() {
        Diagnosis diagnosis = ctrl.register(form(1L));

        assertEquals(diagnosis, repo.findAll().get(0));
        assertEquals(1L, diagnosis.getAppointmentId());
        assertEquals(12.8f, diagnosis.getBloodPressure());
        assertEquals("Flu", diagnosis.getDisease());
    }

    @Test
    void registerRejectsADiagnosisWithNoAppointmentSelected() {
        assertThrows(InvalidDiagnosisDataException.class, () -> ctrl.register(form(null)));
    }

    @Test
    void deleteRemovesTheDiagnosis() {
        Diagnosis diagnosis = ctrl.register(form(1L));

        ctrl.delete(diagnosis);

        assertTrue(repo.findAll().isEmpty());
    }

    private static DiagnosisController.Form form(Long appointmentId) {
        return new DiagnosisController.Form(appointmentId, "12.8", "37", "180", "80", "Flu", "Rest");
    }
}
