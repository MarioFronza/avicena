package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.InvalidDiagnosisDataException;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteDiagnosis;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterDiagnosis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiagnosisCrudControllerTest {

    private InMemoryDiagnosisRepository repo;
    private DiagnosisRegistrationFrame frame;
    private DiagnosisCrudController ctrl;

    @BeforeEach
    void setUp() {
        repo = new InMemoryDiagnosisRepository();
        frame = new DiagnosisRegistrationFrame();
        ctrl = new DiagnosisCrudController(frame, new RegisterDiagnosis(repo), new DeleteDiagnosis(repo));
    }

    @Test
    void registerRejectsADiagnosisWithNoAppointmentSelected() {
        frame.getBloodPressureField().setText("12.8");
        frame.getTemperatureField().setText("37");
        frame.getHeightField().setText("180");
        frame.getWeightField().setText("80");

        assertThrows(InvalidDiagnosisDataException.class, () -> ctrl.register());
    }

    @Test
    void deleteRemovesTheCurrentDiagnosis() {
        Diagnosis diagnosis = new Diagnosis(1L, 12.8f, 37, 180, 80, "notes", "Flu", "Rest", "exam", "final");
        repo.save(diagnosis);
        ctrl.setDiagnosis(diagnosis);

        ctrl.delete();

        assertTrue(repo.findAll().isEmpty());
    }
}
