package br.udesc.ceavi.progii.avicena.patient.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddressEntryFrame extends FrameCRUD {

    private static AddressEntryFrame instance;

    private static final String TITLE = "Address Entry";
    private static final Dimension DIMENSION = new Dimension(400, 280);

    private JLabel zipCodeLabel;
    private JLabel streetLabel;
    private JLabel numberLabel;
    private JLabel complementLabel;
    private JLabel neighborhoodLabel;
    private JLabel cityLabel;

    private JTextField zipCodeField;
    private JTextField streetField;
    private JTextField numberField;
    private JTextField complementField;
    private JTextField neighborhoodField;
    private JTextField cityField;

    private JPanel formPanel;
    private LayoutManager layout;
    private GridBagConstraints constraints;

    public AddressEntryFrame(String title, Dimension dimension) {
        super(title, dimension);

        initializeComponents();
        addComponents();
    }

    public static AddressEntryFrame getInstance() {
        if (instance == null) {
            instance = new AddressEntryFrame(TITLE, DIMENSION);
        }
        return instance;
    }

    @Override
    public void limparCampos() {
        zipCodeField.setText(null);
        streetField.setText(null);
        numberField.setText(null);
        complementField.setText(null);
        neighborhoodField.setText(null);
        cityField.setText(null);
    }

    @Override
    public void carregarCampos() {}

    private void initializeComponents() {
        zipCodeLabel = new JLabel("Zip Code:");
        streetLabel = new JLabel("Street:");
        numberLabel = new JLabel("Number:");
        complementLabel = new JLabel("Complement:");
        neighborhoodLabel = new JLabel("Neighborhood:");
        cityLabel = new JLabel("City:");

        zipCodeField = new JTextField();
        streetField = new JTextField();
        numberField = new JTextField();
        complementField = new JTextField();
        neighborhoodField = new JTextField();
        cityField = new JTextField();

        layout = new GridBagLayout();
        formPanel = new JPanel(layout);

        formPanel.setBorder(BorderFactory.createTitledBorder("Address Data"));
    }

    private void addComponents() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 10;
        formPanel.add(zipCodeLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 70;
        formPanel.add(zipCodeField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(streetLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        formPanel.add(streetField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 10;
        formPanel.add(numberLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        formPanel.add(numberField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 10;
        formPanel.add(complementLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 50;
        formPanel.add(complementField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 10;
        formPanel.add(neighborhoodLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        formPanel.add(neighborhoodField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(cityLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        formPanel.add(cityField, constraints);

        super.addFormulario(formPanel);
    }

    public JTextField getZipCodeField() {
        return zipCodeField;
    }

    public JTextField getStreetField() {
        return streetField;
    }

    public JTextField getNumberField() {
        return numberField;
    }

    public JTextField getComplementField() {
        return complementField;
    }

    public JTextField getNeighborhoodField() {
        return neighborhoodField;
    }

    public JTextField getCityField() {
        return cityField;
    }
}
