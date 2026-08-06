package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

public class ShowPatientHistoryMenuListener extends MenuActionListener {

    public ShowPatientHistoryMenuListener(FrameSistema tela) {
        super(tela);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame2 = PatientHistoryFrame.getInstance();

        if (!frame2.isVisible()) {
            tela.adicionarFrameInterno(frame2);
            frame2.setVisible(true);
        }
    }
}
