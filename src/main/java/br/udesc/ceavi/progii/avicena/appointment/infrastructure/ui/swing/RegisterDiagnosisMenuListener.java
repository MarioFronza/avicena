package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.control.menu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.main.MainFrame;
import br.udesc.ceavi.progii.avicena.view.main.MainMenu.Section;
import java.awt.event.ActionEvent;

public class RegisterDiagnosisMenuListener extends MenuActionListener {

    private final DiagnosisRegistrationFrame diagnosisFrame;

    public RegisterDiagnosisMenuListener(MainFrame mainFrame, DiagnosisRegistrationFrame diagnosisFrame) {
        super(
                mainFrame,
                Section.PATIENT,
                "Diagnóstico",
                "report_edit.png",
                "Cadastro dos diagnósticos primário e final");
        this.diagnosisFrame = diagnosisFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        diagnosisFrame.reloadAppointments();

        if (!diagnosisFrame.isVisible()) {
            mainFrame.addInternalFrame(diagnosisFrame);
            diagnosisFrame.setVisible(true);
        }
    }
}
