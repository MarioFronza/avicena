package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller.AppointmentController;
import br.udesc.ceavi.progii.avicena.view.frames.ReadOnlyFrame;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
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

    private final AppointmentController controller;

    public AppointmentListFrame(AppointmentController controller) throws HeadlessException {
        super(TITLE, DIMENSION);
        this.controller = controller;

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
        for (AppointmentController.Row row : controller.list()) {
            tableModel.addRow(toTableRow(row));
        }
    }

    static String[] toTableRow(AppointmentController.Row row) {
        return new String[] {
            row.date().format(AppointmentController.DATE_FORMAT),
            row.time().format(AppointmentController.TIME_FORMAT),
            orPlaceholder(row.patientName()),
            orPlaceholder(row.doctorName()),
            orPlaceholder(row.nurseName())
        };
    }

    private static String orPlaceholder(String name) {
        return name == null ? "-" : name;
    }

    private void addComponents() {
        backgroundPanel.add(scrollPane);
        super.addFormAt("North", backgroundPanel);
    }
}
