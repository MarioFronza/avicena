package br.udesc.ceavi.progii.avicena.nurse.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.nurse.domain.InvalidNurseDataException;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.DeleteNurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.RegisterNurse;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class NurseCrudController {

    private Nurse nurse;
    private Address address;
    private final NurseRegistrationFrame frame;
    private final RegisterNurse registerNurse;
    private final DeleteNurse deleteNurse;

    public NurseCrudController(NurseRegistrationFrame frame, RegisterNurse registerNurse, DeleteNurse deleteNurse) {
        this.frame = frame;
        this.registerNurse = registerNurse;
        this.deleteNurse = deleteNurse;
        addCrudListeners();
    }

    public void useAddress(Address address) {
        this.address = address;
    }

    public void delete() {
        deleteNurse.delete(nurse);
    }

    public void register() {
        String name = frame.getNameField().getText();
        String cpf = frame.getCpfField().getText();
        String phone = frame.getPhoneField().getText();
        String formation = frame.getFormationField().getText();
        int hoursCompleted = Integer.parseInt(frame.getHoursCompletedField().getText());
        MaritalStatus maritalStatus =
                (MaritalStatus) frame.getMaritalStatusComboBox().getSelectedItem();
        nurse = registerNurse.register(new Nurse(name, cpf, phone, address, maritalStatus, formation, hoursCompleted));
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Nurse getNurse() {
        return nurse;
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
                delete();
                frame.limparCampos();
            }
        }
    }

    private class RegisterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                register();
                JOptionPane.showMessageDialog(frame, "Nurse saved");
            } catch (InvalidNurseDataException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }

            frame.limparCampos();
        }
    }
}
