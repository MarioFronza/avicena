package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

public class RegisterDiagnosisMenuListener extends MenuActionListener {

    public RegisterDiagnosisMenuListener(FrameSistema tela) {
        super(tela);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DiagnosisRegistrationFrame registrationFrame = DiagnosisRegistrationFrame.getInstance();
        frame = registrationFrame;

        DiagnosisCrudController.getInstance(registrationFrame);
        registrationFrame.reloadAppointments();

        if (!frame.isVisible()) {
            tela.adicionarFrameInterno(frame);
            frame.setVisible(true);
        }
    }
}
