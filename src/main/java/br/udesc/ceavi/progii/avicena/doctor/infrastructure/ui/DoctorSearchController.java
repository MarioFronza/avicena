package br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.ListDoctors;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;

public class DoctorSearchController implements ActionListener {

    private final DoctorRegistrationFrame frame;
    private final ListDoctors listDoctors;
    private final DoctorCrudController controller;

    public DoctorSearchController(
            DoctorRegistrationFrame frame, ListDoctors listDoctors, DoctorCrudController controller) {
        this.frame = frame;
        this.listDoctors = listDoctors;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cpf = frame.getCpfField().getText();

        List<Doctor> doctors = listDoctors.list();

        Optional<Doctor> found =
                doctors.stream().filter(doctor -> cpf.equals(doctor.getCpf())).findFirst();

        if (found.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Doctor not found");
            return;
        }

        Doctor doctor = found.get();
        JOptionPane.showMessageDialog(frame, "Doctor found");
        controller.setDoctor(doctor);
        frame.getNameField().setText(doctor.getName());
        frame.getPhoneField().setText(doctor.getPhone());
        frame.getCrmField().setText(doctor.getCrm());
        frame.getSpecialtyField().setText(doctor.getSpecialty());
        frame.getMaritalStatusComboBox().setSelectedItem(doctor.getMaritalStatus());
    }
}
