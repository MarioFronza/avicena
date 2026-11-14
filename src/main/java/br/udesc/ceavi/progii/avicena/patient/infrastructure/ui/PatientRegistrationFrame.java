package br.udesc.ceavi.progii.avicena.patient.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.InvalidPatientDataException;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PatientRegistrationFrame extends FrameCRUD {

    private static final String TITLE = "Patient Registration";
    private static final Dimension DIMENSION = new Dimension(360, 240);

    private JLabel nameLabel;
    private JLabel cpfLabel;
    private JLabel phoneLabel;
    private JLabel addressLabel;
    private JLabel maritalStatusLabel;
    private JButton searchButton;
    private JTextField nameField;
    private JTextField cpfField;
    private JTextField phoneField;
    private JButton addressButton;
    private JComboBox<MaritalStatus> maritalStatusComboBox;

    private JPanel formPanel;
    private LayoutManager layout;
    private GridBagConstraints constraints;

    private final PatientController controller;
    private Patient patient;
    private Address address;

    public PatientRegistrationFrame(PatientController controller) {
        super(TITLE, DIMENSION);
        this.controller = controller;

        initializeComponents();
        addComponents();
        addListeners();
    }

    public void useAddress(Address address) {
        this.address = address;
    }

    private void addListeners() {
        getPanelBotoesCRUD().getBtCancelar().addActionListener(e -> cancel());
        getPanelBotoesCRUD().getBtNovo().addActionListener(e -> register());
        getPanelBotoesCRUD().getBtExcluir().addActionListener(e -> delete());
        getPanelBotoesCRUD().getBtGravar().addActionListener(e -> register());
        searchButton.addActionListener(e -> search());
    }

    private void cancel() {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Do you really want to close this window?",
                "Confirm Close",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            dispose();
            limparCampos();
        }
    }

    private void delete() {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Do you want to delete this Patient?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            controller.delete(patient);
            limparCampos();
        }
    }

    private void register() {
        try {
            patient = controller.register(new PatientController.Form(
                    nameField.getText(), cpfField.getText(), phoneField.getText(), address, (MaritalStatus)
                            maritalStatusComboBox.getSelectedItem()));
            JOptionPane.showMessageDialog(this, "Patient saved");
        } catch (InvalidPatientDataException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

        limparCampos();
    }

    private void search() {
        controller
                .findByCpf(cpfField.getText())
                .ifPresentOrElse(this::show, () -> JOptionPane.showMessageDialog(this, "Patient not found"));
    }

    private void show(Patient found) {
        JOptionPane.showMessageDialog(this, "Patient found");
        patient = found;
        nameField.setText(found.getName());
        phoneField.setText(found.getPhone());
        maritalStatusComboBox.setSelectedItem(found.getMaritalStatus());
    }

    @Override
    public void limparCampos() {
        nameField.setText(null);
        cpfField.setText(null);
        phoneField.setText(null);
    }

    @Override
    public void carregarCampos() {}

    private void initializeComponents() {
        nameLabel = new JLabel("Name:");
        cpfLabel = new JLabel("CPF:");
        phoneLabel = new JLabel("Phone:");
        addressLabel = new JLabel("Address:");
        maritalStatusLabel = new JLabel("Marital Status:");
        searchButton = new JButton("Search");

        nameField = new JTextField();
        cpfField = new JTextField();
        phoneField = new JTextField();
        addressButton = new JButton("Add");
        addressButton.setSize(100, 20);
        maritalStatusComboBox = new JComboBox<>(MaritalStatus.values());
        maritalStatusComboBox.setSelectedIndex(-1);

        layout = new GridBagLayout();
        formPanel = new JPanel(layout);
        formPanel.setBorder(BorderFactory.createTitledBorder("Patient Data"));
    }

    private void addComponents() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        formPanel.add(nameLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 200;
        formPanel.add(nameField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        formPanel.add(cpfLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 105;
        constraints.insets = new Insets(0, 0, 0, 10);
        formPanel.add(cpfField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        formPanel.add(searchButton, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        formPanel.add(phoneLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        formPanel.add(phoneField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 20;
        formPanel.add(maritalStatusLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(maritalStatusComboBox, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        formPanel.add(addressLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(addressButton, constraints);

        super.addFormulario(formPanel);
    }

    public JButton getAddressButton() {
        return addressButton;
    }
}
