package br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.InvalidDoctorDataException;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorJpaRepository;
import br.udesc.ceavi.progii.avicena.doctor.usecase.DeleteDoctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.RegisterDoctor;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.AddressEntryController;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class DoctorCrudController {

    private static DoctorCrudController instance;

    private Doctor doctor;
    private final FrameCRUD frame;
    private final RegisterDoctor registerDoctor;
    private final DeleteDoctor deleteDoctor;

    public DoctorCrudController(FrameCRUD frame) {
        this.frame = frame;
        DoctorJpaRepository repository = new DoctorJpaRepository();
        this.registerDoctor = new RegisterDoctor(repository);
        this.deleteDoctor = new DeleteDoctor(repository);

        addCrudListeners();
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public static DoctorCrudController getInstance(FrameCRUD frame) {
        if (instance == null) {
            instance = new DoctorCrudController(frame);
        }
        return instance;
    }

    public static DoctorCrudController getInstance() {
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
                    "Do you want to delete this Doctor?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                deleteDoctor.delete(doctor);
                frame.limparCampos();
            }
        }
    }

    private class RegisterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DoctorRegistrationFrame registrationFrame = DoctorRegistrationFrame.getInstance();
            String name = registrationFrame.getNameField().getText();
            String cpf = registrationFrame.getCpfField().getText();
            String phone = registrationFrame.getPhoneField().getText();
            String crm = registrationFrame.getCrmField().getText();
            String specialty = registrationFrame.getSpecialtyField().getText();
            MaritalStatus maritalStatus =
                    (MaritalStatus) registrationFrame.getMaritalStatusComboBox().getSelectedItem();

            try {
                doctor = registerDoctor.register(
                        new Doctor(name, cpf, phone, currentAddress(), maritalStatus, crm, specialty));
                JOptionPane.showMessageDialog(frame, "Doctor saved");
            } catch (InvalidDoctorDataException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }

            frame.limparCampos();
        }
    }
}
