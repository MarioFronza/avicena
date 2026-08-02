package br.udesc.ceavi.progii.avicena.patient.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

public class RegisterPatientMenuListener extends MenuActionListener {

    public RegisterPatientMenuListener(FrameSistema tela) {
        super(tela);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame = PatientRegistrationFrame.getInstance();

        PatientCrudController.getInstance(frame);

        if (!frame.isVisible()) {
            tela.adicionarFrameInterno(frame);
            frame.setVisible(true);
        }
    }
}
