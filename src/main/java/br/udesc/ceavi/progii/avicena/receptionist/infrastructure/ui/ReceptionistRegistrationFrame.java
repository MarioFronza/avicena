package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.BtEnderecoListener;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import br.udesc.ceavi.progii.avicena.view.principal.MenuPrincipal;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ReceptionistRegistrationFrame extends FrameCRUD implements ActionListener {

    private static ReceptionistRegistrationFrame instance;
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

    private final FrameSistema mainFrame = MenuPrincipal.getInstance().getFrame();

    public ReceptionistRegistrationFrame(String title, Dimension dimension) {
        super(title, dimension);

        initializeComponents();
        addComponents();
        addListeners();
    }

    public static ReceptionistRegistrationFrame getInstance() {
        if (instance == null) {
            instance = new ReceptionistRegistrationFrame(TITLE, DIMENSION);
        }
        return instance;
    }

    @Override
    public void limparCampos() {
        nameField.setText(null);
        cpfField.setText(null);
        phoneField.setText(null);
        workHoursField.setText(null);
        overtimeHoursField.setText(null);
        salaryField.setText(null);
        laborCardNumberField.setText(null);
    }

    @Override
    public void carregarCampos() {}

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
        addressButton.addActionListener(this);
        formPanel.add(addressButton, constraints);

        super.addFormulario(formPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    private void addListeners() {
        ActionListener addressListener = new BtEnderecoListener(mainFrame);
        ActionListener searchListener = new ReceptionistSearchController();
        searchButton.addActionListener(searchListener);
        addressButton.addActionListener(addressListener);
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getCpfField() {
        return cpfField;
    }

    public JTextField getPhoneField() {
        return phoneField;
    }

    public JTextField getWorkHoursField() {
        return workHoursField;
    }

    public JTextField getOvertimeHoursField() {
        return overtimeHoursField;
    }

    public JTextField getSalaryField() {
        return salaryField;
    }

    public JTextField getLaborCardNumberField() {
        return laborCardNumberField;
    }

    public JComboBox<MaritalStatus> getMaritalStatusComboBox() {
        return maritalStatusComboBox;
    }

    public JButton getSearchButton() {
        return searchButton;
    }
}
