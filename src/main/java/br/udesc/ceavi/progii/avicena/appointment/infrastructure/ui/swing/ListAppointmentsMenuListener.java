package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

public class ListAppointmentsMenuListener extends MenuActionListener {

    private final AppointmentListFrame appointmentListFrame;

    public ListAppointmentsMenuListener(FrameSistema tela, AppointmentListFrame appointmentListFrame) {
        super(tela);
        this.appointmentListFrame = appointmentListFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appointmentListFrame.reloadAppointments();

        if (!appointmentListFrame.isVisible()) {
            tela.adicionarFrameInterno(appointmentListFrame);
            appointmentListFrame.setVisible(true);
        }
    }
}
