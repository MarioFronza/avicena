package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientHistorySearchController implements ActionListener {

    private final PatientHistoryFrame frame;

    public PatientHistorySearchController(PatientHistoryFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cpf = frame.getCpfField().getText();

        frame.showAppointments(PatientHistoryFrame.filterByPatientCpf(PatientHistoryFrame.findAll(), cpf));
    }
}
