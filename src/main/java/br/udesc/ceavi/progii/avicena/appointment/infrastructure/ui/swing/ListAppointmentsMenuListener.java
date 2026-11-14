package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.control.menu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.main.MainFrame;
import br.udesc.ceavi.progii.avicena.view.main.MainMenu.Section;
import java.awt.event.ActionEvent;

public class ListAppointmentsMenuListener extends MenuActionListener {

    private final AppointmentListFrame appointmentListFrame;

    public ListAppointmentsMenuListener(MainFrame mainFrame, AppointmentListFrame appointmentListFrame) {
        super(mainFrame, Section.APPOINTMENT, "Lista Consulta", "table_gear.png", "Mostra as consultas cadastradas");
        this.appointmentListFrame = appointmentListFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appointmentListFrame.reloadAppointments();

        if (!appointmentListFrame.isVisible()) {
            mainFrame.addInternalFrame(appointmentListFrame);
            appointmentListFrame.setVisible(true);
        }
    }
}
