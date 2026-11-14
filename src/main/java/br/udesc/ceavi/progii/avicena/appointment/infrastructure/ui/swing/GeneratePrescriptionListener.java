package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.EntityManager;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Generates the prescription PDF when the "Generate Receipt" button is clicked.
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class GeneratePrescriptionListener implements ActionListener {

    private final DiagnosisRegistrationFrame diagnosisFrame;

    public GeneratePrescriptionListener(DiagnosisRegistrationFrame diagnosisFrame) {
        this.diagnosisFrame = diagnosisFrame;
    }

    static List<String> buildPrescriptionLines(AppointmentEntity appointment) {
        if (appointment.getDoctor() == null || appointment.getDoctor().getAddress() == null) {
            throw new IllegalStateException("Médico ou endereço do médico não cadastrado");
        }
        if (appointment.getPatient() == null || appointment.getPatient().getAddress() == null) {
            throw new IllegalStateException("Paciente ou endereço do paciente não cadastrado");
        }

        List<String> lines = new ArrayList<>();
        lines.add("Receira Médica - AVICENA");
        lines.add("------------------------------------------------------------------");
        lines.add("Dr. " + appointment.getDoctor().getName());
        lines.add("Rua. " + appointment.getDoctor().getAddress().getStreet() + " , "
                + appointment.getDoctor().getAddress().getNeighborhood());
        lines.add("Telefone: (479921-00081)");
        lines.add("CRM " + appointment.getDoctor().getCrm());
        lines.add("------------------------------------------------------------------");
        lines.add("Paciente " + appointment.getPatient().getName());
        lines.add("Rua " + appointment.getPatient().getAddress().getStreet() + " , "
                + appointment.getPatient().getAddress().getNeighborhood());
        lines.add("Remédios:");
        lines.add(" ");
        lines.add("________________________");
        lines.add(" ");
        lines.add("________________________");
        lines.add(" ");
        lines.add("________________________");
        lines.add(" ");
        lines.add("Assinatura do profissional:");
        lines.add(" ");
        lines.add("_______________________________________________________");
        return lines;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EntityManager entityManager = PersistenceConfig.entityManagerFactory().createEntityManager();
        List<AppointmentEntity> appointments;
        try {
            appointments = entityManager
                    .createQuery("SELECT a FROM AppointmentEntity a", AppointmentEntity.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
        int selectedIndex = diagnosisFrame.getAppointmentComboBox().getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= appointments.size()) {
            JOptionPane.showMessageDialog(diagnosisFrame, "Selecione uma consulta válida");
            return;
        }
        AppointmentEntity appointment = appointments.get(selectedIndex);

        List<String> lines;
        try {
            lines = buildPrescriptionLines(appointment);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(diagnosisFrame, ex.getMessage());
            return;
        }

        boolean generated = false;
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Receira-Avicena.pdf"));
            document.open();
            for (String line : lines) {
                document.add(new Paragraph(line));
            }
            generated = true;
        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(GeneratePrescriptionListener.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            document.close();
        }

        if (!generated) {
            return;
        }

        try {
            Desktop.getDesktop().open(new File("Receira-Avicena.pdf"));
        } catch (IOException ex) {
            Logger.getLogger(GeneratePrescriptionListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
