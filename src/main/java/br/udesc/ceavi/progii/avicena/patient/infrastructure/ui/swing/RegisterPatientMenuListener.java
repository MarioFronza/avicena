package br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.control.menu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.main.MainFrame;
import br.udesc.ceavi.progii.avicena.view.main.MainMenu.Section;
import java.awt.event.ActionEvent;

public class RegisterPatientMenuListener extends MenuActionListener {

    private final PatientRegistrationFrame patientFrame;

    public RegisterPatientMenuListener(MainFrame mainFrame, PatientRegistrationFrame patientFrame) {
        super(
                mainFrame,
                Section.REGISTRATION,
                "Patient Registration",
                "vcard_add.png",
                "Register patients and search them by CPF");
        this.patientFrame = patientFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!patientFrame.isVisible()) {
            mainFrame.addInternalFrame(patientFrame);
            patientFrame.setVisible(true);
        }
    }
}
