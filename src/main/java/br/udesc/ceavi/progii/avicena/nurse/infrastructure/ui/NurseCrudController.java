package br.udesc.ceavi.progii.avicena.nurse.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.nurse.domain.InvalidNurseDataException;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence.NurseJpaRepository;
import br.udesc.ceavi.progii.avicena.nurse.usecase.DeleteNurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.RegisterNurse;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.AddressEntryController;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class NurseCrudController {

    private static NurseCrudController instance;

    private Nurse nurse;
    private final FrameCRUD frame;
    private final RegisterNurse registerNurse;
    private final DeleteNurse deleteNurse;

    public NurseCrudController(FrameCRUD frame) {
        this.frame = frame;
        NurseJpaRepository repository = new NurseJpaRepository();
        this.registerNurse = new RegisterNurse(repository);
        this.deleteNurse = new DeleteNurse(repository);

        addCrudListeners();
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public static NurseCrudController getInstance(FrameCRUD frame) {
        if (instance == null) {
            instance = new NurseCrudController(frame);
        }
        return instance;
    }

    public static NurseCrudController getInstance() {
        return instance;
    }

    private void addCrudListeners() {
        JButton button;

        button = frame.getPanelBotoesCRUD().getBtCancelar();
        button.addActionListener(new CancelActionListener());
        button = frame.getPanelBotoesCRUD().getBtNovo();
        button.addActionListener(new RegisterActionListener());
        button = frame.getPanelBotoesCRUD().getBtExcluir();
        button.addActionListener(new DeleteActionListener());
        button = frame.getPanelBotoesCRUD().getBtGravar();
        button.addActionListener(new RegisterActionListener());
    }

    private Address currentAddress() {
        AddressEntryController addressController = AddressEntryController.getInstance();
        return addressController == null ? null : addressController.getAddress();
    }

    private class CancelActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(
                    frame,
                    "Do you really want to close this window?",
                    "Confirm Close",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                frame.dispose();
                frame.limparCampos();
            }
        }
    }

    private class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(
                    frame,
                    "Do you want to delete this Nurse?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                deleteNurse.delete(nurse);
                frame.limparCampos();
            }
        }
    }

    private class RegisterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            NurseRegistrationFrame registrationFrame = NurseRegistrationFrame.getInstance();
            String name = registrationFrame.getNameField().getText();
            String cpf = registrationFrame.getCpfField().getText();
            String phone = registrationFrame.getPhoneField().getText();
            String formation = registrationFrame.getFormationField().getText();
            int hoursCompleted =
                    Integer.parseInt(registrationFrame.getHoursCompletedField().getText());
            MaritalStatus maritalStatus =
                    (MaritalStatus) registrationFrame.getMaritalStatusComboBox().getSelectedItem();

            try {
                nurse = registerNurse.register(
                        new Nurse(name, cpf, phone, currentAddress(), maritalStatus, formation, hoursCompleted));
                JOptionPane.showMessageDialog(frame, "Nurse saved");
            } catch (InvalidNurseDataException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }

            frame.limparCampos();
        }
    }
}
