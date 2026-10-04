package br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.AddAddressListener;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import br.udesc.ceavi.progii.avicena.view.principal.MenuPrincipal;
import java.awt.Component;
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
        addressButton.addActionListener(this);
        addField(addressButton, 1, 6, 1, 0, 0, null);

        super.addFormulario(formPanel);
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
