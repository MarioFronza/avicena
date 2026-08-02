package br.udesc.ceavi.progii.avicena.patient.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientJpaRepository;
import br.udesc.ceavi.progii.avicena.patient.usecase.ListPatients;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;

public class PatientSearchController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        PatientRegistrationFrame frame = PatientRegistrationFrame.getInstance();
        String cpf = frame.getCpfField().getText();

        ListPatients listPatients = new ListPatients(new PatientJpaRepository());
        List<Patient> patients = listPatients.list();

        Optional<Patient> found = patients.stream()
                .filter(patient -> cpf.equals(patient.getCpf()))
                .findFirst();

        if (found.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Patient not found");
            return;
        }

        Patient patient = found.get();
        JOptionPane.showMessageDialog(frame, "Patient found");
        PatientCrudController.getInstance().setPatient(patient);
        frame.getNameField().setText(patient.getName());
        frame.getPhoneField().setText(patient.getPhone());
        frame.getMaritalStatusComboBox().setSelectedItem(patient.getMaritalStatus());
    }
}
