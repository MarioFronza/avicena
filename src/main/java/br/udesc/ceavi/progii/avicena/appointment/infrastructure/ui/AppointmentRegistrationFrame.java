package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorJpaRepository;
import br.udesc.ceavi.progii.avicena.doctor.usecase.ListDoctors;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence.NurseJpaRepository;
import br.udesc.ceavi.progii.avicena.nurse.usecase.ListNurses;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.text.ParseException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class AppointmentRegistrationFrame extends FrameCRUD {

    private static AppointmentRegistrationFrame instance;
    private static final String TITLE = "New Appointment";
    private static final Dimension DIMENSION = new Dimension(630, 260);

    private JLabel dateLabel;
    private JLabel timeLabel;
    private JLabel symptomsLabel;
    private JLabel patientCpfLabel;
    private JLabel doctorLabel;
    private JLabel nurseLabel;
    private JLabel urgencyLabel;

    private JTextField dateField;
    private JTextField timeField;
    private JTextField symptomsField;
    private JTextField patientCpfField;
    private JComboBox<String> doctorComboBox;
    private JComboBox<String> nurseComboBox;
    private JComboBox<UrgencyStatus> urgencyComboBox;

    private List<Doctor> doctors;
    private List<Nurse> nurses;

    private JPanel formPanel;
    private LayoutManager layout;
    private GridBagConstraints constraints;

    public AppointmentRegistrationFrame(String title, Dimension dimension) {
        super(title, dimension);

        initializeComponents();
        addComponents();
    }

    public static AppointmentRegistrationFrame getInstance() {
        if (instance == null) {
            instance = new AppointmentRegistrationFrame(TITLE, DIMENSION);
        }
        return instance;
    }

    @Override
    public void limparCampos() {
        dateField.setText(null);
        timeField.setText(null);
        symptomsField.setText(null);
        patientCpfField.setText(null);
    }

    @Override
    public void carregarCampos() {}

    public void reloadDoctorsAndNurses() {
        doctors = new ListDoctors(new DoctorJpaRepository()).list();
        doctorComboBox.removeAllItems();
        for (Doctor doctor : doctors) {
            doctorComboBox.addItem(doctor.getName() + " - " + doctor.getSpecialty());
        }

        nurses = new ListNurses(new NurseJpaRepository()).list();
        nurseComboBox.removeAllItems();
        for (Nurse nurse : nurses) {
            nurseComboBox.addItem(nurse.getName() + " - " + nurse.getFormation());
        }
    }

    public Long getSelectedDoctorId() {
        int index = doctorComboBox.getSelectedIndex();
        return index < 0 || doctors == null ? null : doctors.get(index).getId();
    }

    public Long getSelectedNurseId() {
        int index = nurseComboBox.getSelectedIndex();
        return index < 0 || nurses == null ? null : nurses.get(index).getId();
    }

    private void initializeComponents() {
        dateLabel = new JLabel("Date:");
        timeLabel = new JLabel("Time:");
        symptomsLabel = new JLabel("Symptoms:");
        patientCpfLabel = new JLabel("Patient (CPF):");
        doctorLabel = new JLabel("Doctor:");
        nurseLabel = new JLabel("Nurse:");
        urgencyLabel = new JLabel("Urgency Status:");

        symptomsField = new JTextField();
        patientCpfField = new JTextField();
        doctorComboBox = new JComboBox<>();
        nurseComboBox = new JComboBox<>();
        urgencyComboBox = new JComboBox<>(UrgencyStatus.values());

        try {
            dateField = new JFormattedTextField(new MaskFormatter("##/##/####"));
            timeField = new JFormattedTextField(new MaskFormatter("##:##"));
        } catch (ParseException e) {
            dateField = new JTextField();
            timeField = new JTextField();
        }

        layout = new GridBagLayout();
        formPanel = new JPanel(layout);
        formPanel.setBorder(BorderFactory.createTitledBorder("Appointment Data"));
    }

    private void addComponents() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(dateLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 150;
        formPanel.add(dateField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 20, 0, 0);
        formPanel.add(timeLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        formPanel.add(timeField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 10;
        formPanel.add(symptomsLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 200;
        formPanel.add(symptomsField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 10;
        formPanel.add(patientCpfLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 200;
        formPanel.add(patientCpfField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 10;
        formPanel.add(doctorLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(doctorComboBox, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 10;
        formPanel.add(nurseLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(nurseComboBox, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 10;
        formPanel.add(urgencyLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(urgencyComboBox, constraints);

        super.addFormulario(formPanel);
    }

    public JTextField getDateField() {
        return dateField;
    }

    public JTextField getTimeField() {
        return timeField;
    }

    public JTextField getSymptomsField() {
        return symptomsField;
    }

    public JTextField getPatientCpfField() {
        return patientCpfField;
    }

    public JComboBox<String> getDoctorComboBox() {
        return doctorComboBox;
    }

    public JComboBox<String> getNurseComboBox() {
        return nurseComboBox;
    }

    public JComboBox<UrgencyStatus> getUrgencyComboBox() {
        return urgencyComboBox;
    }
}
