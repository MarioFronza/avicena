package br.udesc.ceavi.progii.avicena.nurse.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.udesc.ceavi.progii.avicena.nurse.usecase.DeleteNurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.RegisterNurse;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import java.awt.Dimension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NurseCrudControllerTest {

    private InMemoryNurseRepository repo;
    private NurseRegistrationFrame frame;
    private NurseCrudController ctrl;

    @BeforeEach
    void setUp() {
        repo = new InMemoryNurseRepository();
        frame = new NurseRegistrationFrame("Nurse Registration", new Dimension(360, 300));
        ctrl = new NurseCrudController(frame, new RegisterNurse(repo), new DeleteNurse(repo));
    }

    @Test
    void registerSavesNurseFromFormFields() {
        frame.getNameField().setText("Nurse Test");
        frame.getCpfField().setText("12345678900");
        frame.getPhoneField().setText("48999990000");
        frame.getFormationField().setText("Nursing");
        frame.getHoursCompletedField().setText("40");
        frame.getMaritalStatusComboBox().setSelectedItem(MaritalStatus.SINGLE);

        ctrl.register();

        assertEquals(40, repo.findAll().get(0).getHoursCompleted());
    }
}
