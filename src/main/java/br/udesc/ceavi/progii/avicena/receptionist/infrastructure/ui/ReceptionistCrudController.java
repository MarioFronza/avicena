package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.receptionist.domain.InvalidReceptionistDataException;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.DeleteReceptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.RegisterReceptionist;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ReceptionistCrudController {

    private Receptionist receptionist;
    private Address address;
    private final ReceptionistRegistrationFrame frame;
    private final RegisterReceptionist registerReceptionist;
    private final DeleteReceptionist deleteReceptionist;

    public ReceptionistCrudController(
            ReceptionistRegistrationFrame frame,
            RegisterReceptionist registerReceptionist,
            DeleteReceptionist deleteReceptionist) {
        this.frame = frame;
        this.registerReceptionist = registerReceptionist;
        this.deleteReceptionist = deleteReceptionist;
        addCrudListeners();
    }

    public void useAddress(Address address) {
        this.address = address;
    }

    public void delete() {
        deleteReceptionist.delete(receptionist);
    }

    public void register() {
        String name = frame.getNameField().getText();
        String cpf = frame.getCpfField().getText();
        String phone = frame.getPhoneField().getText();
        int workHours = Integer.parseInt(frame.getWorkHoursField().getText());
        int overtimeHours = Integer.parseInt(frame.getOvertimeHoursField().getText());
        float salary = Float.parseFloat(frame.getSalaryField().getText());
        int laborCardNumber = Integer.parseInt(frame.getLaborCardNumberField().getText());
        MaritalStatus maritalStatus =
                (MaritalStatus) frame.getMaritalStatusComboBox().getSelectedItem();
        receptionist = registerReceptionist.register(new Receptionist(
                name, cpf, phone, address, maritalStatus, workHours, salary, overtimeHours, laborCardNumber));
    }

    public void setReceptionist(Receptionist receptionist) {
        this.receptionist = receptionist;
    }

    public Receptionist getReceptionist() {
        return receptionist;
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
                    "Do you want to delete this Receptionist?",
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
                JOptionPane.showMessageDialog(frame, "Receptionist saved");
            } catch (InvalidReceptionistDataException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }

            frame.limparCampos();
        }
    }
}
