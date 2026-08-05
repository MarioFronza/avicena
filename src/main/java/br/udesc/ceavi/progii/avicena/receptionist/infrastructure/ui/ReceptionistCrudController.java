package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.control.listenersCRUD.ListenerCRUDEndereco;
import br.udesc.ceavi.progii.avicena.model.Endereco;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.receptionist.domain.InvalidReceptionistDataException;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.infrastructure.persistence.ReceptionistJpaRepository;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.DeleteReceptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.RegisterReceptionist;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ReceptionistCrudController {

    private static ReceptionistCrudController instance;

    private Receptionist receptionist;
    private final FrameCRUD frame;
    private final RegisterReceptionist registerReceptionist;
    private final DeleteReceptionist deleteReceptionist;

    public ReceptionistCrudController(FrameCRUD frame) {
        this.frame = frame;
        ReceptionistJpaRepository repository = new ReceptionistJpaRepository();
        this.registerReceptionist = new RegisterReceptionist(repository);
        this.deleteReceptionist = new DeleteReceptionist(repository);

        addCrudListeners();
    }

    public void setReceptionist(Receptionist receptionist) {
        this.receptionist = receptionist;
    }

    public Receptionist getReceptionist() {
        return receptionist;
    }

    public static ReceptionistCrudController getInstance(FrameCRUD frame) {
        if (instance == null) {
            instance = new ReceptionistCrudController(frame);
        }
        return instance;
    }

    public static ReceptionistCrudController getInstance() {
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
        ListenerCRUDEndereco addressController = ListenerCRUDEndereco.getInstance();
        if (addressController == null) {
            return null;
        }
        Endereco endereco = addressController.getEndereco();
        if (endereco == null) {
            return null;
        }
        return new Address(
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getCep(),
                endereco.getRua(),
                endereco.getBairro(),
                endereco.getCidade());
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
                deleteReceptionist.delete(receptionist);
                frame.limparCampos();
            }
        }
    }

    private class RegisterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ReceptionistRegistrationFrame registrationFrame = ReceptionistRegistrationFrame.getInstance();
            String name = registrationFrame.getNameField().getText();
            String cpf = registrationFrame.getCpfField().getText();
            String phone = registrationFrame.getPhoneField().getText();
            int workHours = Integer.parseInt(registrationFrame.getWorkHoursField().getText());
            int overtimeHours = Integer.parseInt(registrationFrame.getOvertimeHoursField().getText());
            float salary = Float.parseFloat(registrationFrame.getSalaryField().getText());
            int laborCardNumber = Integer.parseInt(registrationFrame.getLaborCardNumberField().getText());
            MaritalStatus maritalStatus =
                    (MaritalStatus) registrationFrame.getMaritalStatusComboBox().getSelectedItem();

            try {
                receptionist = registerReceptionist.register(new Receptionist(
                        name,
                        cpf,
                        phone,
                        currentAddress(),
                        maritalStatus,
                        workHours,
                        salary,
                        overtimeHours,
                        laborCardNumber));
                JOptionPane.showMessageDialog(frame, "Receptionist saved");
            } catch (InvalidReceptionistDataException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }

            frame.limparCampos();
        }
    }
}
