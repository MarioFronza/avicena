package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.appointment.domain.InvalidAppointmentDataException;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller.AppointmentController;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
    private final AppointmentController controller;

    public GeneratePrescriptionListener(DiagnosisRegistrationFrame diagnosisFrame, AppointmentController controller) {
        this.diagnosisFrame = diagnosisFrame;
        this.controller = controller;
    }

    static List<String> buildPrescriptionLines(AppointmentController.Prescription prescription) {
        List<String> lines = new ArrayList<>();
        lines.add("Medical Prescription - AVICENA");
        lines.add("------------------------------------------------------------------");
        lines.add("Dr. " + prescription.doctorName());
        lines.add("Street: " + prescription.doctorStreet() + " , " + prescription.doctorNeighborhood());
        lines.add("Phone: (479921-00081)");
        lines.add("CRM " + prescription.crm());
        lines.add("------------------------------------------------------------------");
        lines.add("Patient: " + prescription.patientName());
        lines.add("Street: " + prescription.patientStreet() + " , " + prescription.patientNeighborhood());
        lines.add("Medications:");
        lines.add(" ");
        lines.add("________________________");
        lines.add(" ");
        lines.add("________________________");
        lines.add(" ");
        lines.add("________________________");
        lines.add(" ");
        lines.add("Practitioner's signature:");
        lines.add(" ");
        lines.add("_______________________________________________________");
        return lines;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<String> lines;
        try {
            lines = buildPrescriptionLines(controller.prescription(diagnosisFrame.getSelectedAppointmentId()));
        } catch (InvalidAppointmentDataException ex) {
            JOptionPane.showMessageDialog(diagnosisFrame, ex.getMessage());
            return;
        }

        boolean generated = false;
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Avicena-Prescription.pdf"));
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
            Desktop.getDesktop().open(new File("Avicena-Prescription.pdf"));
        } catch (IOException ex) {
            Logger.getLogger(GeneratePrescriptionListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
