package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.receptionist.domain.InvalidReceptionistDataException;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.infrastructure.controller.ReceptionistController;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.view.frames.CrudFrame;
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

public class ReceptionistRegistrationFrame extends CrudFrame {

    private static final String TITLE = "Receptionist Registration";
    private static final Dimension DIMENSION = new Dimension(430, 330);

    private JLabel nameLabel;
    private JLabel cpfLabel;
    private JLabel phoneLabel;
    private JLabel workHoursLabel;
    private JLabel overtimeHoursLabel;
    private JLabel salaryLabel;
    private JLabel laborCardNumberLabel;
    private JLabel addressLabel;
    private JLabel maritalStatusLabel;
    private JButton searchButton;
    private JTextField nameField;
    private JTextField cpfField;
    private JTextField phoneField;
    private JTextField workHoursField;
    private JTextField overtimeHoursField;
    private JTextField salaryField;
    private JTextField laborCardNumberField;
    private JButton addressButton;
    private JComboBox<MaritalStatus> maritalStatusComboBox;

    private JPanel formPanel;
    private LayoutManager layout;
    private GridBagConstraints constraints;

    private final ReceptionistController controller;
    private Receptionist receptionist;
    private Address address;

    public ReceptionistRegistrationFrame(ReceptionistController controller) {
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
        getCrudActionPanel().getCancelButton().addActionListener(e -> cancel());
        getCrudActionPanel().getNewButton().addActionListener(e -> register());
        getCrudActionPanel().getDeleteButton().addActionListener(e -> delete());
        getCrudActionPanel().getSaveButton().addActionListener(e -> register());
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
            clearFields();
        }
    }

    private void delete() {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Do you want to delete this Receptionist?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            controller.delete(receptionist);
            clearFields();
        }
    }

    private void register() {
        try {
            receptionist = controller.register(new ReceptionistController.Form(
                    nameField.getText(),
                    cpfField.getText(),
                    phoneField.getText(),
                    address,
                    (MaritalStatus) maritalStatusComboBox.getSelectedItem(),
                    workHoursField.getText(),
                    salaryField.getText(),
                    overtimeHoursField.getText(),
                    laborCardNumberField.getText()));
            JOptionPane.showMessageDialog(this, "Receptionist saved");
        } catch (InvalidReceptionistDataException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

        clearFields();
    }

    private void search() {
        controller
                .findByCpf(cpfField.getText())
                .ifPresentOrElse(this::show, () -> JOptionPane.showMessageDialog(this, "Receptionist not found"));
    }

    private void show(Receptionist found) {
        JOptionPane.showMessageDialog(this, "Receptionist found");
        receptionist = found;
        nameField.setText(found.getName());
        phoneField.setText(found.getPhone());
        workHoursField.setText(String.valueOf(found.getWorkHours()));
        salaryField.setText(String.valueOf(found.getSalary()));
        overtimeHoursField.setText(String.valueOf(found.getOvertimeHours()));
        laborCardNumberField.setText(String.valueOf(found.getLaborCardNumber()));
        maritalStatusComboBox.setSelectedItem(found.getMaritalStatus());
    }

    @Override
    public void clearFields() {
        nameField.setText(null);
        cpfField.setText(null);
        phoneField.setText(null);
        workHoursField.setText(null);
        overtimeHoursField.setText(null);
        salaryField.setText(null);
        laborCardNumberField.setText(null);
    }

    private void initializeComponents() {
        nameLabel = new JLabel("Name:");
        cpfLabel = new JLabel("CPF:");
        phoneLabel = new JLabel("Phone:");
        workHoursLabel = new JLabel("Work Hours:");
        overtimeHoursLabel = new JLabel("Overtime Hours:");
        salaryLabel = new JLabel("Salary:");
        laborCardNumberLabel = new JLabel("Labor Card Number:");
        addressLabel = new JLabel("Address:");
        maritalStatusLabel = new JLabel("Marital Status:");
        searchButton = new JButton("Search");

        nameField = new JTextField();
        cpfField = new JTextField();
        phoneField = new JTextField();
        workHoursField = new JTextField();
        overtimeHoursField = new JTextField();
        salaryField = new JTextField();
        laborCardNumberField = new JTextField();
        addressButton = new JButton("Add");
        addressButton.setSize(100, 20);
        maritalStatusComboBox = new JComboBox<>(MaritalStatus.values());
        maritalStatusComboBox.setSelectedIndex(-1);

        layout = new GridBagLayout();
        formPanel = new JPanel(layout);
        formPanel.setBorder(BorderFactory.createTitledBorder("Receptionist Data"));
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
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        formPanel.add(workHoursLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        formPanel.add(workHoursField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        formPanel.add(overtimeHoursLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        formPanel.add(overtimeHoursField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        formPanel.add(salaryLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        formPanel.add(salaryField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        formPanel.add(laborCardNumberLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        formPanel.add(laborCardNumberField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 20;
        formPanel.add(maritalStatusLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(maritalStatusComboBox, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        formPanel.add(addressLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 8;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(addressButton, constraints);

        super.addForm(formPanel);
    }

    public JButton getAddressButton() {
        return addressButton;
    }
}
