package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.DeleteReceptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.RegisterReceptionist;
import java.awt.Dimension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReceptionistCrudControllerTest {

    private InMemoryReceptionistRepository repo;
    private ReceptionistRegistrationFrame frame;
    private ReceptionistCrudController ctrl;

    @BeforeEach
    void setUp() {
        repo = new InMemoryReceptionistRepository();
        frame = new ReceptionistRegistrationFrame("Receptionist Registration", new Dimension(430, 330));
        ctrl = new ReceptionistCrudController(frame, new RegisterReceptionist(repo), new DeleteReceptionist(repo));
    }

    @Test
    void registerSavesReceptionistFromFormFields() {
        frame.getNameField().setText("Receptionist Test");
        frame.getCpfField().setText("12345678900");
        frame.getPhoneField().setText("48999990000");
        frame.getWorkHoursField().setText("8");
        frame.getOvertimeHoursField().setText("2");
        frame.getSalaryField().setText("1500.5");
        frame.getLaborCardNumberField().setText("123");
        frame.getMaritalStatusComboBox().setSelectedItem(MaritalStatus.SINGLE);

        ctrl.register();

        assertEquals(1500.5f, repo.findAll().get(0).getSalary());
    }

    @Test
    void deleteRemovesTheCurrentReceptionist() {
        Receptionist receptionist = new Receptionist(
                "Receptionist Test", "12345678900", "48999990000", null, MaritalStatus.SINGLE, 8, 1500.5f, 2, 123);
        repo.save(receptionist);
        ctrl.setReceptionist(receptionist);

        ctrl.delete();

        assertTrue(repo.findAll().isEmpty());
    }
}
