package br.udesc.ceavi.progii.avicena.nurse.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.control.menu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.main.MainFrame;
import br.udesc.ceavi.progii.avicena.view.main.MainMenu.Section;
import java.awt.event.ActionEvent;

public class RegisterNurseMenuListener extends MenuActionListener {

    private final NurseRegistrationFrame nurseFrame;

    public RegisterNurseMenuListener(MainFrame mainFrame, NurseRegistrationFrame nurseFrame) {
        super(mainFrame, Section.REGISTRATION, "Cadastro Enfermeiro", "vcard_add.png", "Cadastra o enfermeiro ");
        this.nurseFrame = nurseFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!nurseFrame.isVisible()) {
            mainFrame.addInternalFrame(nurseFrame);
            nurseFrame.setVisible(true);
        }
    }
}
