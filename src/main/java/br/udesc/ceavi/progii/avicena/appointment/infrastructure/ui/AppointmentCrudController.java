package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.InvalidAppointmentDataException;
import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentJpaRepository;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteAppointment;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterAppointment;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientJpaRepository;
import br.udesc.ceavi.progii.avicena.patient.usecase.ListPatients;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AppointmentCrudController {

    private static AppointmentCrudController instance;

    private Appointment appointment;
    private final FrameCRUD frame;
    private final RegisterAppointment registerAppointment;
    private final DeleteAppointment deleteAppointment;

    public AppointmentCrudController(FrameCRUD frame) {
        this.frame = frame;
        AppointmentJpaRepository repository = new AppointmentJpaRepository();
        this.registerAppointment = new RegisterAppointment(repository);
        this.deleteAppointment = new DeleteAppointment(repository);

        addCrudListeners();
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public static AppointmentCrudController getInstance(FrameCRUD frame) {
        if (instance == null) {
            instance = new AppointmentCrudController(frame);
        }
        return instance;
    }

    public static AppointmentCrudController getInstance() {
        return instance;
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
        List<Patient> patients = new ListPatients(new PatientJpaRepository()).list();
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
                deleteAppointment.delete(appointment);
                frame.limparCampos();
            }
        }
    }

    private class RegisterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AppointmentRegistrationFrame registrationFrame = AppointmentRegistrationFrame.getInstance();
            String date = registrationFrame.getDateField().getText();
            String time = registrationFrame.getTimeField().getText();
            String symptoms = registrationFrame.getSymptomsField().getText();
            Long patientId =
                    findPatientIdByCpf(registrationFrame.getPatientCpfField().getText());
            Long doctorId = registrationFrame.getSelectedDoctorId();
            Long nurseId = registrationFrame.getSelectedNurseId();

            try {
                appointment = registerAppointment.register(
                        new Appointment(date, time, symptoms, patientId, doctorId, nurseId, UrgencyStatus.NOT_URGENT));
                JOptionPane.showMessageDialog(frame, "Appointment saved");
            } catch (InvalidAppointmentDataException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }

            frame.limparCampos();
        }
    }
}
