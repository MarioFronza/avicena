package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

public class ShowPatientHistoryMenuListener extends MenuActionListener {

    private final PatientHistoryFrame patientHistoryFrame;

    public ShowPatientHistoryMenuListener(FrameSistema tela, PatientHistoryFrame patientHistoryFrame) {
        super(tela);
        this.patientHistoryFrame = patientHistoryFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!patientHistoryFrame.isVisible()) {
            tela.adicionarFrameInterno(patientHistoryFrame);
            patientHistoryFrame.setVisible(true);
        }
    }
}
