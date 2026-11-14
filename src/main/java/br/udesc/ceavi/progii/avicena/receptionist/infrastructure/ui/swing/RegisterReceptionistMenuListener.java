package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.control.menu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.main.MainFrame;
import br.udesc.ceavi.progii.avicena.view.main.MainMenu.Section;
import java.awt.event.ActionEvent;

public class RegisterReceptionistMenuListener extends MenuActionListener {

    private final ReceptionistRegistrationFrame receptionistFrame;

    public RegisterReceptionistMenuListener(MainFrame mainFrame, ReceptionistRegistrationFrame receptionistFrame) {
        super(
                mainFrame,
                Section.REGISTRATION,
                "Cadastro Atendente",
                "vcard_add.png",
                "Cadastra a atendente, secretaria,...");
        this.receptionistFrame = receptionistFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!receptionistFrame.isVisible()) {
            mainFrame.addInternalFrame(receptionistFrame);
            receptionistFrame.setVisible(true);
        }
    }
}
