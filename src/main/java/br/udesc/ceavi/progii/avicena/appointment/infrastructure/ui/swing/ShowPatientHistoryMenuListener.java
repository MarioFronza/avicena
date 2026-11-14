package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.control.menu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.main.MainFrame;
import br.udesc.ceavi.progii.avicena.view.main.MainMenu.Section;
import java.awt.event.ActionEvent;

public class ShowPatientHistoryMenuListener extends MenuActionListener {

    private final PatientHistoryFrame patientHistoryFrame;

    public ShowPatientHistoryMenuListener(MainFrame mainFrame, PatientHistoryFrame patientHistoryFrame) {
        super(mainFrame, Section.PATIENT, "Histórico", "status_away.png", "Histórico de consultas do paciente");
        this.patientHistoryFrame = patientHistoryFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!patientHistoryFrame.isVisible()) {
            mainFrame.addInternalFrame(patientHistoryFrame);
            patientHistoryFrame.setVisible(true);
        }
    }
}
