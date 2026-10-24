package br.udesc.ceavi.progii.avicena.patient.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.usecase.DeletePatient;
import br.udesc.ceavi.progii.avicena.patient.usecase.RegisterPatient;
import java.awt.Dimension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientCrudControllerTest {

    private InMemoryPatientRepository repo;
    private PatientRegistrationFrame frame;
    private PatientCrudController ctrl;

    @BeforeEach
    void setUp() {
        repo = new InMemoryPatientRepository();
        frame = new PatientRegistrationFrame("Patient Registration", new Dimension(360, 240));
        ctrl = new PatientCrudController(frame, new RegisterPatient(repo), new DeletePatient(repo));
    }

    @Test
    void deleteRemovesTheCurrentPatient() {
        Patient patient = new Patient("Patient Test", "12345678900", "48999990000", null, MaritalStatus.SINGLE);
        repo.save(patient);
        ctrl.setPatient(patient);

        ctrl.delete();

        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void registerSavesPatientFromFormFields() {
        frame.getNameField().setText("Patient Test");
        frame.getCpfField().setText("12345678900");
        frame.getPhoneField().setText("48999990000");
        frame.getMaritalStatusComboBox().setSelectedItem(MaritalStatus.SINGLE);

        ctrl.register();

        assertEquals("12345678900", repo.findAll().get(0).getCpf());
    }
}
