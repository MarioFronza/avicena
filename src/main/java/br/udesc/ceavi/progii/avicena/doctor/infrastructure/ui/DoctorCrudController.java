package br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.InvalidDoctorDataException;
import br.udesc.ceavi.progii.avicena.doctor.usecase.DeleteDoctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.RegisterDoctor;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class DoctorCrudController {

    private Doctor doctor;
    private Address address;
    private final DoctorRegistrationFrame frame;
    private final RegisterDoctor registerDoctor;
    private final DeleteDoctor deleteDoctor;

    public DoctorCrudController(
            DoctorRegistrationFrame frame, RegisterDoctor registerDoctor, DeleteDoctor deleteDoctor) {
        this.frame = frame;
        this.registerDoctor = registerDoctor;
        this.deleteDoctor = deleteDoctor;

        addCrudListeners();
    }

    public void useAddress(Address address) {
        this.address = address;
    }

    public void delete() {
        deleteDoctor.delete(doctor);
    }

    public void register() {
        String name = frame.getNameField().getText();
        String cpf = frame.getCpfField().getText();
        String phone = frame.getPhoneField().getText();
        String crm = frame.getCrmField().getText();
        String specialty = frame.getSpecialtyField().getText();
        MaritalStatus maritalStatus =
                (MaritalStatus) frame.getMaritalStatusComboBox().getSelectedItem();
        doctor = registerDoctor.register(toDoctor(name, cpf, phone, address, maritalStatus, crm, specialty));
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
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

    static Doctor toDoctor(
            String name,
            String cpf,
            String phone,
            Address address,
            MaritalStatus maritalStatus,
            String crm,
            String specialty) {
        return new Doctor(name, cpf, phone, address, maritalStatus, crm, specialty);
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
                JOptionPane.showMessageDialog(frame, "Doctor saved");
            } catch (InvalidDoctorDataException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }

            frame.limparCampos();
        }
    }
}
