package br.udesc.ceavi.progii.avicena.nurse.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.ListNurses;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;

public class NurseSearchController implements ActionListener {

    private final NurseRegistrationFrame frame;
    private final ListNurses listNurses;
    private final NurseCrudController controller;

    public NurseSearchController(NurseRegistrationFrame frame, ListNurses listNurses, NurseCrudController controller) {
        this.frame = frame;
        this.listNurses = listNurses;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cpf = frame.getCpfField().getText();

        List<Nurse> nurses = listNurses.list();

        Optional<Nurse> found =
                nurses.stream().filter(nurse -> cpf.equals(nurse.getCpf())).findFirst();

        if (found.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nurse not found");
            return;
        }

        Nurse nurse = found.get();
        JOptionPane.showMessageDialog(frame, "Nurse found");
        controller.setNurse(nurse);
        frame.getNameField().setText(nurse.getName());
        frame.getPhoneField().setText(nurse.getPhone());
        frame.getFormationField().setText(nurse.getFormation());
        frame.getHoursCompletedField().setText(String.valueOf(nurse.getHoursCompleted()));
        frame.getMaritalStatusComboBox().setSelectedItem(nurse.getMaritalStatus());
    }
}
