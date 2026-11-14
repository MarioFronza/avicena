package br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.control.menu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.main.MainFrame;
import br.udesc.ceavi.progii.avicena.view.main.MainMenu.Section;
import java.awt.event.ActionEvent;

public class RegisterDoctorMenuListener extends MenuActionListener {

    private final DoctorRegistrationFrame doctorFrame;

    public RegisterDoctorMenuListener(MainFrame mainFrame, DoctorRegistrationFrame doctorFrame) {
        super(
                mainFrame,
                Section.REGISTRATION,
                "Cadastro Médico",
                "vcard_add.png",
                "Cadastra o médico e sua especialidade");
        this.doctorFrame = doctorFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!doctorFrame.isVisible()) {
            mainFrame.addInternalFrame(doctorFrame);
            doctorFrame.setVisible(true);
        }
    }
}
