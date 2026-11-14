package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

public class RegisterAppointmentMenuListener extends MenuActionListener {

    private final AppointmentRegistrationFrame appointmentFrame;

    public RegisterAppointmentMenuListener(FrameSistema tela, AppointmentRegistrationFrame appointmentFrame) {
        super(tela);
        this.appointmentFrame = appointmentFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appointmentFrame.reloadDoctorsAndNurses();

        if (!appointmentFrame.isVisible()) {
            tela.adicionarFrameInterno(appointmentFrame);
            appointmentFrame.setVisible(true);
        }
    }
}
