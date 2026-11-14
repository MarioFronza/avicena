package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorEntity;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence.NurseEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import br.udesc.ceavi.progii.avicena.view.frames.ReadOnlyFrame;
import jakarta.persistence.EntityManager;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AppointmentListFrame extends ReadOnlyFrame {

    private static final String TITLE = "Appointment List";
    private static final Dimension DIMENSION = new Dimension(800, 500);

    private JPanel backgroundPanel;
    private JTable table;
    private JScrollPane scrollPane;

    DefaultTableModel tableModel;

    public AppointmentListFrame() throws HeadlessException {
        super(TITLE, DIMENSION);

        initializeComponents();
        addComponents();
    }

    private void initializeComponents() {
        table = new JTable();
        table.setModel(
                new DefaultTableModel(new Object[][] {}, new String[] {"Date", "Time", "Patient", "Doctor", "Nurse"}));
        tableModel = (DefaultTableModel) table.getModel();
        backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new GridLayout(1, 1));
        scrollPane = new JScrollPane(table);
    }

    public void reloadAppointments() {
        tableModel.setRowCount(0);
        for (AppointmentEntity appointment : findAll()) {
            tableModel.addRow(toTableRow(appointment));
        }
    }

    private List<AppointmentEntity> findAll() {
        EntityManager entityManager = PersistenceConfig.entityManagerFactory().createEntityManager();
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
            nameOrPlaceholder(appointment.getPatient()),
            nameOrPlaceholder(appointment.getDoctor()),
            nameOrPlaceholder(appointment.getNurse())
        };
    }

    private static String nameOrPlaceholder(PatientEntity patient) {
        return patient == null ? "-" : patient.getName();
    }

    private static String nameOrPlaceholder(DoctorEntity doctor) {
        return doctor == null ? "-" : doctor.getName();
    }

    private static String nameOrPlaceholder(NurseEntity nurse) {
        return nurse == null ? "-" : nurse.getName();
    }

    private void addComponents() {
        backgroundPanel.add(scrollPane);
        super.addFormAt("North", backgroundPanel);
    }
}
