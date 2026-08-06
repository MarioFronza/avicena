package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientHistorySearchController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        PatientHistoryFrame frame = PatientHistoryFrame.getInstance();
        String cpf = frame.getCpfField().getText();

        frame.showAppointments(PatientHistoryFrame.filterByPatientCpf(PatientHistoryFrame.findAll(), cpf));
    }
}
