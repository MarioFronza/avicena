package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.InvalidAppointmentDataException;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteAppointment;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterAppointment;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.usecase.ListPatients;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AppointmentCrudController {

    private Appointment appointment;
    private final AppointmentRegistrationFrame frame;
    private final RegisterAppointment registerAppointment;
    private final DeleteAppointment deleteAppointment;
    private final ListPatients listPatients;

    public AppointmentCrudController(
            AppointmentRegistrationFrame frame,
            RegisterAppointment registerAppointment,
            DeleteAppointment deleteAppointment,
            ListPatients listPatients) {
        this.frame = frame;
        this.registerAppointment = registerAppointment;
        this.deleteAppointment = deleteAppointment;
        this.listPatients = listPatients;
        addCrudListeners();
    }

    public void delete() {
        deleteAppointment.delete(appointment);
    }

    public void register() {
        String date = frame.getDateField().getText();
        String time = frame.getTimeField().getText();
        String symptoms = frame.getSymptomsField().getText();
        Long patientId = findPatientIdByCpf(frame.getPatientCpfField().getText());
        Long doctorId = frame.getSelectedDoctorId();
        Long nurseId = frame.getSelectedNurseId();
        UrgencyStatus urgencyStatus = (UrgencyStatus) frame.getUrgencyComboBox().getSelectedItem();
        appointment = registerAppointment.register(
                new Appointment(date, time, symptoms, patientId, doctorId, nurseId, urgencyStatus));
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    private void addCrudListeners() {
        JButton button;

        button = frame.getPanelBotoesCRUD().getBtCancelar();
        button.addActionListener(new CancelActionListener());
        button = frame.getPanelBotoesCRUD().getBtNovo();
        button.addActionListener(new RegisterActionListener());
        button = frame.getPanelBotoesCRUD().getBtExcluir();
        button.addActionListener(new DeleteActionListener());
        button = frame.getPanelBotoesCRUD().getBtGravar();
        button.addActionListener(new RegisterActionListener());
    }

    private Long findPatientIdByCpf(String cpf) {
        List<Patient> patients = listPatients.list();
        Optional<Patient> found = patients.stream()
                .filter(patient -> cpf.equals(patient.getCpf()))
                .findFirst();
        return found.map(Patient::getId).orElse(null);
    }

    private class CancelActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(
                    frame,
                    "Do you really want to close this window?",
                    "Confirm Close",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                frame.dispose();
                frame.limparCampos();
            }
        }
    }

    private class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(
                    frame,
                    "Do you want to delete this Appointment?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                delete();
                frame.limparCampos();
            }
        }
    }

    private class RegisterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                register();
                JOptionPane.showMessageDialog(frame, "Appointment saved");
            } catch (InvalidAppointmentDataException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }

            frame.limparCampos();
        }
    }
}
