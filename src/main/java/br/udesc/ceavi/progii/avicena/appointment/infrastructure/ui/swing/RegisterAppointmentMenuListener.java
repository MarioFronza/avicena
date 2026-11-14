package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.control.menu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.main.MainFrame;
import br.udesc.ceavi.progii.avicena.view.main.MainMenu.Section;
import java.awt.event.ActionEvent;

public class RegisterAppointmentMenuListener extends MenuActionListener {

    private final AppointmentRegistrationFrame appointmentFrame;

    public RegisterAppointmentMenuListener(MainFrame mainFrame, AppointmentRegistrationFrame appointmentFrame) {
        super(mainFrame, Section.APPOINTMENT, "Nova Consulta", "table_add.png", "Cadastra uma nova consulta");
        this.appointmentFrame = appointmentFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appointmentFrame.reloadDoctorsAndNurses();

        if (!appointmentFrame.isVisible()) {
            mainFrame.addInternalFrame(appointmentFrame);
            appointmentFrame.setVisible(true);
        }
    }
}
