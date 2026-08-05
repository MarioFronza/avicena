package br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.AddAddressListener;
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

public class DoctorRegistrationFrame extends FrameCRUD implements ActionListener {

    private static DoctorRegistrationFrame instance;
    private static final String TITLE = "Doctor Registration";
    private static final Dimension DIMENSION = new Dimension(360, 300);

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
    private GridBagConstraints constraints;

    private final FrameSistema mainFrame = MenuPrincipal.getInstance().getFrame();

    public DoctorRegistrationFrame(String title, Dimension dimension) {
        super(title, dimension);

        initializeComponents();
        addComponents();
        addListeners();
    }

    public static DoctorRegistrationFrame getInstance() {
        if (instance == null) {
            instance = new DoctorRegistrationFrame(TITLE, DIMENSION);
        }
        return instance;
    }

    @Override
    public void limparCampos() {
        nameField.setText(null);
        cpfField.setText(null);
        phoneField.setText(null);
        crmField.setText(null);
        specialtyField.setText(null);
    }

    @Override
    public void carregarCampos() {}

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
        formPanel.add(crmLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        formPanel.add(crmField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        formPanel.add(specialtyLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        formPanel.add(specialtyField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 20;
        formPanel.add(maritalStatusLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(maritalStatusComboBox, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        formPanel.add(addressLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        addressButton.addActionListener(this);
        formPanel.add(addressButton, constraints);

        super.addFormulario(formPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    private void addListeners() {
        ActionListener addressListener = new AddAddressListener(mainFrame);
        ActionListener searchListener = new DoctorSearchController();
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

    public JTextField getCrmField() {
        return crmField;
    }

    public JTextField getSpecialtyField() {
        return specialtyField;
    }

    public JComboBox<MaritalStatus> getMaritalStatusComboBox() {
        return maritalStatusComboBox;
    }

    public JButton getSearchButton() {
        return searchButton;
    }
}
