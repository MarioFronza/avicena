package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorEntity;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence.NurseEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import br.udesc.ceavi.progii.avicena.view.frames.FrameSemCRUD;
import jakarta.persistence.EntityManager;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AppointmentListFrame extends FrameSemCRUD {

    private static AppointmentListFrame instance;

    private static final String TITLE = "Appointment List";
    private static final Dimension DIMENSION = new Dimension(800, 500);

    private JPanel backgroundPanel;
    private JTable table;
    private JScrollPane scrollPane;

    DefaultTableModel tableModel;

    public AppointmentListFrame(String title, Dimension dimension) throws HeadlessException {
        super(title, dimension);

        initializeComponents();
        addComponents();
    }

    @Override
    public void limparCampos() {}

    @Override
    public void carregarCampos() {}

    private void initializeComponents() {
        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {}, new String[] {"Date", "Time", "Patient", "Doctor", "Nurse"}));
        tableModel = (DefaultTableModel) table.getModel();
        backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new GridLayout(1, 1));
        scrollPane = new JScrollPane(table);

        for (AppointmentEntity appointment : findAll()) {
            tableModel.addRow(toTableRow(appointment));
        }
    }

    private List<AppointmentEntity> findAll() {
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

    static String[] toTableRow(AppointmentEntity appointment) {
        return new String[] {
            appointment.getDate(),
            appointment.getTime(),
            nomeOuPlaceholder(appointment.getPatient()),
            nomeOuPlaceholder(appointment.getDoctor()),
            nomeOuPlaceholder(appointment.getNurse())
        };
    }

    private static String nomeOuPlaceholder(PatientEntity patient) {
        return patient == null ? "-" : patient.getName();
    }

    private static String nomeOuPlaceholder(DoctorEntity doctor) {
        return doctor == null ? "-" : doctor.getName();
    }

    private static String nomeOuPlaceholder(NurseEntity nurse) {
        return nurse == null ? "-" : nurse.getName();
    }

    public static AppointmentListFrame getInstance() {
        if (instance == null) {
            instance = new AppointmentListFrame(TITLE, DIMENSION);
        }
        return instance;
    }

    private void addComponents() {
        backgroundPanel.add(scrollPane);
        super.addFormulario2("North", backgroundPanel);
    }
}
