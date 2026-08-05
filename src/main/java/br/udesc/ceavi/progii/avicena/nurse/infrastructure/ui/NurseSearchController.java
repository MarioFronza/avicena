package br.udesc.ceavi.progii.avicena.nurse.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence.NurseJpaRepository;
import br.udesc.ceavi.progii.avicena.nurse.usecase.ListNurses;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;

public class NurseSearchController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        NurseRegistrationFrame frame = NurseRegistrationFrame.getInstance();
        String cpf = frame.getCpfField().getText();

        ListNurses listNurses = new ListNurses(new NurseJpaRepository());
        List<Nurse> nurses = listNurses.list();

        Optional<Nurse> found =
                nurses.stream().filter(nurse -> cpf.equals(nurse.getCpf())).findFirst();

        if (found.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nurse not found");
            return;
        }

        Nurse nurse = found.get();
        JOptionPane.showMessageDialog(frame, "Nurse found");
        NurseCrudController.getInstance().setNurse(nurse);
        frame.getNameField().setText(nurse.getName());
        frame.getPhoneField().setText(nurse.getPhone());
        frame.getFormationField().setText(nurse.getFormation());
        frame.getHoursCompletedField().setText(String.valueOf(nurse.getHoursCompleted()));
        frame.getMaritalStatusComboBox().setSelectedItem(nurse.getMaritalStatus());
    }
}
