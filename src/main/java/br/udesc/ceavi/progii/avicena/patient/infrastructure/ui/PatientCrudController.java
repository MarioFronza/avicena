package br.udesc.ceavi.progii.avicena.patient.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.InvalidPatientDataException;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientJpaRepository;
import br.udesc.ceavi.progii.avicena.patient.usecase.DeletePatient;
import br.udesc.ceavi.progii.avicena.patient.usecase.RegisterPatient;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class PatientCrudController {

    private static PatientCrudController instance;

    private Patient patient;
    private final FrameCRUD frame;
    private final RegisterPatient registerPatient;
    private final DeletePatient deletePatient;

    public PatientCrudController(FrameCRUD frame) {
        this.frame = frame;
        PatientJpaRepository repository = new PatientJpaRepository();
        this.registerPatient = new RegisterPatient(repository);
        this.deletePatient = new DeletePatient(repository);

        addCrudListeners();
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public static PatientCrudController getInstance(FrameCRUD frame) {
        if (instance == null) {
            instance = new PatientCrudController(frame);
        }
        return instance;
    }

    public static PatientCrudController getInstance() {
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
                    "Do you want to delete this Patient?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                deletePatient.delete(patient);
                frame.limparCampos();
            }
        }
    }

    private class RegisterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            PatientRegistrationFrame registrationFrame = PatientRegistrationFrame.getInstance();
            String name = registrationFrame.getNameField().getText();
            String cpf = registrationFrame.getCpfField().getText();
            String phone = registrationFrame.getPhoneField().getText();
            MaritalStatus maritalStatus =
                    (MaritalStatus) registrationFrame.getMaritalStatusComboBox().getSelectedItem();

            try {
                patient = registerPatient.register(new Patient(name, cpf, phone, currentAddress(), maritalStatus));
                JOptionPane.showMessageDialog(frame, "Patient saved");
            } catch (InvalidPatientDataException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }

            frame.limparCampos();
        }
    }
}
