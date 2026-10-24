package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.ListReceptionists;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;

public class ReceptionistSearchController implements ActionListener {

    private final ReceptionistRegistrationFrame frame;
    private final ListReceptionists listReceptionists;
    private final ReceptionistCrudController controller;

    public ReceptionistSearchController(
            ReceptionistRegistrationFrame frame,
            ListReceptionists listReceptionists,
            ReceptionistCrudController controller) {
        this.frame = frame;
        this.listReceptionists = listReceptionists;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cpf = frame.getCpfField().getText();

        List<Receptionist> receptionists = listReceptionists.list();

        Optional<Receptionist> found = receptionists.stream()
                .filter(receptionist -> cpf.equals(receptionist.getCpf()))
                .findFirst();

        if (found.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Receptionist not found");
            return;
        }

        Receptionist receptionist = found.get();
        JOptionPane.showMessageDialog(frame, "Receptionist found");
        controller.setReceptionist(receptionist);
        frame.getNameField().setText(receptionist.getName());
        frame.getPhoneField().setText(receptionist.getPhone());
        frame.getWorkHoursField().setText(String.valueOf(receptionist.getWorkHours()));
        frame.getOvertimeHoursField().setText(String.valueOf(receptionist.getOvertimeHours()));
        frame.getSalaryField().setText(String.valueOf(receptionist.getSalary()));
        frame.getLaborCardNumberField().setText(String.valueOf(receptionist.getLaborCardNumber()));
        frame.getMaritalStatusComboBox().setSelectedItem(receptionist.getMaritalStatus());
    }
}
