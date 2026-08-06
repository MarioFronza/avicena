package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.view.frames.FrameSemCRUD;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.List;

public class PatientHistoryFrame extends FrameSemCRUD {

    public PatientHistoryFrame(String title, Dimension dimension) throws HeadlessException {
        super(title, dimension);
    }

    @Override
    public void limparCampos() {}

    @Override
    public void carregarCampos() {}

    static String[] toTableRow(AppointmentEntity appointment) {
        return new String[] {appointment.getDate(), appointment.getSymptoms()};
    }

    static List<AppointmentEntity> filterByPatientCpf(List<AppointmentEntity> appointments, String cpf) {
        return appointments.stream()
                .filter(appointment -> appointment.getPatient() != null
                        && cpf.equals(appointment.getPatient().getCpf()))
                .toList();
    }
}
