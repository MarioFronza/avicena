package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

public class RegisterAppointmentMenuListener extends MenuActionListener {

    public RegisterAppointmentMenuListener(FrameSistema tela) {
        super(tela);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AppointmentRegistrationFrame registrationFrame = AppointmentRegistrationFrame.getInstance();
        frame = registrationFrame;

        AppointmentCrudController.getInstance(registrationFrame);
        registrationFrame.reloadDoctorsAndNurses();

        if (!frame.isVisible()) {
            tela.adicionarFrameInterno(frame);
            frame.setVisible(true);
        }
    }
}
