package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.view.frames.FrameSemCRUD;
import jakarta.persistence.EntityManager;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PatientHistoryFrame extends FrameSemCRUD {

    private static PatientHistoryFrame instance;

    private static final String TITLE = "Patient History";
    private static final Dimension DIMENSION = new Dimension(720, 480);

    private JLabel cpfLabel;
    private JTextField cpfField;
    private JButton searchButton;
    private JTable table;
    private DefaultTableModel tableModel;

    public PatientHistoryFrame(String title, Dimension dimension) throws HeadlessException {
        super(title, dimension);

        initializeComponents();
        addComponents();
    }

    @Override
    public void limparCampos() {}

    @Override
    public void carregarCampos() {}

    public static PatientHistoryFrame getInstance() {
        if (instance == null) {
            instance = new PatientHistoryFrame(TITLE, DIMENSION);
        }
        return instance;
    }

    public JTextField getCpfField() {
        return cpfField;
    }

    public void showAppointments(List<AppointmentEntity> appointments) {
        tableModel.setRowCount(0);
        for (AppointmentEntity appointment : appointments) {
            tableModel.addRow(toTableRow(appointment));
        }
    }

    static String[] toTableRow(AppointmentEntity appointment) {
        return new String[] {appointment.getDate(), appointment.getSymptoms()};
    }

    static List<AppointmentEntity> filterByPatientCpf(List<AppointmentEntity> appointments, String cpf) {
        return appointments.stream()
                .filter(appointment -> appointment.getPatient() != null
                        && cpf.equals(appointment.getPatient().getCpf()))
                .toList();
    }

    static List<AppointmentEntity> findAll() {
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

    private void initializeComponents() {
        cpfLabel = new JLabel("Cpf: ");
        cpfField = new JTextField();
        searchButton = new JButton("Buscar");
        table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"Date", "Symptoms"}));
        tableModel = (DefaultTableModel) table.getModel();

        searchButton.addActionListener(new PatientHistorySearchController());
    }

    private void addComponents() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(cpfLabel, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        searchPanel.add(cpfField, cons);

        cons = new GridBagConstraints();
        cons.gridx = 2;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(searchButton, cons);

        JPanel tablePanel = new JPanel(new GridLayout(1, 1));
        tablePanel.add(new JScrollPane(table));

        super.addFormulario2("North", searchPanel);
        super.addFormulario2("Center", tablePanel);
    }
}
