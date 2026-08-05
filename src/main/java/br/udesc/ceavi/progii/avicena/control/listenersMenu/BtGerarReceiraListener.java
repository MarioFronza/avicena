/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.model.DiagnosticoPrimario;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroDiagnostico;
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
 * Listener para evento de clique do botão Gerar Receita
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class BtGerarReceiraListener implements ActionListener {

    private DiagnosticoPrimario diagnosticoPrimario;

    static List<String> buildReceitaLines(AppointmentEntity consulta) {
        if (consulta.getDoctor() == null || consulta.getDoctor().getAddress() == null) {
            throw new IllegalStateException("Médico ou endereço do médico não cadastrado");
        }
        if (consulta.getPatient() == null || consulta.getPatient().getAddress() == null) {
            throw new IllegalStateException("Paciente ou endereço do paciente não cadastrado");
        }

        List<String> lines = new ArrayList<>();
        lines.add("Receira Médica - AVICENA");
        lines.add("------------------------------------------------------------------");
        lines.add("Dr. " + consulta.getDoctor().getName());
        lines.add("Rua. " + consulta.getDoctor().getAddress().getStreet() + " , "
                + consulta.getDoctor().getAddress().getNeighborhood());
        lines.add("Telefone: (479921-00081)");
        lines.add("CRM " + consulta.getDoctor().getCrm());
        lines.add("------------------------------------------------------------------");
        lines.add("Paciente " + consulta.getPatient().getName());
        lines.add("Rua " + consulta.getPatient().getAddress().getStreet() + " , "
                + consulta.getPatient().getAddress().getNeighborhood());
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
        EntityManager entityManager =
                PersistenceConfig.createEntityManagerFactory().createEntityManager();
        List<AppointmentEntity> consultas;
        try {
            consultas = entityManager
                    .createQuery("SELECT a FROM AppointmentEntity a", AppointmentEntity.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
        int selectedIndex =
                FrameCadastroDiagnostico.getInstance().getCbConsulta().getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= consultas.size()) {
            JOptionPane.showMessageDialog(FrameCadastroDiagnostico.getInstance(), "Selecione uma consulta válida");
            return;
        }
        AppointmentEntity consulta = consultas.get(selectedIndex);

        List<String> lines;
        try {
            lines = buildReceitaLines(consulta);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(FrameCadastroDiagnostico.getInstance(), ex.getMessage());
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
            Logger.getLogger(BtGerarReceiraListener.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            document.close();
        }

        if (!generated) {
            return;
        }

        try {
            Desktop.getDesktop().open(new File("Receira-Avicena.pdf"));
        } catch (IOException ex) {
            Logger.getLogger(BtGerarReceiraListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
