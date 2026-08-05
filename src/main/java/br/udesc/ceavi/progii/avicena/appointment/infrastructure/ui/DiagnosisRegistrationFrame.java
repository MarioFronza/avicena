package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.BtGerarReceiraListener;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import jakarta.persistence.EntityManager;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DiagnosisRegistrationFrame extends FrameCRUD implements ActionListener {

    private static DiagnosisRegistrationFrame instance;
    private static final String TITLE = "Diagnosis Registration";
    private static final Dimension DIMENSION = new Dimension(350, 300);

    private JLabel bloodPressureLabel;
    private JLabel temperatureLabel;
    private JLabel heightLabel;
    private JLabel weightLabel;
    private JLabel notesLabel;
    private JLabel appointmentLabel;
    private JLabel diseaseLabel;
    private JLabel medicationsLabel;
    private JLabel examNotesLabel;
    private JLabel finalDescriptionLabel;

    private JTextField bloodPressureField;
    private JTextField temperatureField;
    private JTextField heightField;
    private JTextField weightField;
    private JTextArea notesArea;
    private JComboBox<String> appointmentComboBox;
    private JTextField diseaseField;
    private JTextField medicationsField;
    private JTextField examNotesField;
    private JTextArea finalDescriptionArea;
    private JButton generateReceiptButton;

    private JButton primaryPanelButton;
    private JButton finalPanelButton;

    private JPanel primaryPanel;
    private JPanel finalPanel;
    private JPanel buttonsPanel;
    private JPanel cardPanel;

    private List<AppointmentEntity> appointments;

    private LayoutManager layout;
    private GridBagConstraints constraints;

    public DiagnosisRegistrationFrame(String title, Dimension dimension) {
        super(title, dimension);

        initializeComponents();
        addComponents();
        addListeners();
    }

    public static DiagnosisRegistrationFrame getInstance() {
        if (instance == null) {
            instance = new DiagnosisRegistrationFrame(TITLE, DIMENSION);
        }
        return instance;
    }

    @Override
    public void limparCampos() {}

    @Override
    public void carregarCampos() {}

    public void reloadAppointments() {
        appointments = findAllAppointments();
        appointmentComboBox.removeAllItems();
        for (AppointmentEntity appointment : appointments) {
            appointmentComboBox.addItem(appointment.getTime() + " - " + appointment.getPatient().getName());
        }
    }

    private List<AppointmentEntity> findAllAppointments() {
        EntityManager entityManager =
                PersistenceConfig.createEntityManagerFactory().createEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT a FROM AppointmentEntity a", AppointmentEntity.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public Long getSelectedAppointmentId() {
        int index = appointmentComboBox.getSelectedIndex();
        return index < 0 || appointments == null ? null : appointments.get(index).getId();
    }

    private void initializeComponents() {
        bloodPressureLabel = new JLabel("Blood Pressure:");
        temperatureLabel = new JLabel("Temperature:");
        heightLabel = new JLabel("Height:");
        weightLabel = new JLabel("Weight:");
        notesLabel = new JLabel("Notes");
        appointmentLabel = new JLabel("Appointment");

        diseaseLabel = new JLabel("Disease:");
        medicationsLabel = new JLabel("Medications:");
        examNotesLabel = new JLabel("Exam Notes:");
        finalDescriptionLabel = new JLabel("Description:");

        bloodPressureField = new JTextField();
        temperatureField = new JTextField();
        heightField = new JTextField();
        weightField = new JTextField();
        notesArea = new JTextArea(4, 4);
        appointmentComboBox = new JComboBox<>();

        diseaseField = new JTextField();
        medicationsField = new JTextField();
        examNotesField = new JTextField();
        finalDescriptionArea = new JTextArea(4, 4);
        generateReceiptButton = new JButton("Generate Receipt");

        primaryPanelButton = new JButton("Primary Diagnosis");
        finalPanelButton = new JButton("Final Diagnosis");
        primaryPanelButton.addActionListener(this);
        finalPanelButton.addActionListener(this);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));
        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout());

        layout = new GridBagLayout();
        primaryPanel = new JPanel(layout);
        finalPanel = new JPanel(layout);

        primaryPanel.setBorder(BorderFactory.createTitledBorder("Primary Diagnosis Data"));
        finalPanel.setBorder(BorderFactory.createTitledBorder("Final Diagnosis Data"));
    }

    private void addComponents() {
        buttonsPanel.add(primaryPanelButton);
        buttonsPanel.add(finalPanelButton);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        primaryPanel.add(bloodPressureLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 200;
        primaryPanel.add(bloodPressureField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        primaryPanel.add(temperatureLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 200;
        primaryPanel.add(temperatureField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        primaryPanel.add(heightLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        primaryPanel.add(heightField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        primaryPanel.add(weightLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        primaryPanel.add(weightField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        primaryPanel.add(notesLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        constraints.ipady = 30;
        constraints.insets = new Insets(5, 0, 5, 0);
        primaryPanel.add(new JScrollPane(notesArea), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        primaryPanel.add(appointmentLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        primaryPanel.add(appointmentComboBox, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        finalPanel.add(diseaseLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 200;
        finalPanel.add(diseaseField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        finalPanel.add(medicationsLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 200;
        finalPanel.add(medicationsField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        finalPanel.add(finalDescriptionLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 5, 0);
        constraints.ipadx = 100;
        finalPanel.add(new JScrollPane(finalDescriptionArea), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        finalPanel.add(examNotesLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(0, 0, 5, 0);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        finalPanel.add(examNotesField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        finalPanel.add(generateReceiptButton, constraints);

        cardPanel.add(primaryPanel, "primary");
        cardPanel.add(finalPanel, "final");

        super.addFormulario2("North", buttonsPanel);
        super.addFormulario2("Center", cardPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();

        if (e.getSource() == primaryPanelButton) {
            cardLayout.show(cardPanel, "primary");
        }
        if (e.getSource() == finalPanelButton) {
            cardLayout.show(cardPanel, "final");
        }
    }

    private void addListeners() {
        ActionListener receiptListener = new BtGerarReceiraListener();
        generateReceiptButton.addActionListener(receiptListener);
    }

    public JTextField getBloodPressureField() {
        return bloodPressureField;
    }

    public JTextField getTemperatureField() {
        return temperatureField;
    }

    public JTextField getHeightField() {
        return heightField;
    }

    public JTextField getWeightField() {
        return weightField;
    }

    public JTextField getDiseaseField() {
        return diseaseField;
    }

    public JTextField getMedicationsField() {
        return medicationsField;
    }

    public JTextField getExamNotesField() {
        return examNotesField;
    }

    public JComboBox<String> getAppointmentComboBox() {
        return appointmentComboBox;
    }
}
