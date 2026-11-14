package br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.InvalidDoctorDataException;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.controller.DoctorController;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.view.frames.CrudFrame;
import java.awt.Component;
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

public class DoctorRegistrationFrame extends CrudFrame {

    private JLabel nameLabel;
    private JLabel cpfLabel;
    private JLabel phoneLabel;
    private JLabel crmLabel;
    private JLabel specialtyLabel;
    private JLabel addressLabel;
    private JLabel maritalStatusLabel;
    private JButton searchButton;
    private JTextField nameField;
    private JTextField cpfField;
    private JTextField phoneField;
    private JTextField crmField;
    private JTextField specialtyField;
    private JButton addressButton;
    private JComboBox<MaritalStatus> maritalStatusComboBox;

    private JPanel formPanel;
    private LayoutManager layout;

    private final DoctorController controller;
    private Doctor doctor;
    private Address address;

    public DoctorRegistrationFrame(DoctorController controller) {
        super("Doctor Registration", new Dimension(360, 300));
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
        getCrudActionPanel().getNewButton().addActionListener(e -> clearFields());
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
                "Do you want to delete this Doctor?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION && doctor != null) {
            controller.delete(doctor);
            clearFields();
        }
    }

    private void register() {
        try {
            doctor = controller.register(new DoctorController.Form(
                    doctor == null ? null : doctor.getId(),
                    nameField.getText(),
                    cpfField.getText(),
                    phoneField.getText(),
                    address,
                    (MaritalStatus) maritalStatusComboBox.getSelectedItem(),
                    crmField.getText(),
                    specialtyField.getText()));
            JOptionPane.showMessageDialog(this, "Doctor saved");
        } catch (InvalidDoctorDataException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

        clearFields();
    }

    private void search() {
        controller
                .findByCpf(cpfField.getText())
                .ifPresentOrElse(this::show, () -> JOptionPane.showMessageDialog(this, "Doctor not found"));
    }

    private void show(Doctor found) {
        JOptionPane.showMessageDialog(this, "Doctor found");
        doctor = found;
        address = found.getAddress();
        nameField.setText(found.getName());
        phoneField.setText(found.getPhone());
        crmField.setText(found.getCrm());
        specialtyField.setText(found.getSpecialty());
        maritalStatusComboBox.setSelectedItem(found.getMaritalStatus());
    }

    @Override
    public void clearFields() {
        doctor = null;
        address = null;
        nameField.setText(null);
        cpfField.setText(null);
        phoneField.setText(null);
        crmField.setText(null);
        specialtyField.setText(null);
    }

    private void initializeComponents() {
        nameLabel = new JLabel("Name:");
        cpfLabel = new JLabel("CPF:");
        phoneLabel = new JLabel("Phone:");
        crmLabel = new JLabel("CRM:");
        specialtyLabel = new JLabel("Specialty:");
        addressLabel = new JLabel("Address:");
        maritalStatusLabel = new JLabel("Marital Status:");
        searchButton = new JButton("Search");

        nameField = new JTextField();
        cpfField = new JTextField();
        phoneField = new JTextField();
        crmField = new JTextField();
        specialtyField = new JTextField();
        addressButton = new JButton("Add");
        addressButton.setSize(100, 20);
        maritalStatusComboBox = new JComboBox<>(MaritalStatus.values());
        maritalStatusComboBox.setSelectedIndex(-1);

        layout = new GridBagLayout();
        formPanel = new JPanel(layout);
        formPanel.setBorder(BorderFactory.createTitledBorder("Doctor Data"));
    }

    private void addComponents() {
        addField(nameLabel, 0, 0, 1, 10, 10, null);
        addField(nameField, 1, 0, 3, 200, 0, null);

        addField(cpfLabel, 0, 1, 1, 10, 10, null);
        addField(cpfField, 1, 1, 1, 105, 0, new Insets(0, 0, 0, 10));
        addField(searchButton, 2, 1, 1, 10, 0, null);

        addField(phoneLabel, 0, 2, 1, 10, 10, null);
        addField(phoneField, 1, 2, 2, 100, 0, null);

        addField(crmLabel, 0, 3, 1, 10, 10, null);
        addField(crmField, 1, 3, 2, 50, 0, null);

        addField(specialtyLabel, 0, 4, 1, 10, 10, null);
        addField(specialtyField, 1, 4, 2, 50, 0, null);

        addField(maritalStatusLabel, 0, 5, 1, 10, 20, null);
        addField(maritalStatusComboBox, 1, 5, 2, 0, 0, null);

        addField(addressLabel, 0, 6, 1, 10, 0, null);
        addField(addressButton, 1, 6, 1, 0, 0, null);

        super.addForm(formPanel);
    }

    private void addField(
            Component component, int gridx, int gridy, int gridwidth, int ipadx, int ipady, Insets insets) {
        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = gridx;
        fieldConstraints.gridy = gridy;
        fieldConstraints.gridwidth = gridwidth;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.ipadx = ipadx;
        fieldConstraints.ipady = ipady;
        if (insets != null) {
            fieldConstraints.insets = insets;
        }
        formPanel.add(component, fieldConstraints);
    }

    public JButton getAddressButton() {
        return addressButton;
    }
}
