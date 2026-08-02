/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.control.dao.ConsultaDAO;
import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.model.Consulta;
import br.udesc.ceavi.progii.avicena.model.DiagnosticoPrimario;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroDiagnostico;
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
 * Listener para evento de clique do botão Gerar Receita
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class BtGerarReceiraListener implements ActionListener {

    private DiagnosticoPrimario diagnosticoPrimario;

    static List<String> buildReceitaLines(Consulta consulta) {
        if (consulta.getMedico() == null || consulta.getMedico().getEndereco() == null) {
            throw new IllegalStateException("Médico ou endereço do médico não cadastrado");
        }
        if (consulta.getPaciente() == null || consulta.getPaciente().getAddress() == null) {
            throw new IllegalStateException("Paciente ou endereço do paciente não cadastrado");
        }

        List<String> lines = new ArrayList<>();
        lines.add("Receira Médica - AVICENA");
        lines.add("------------------------------------------------------------------");
        lines.add("Dr. " + consulta.getMedico().getNome());
        lines.add("Rua. " + consulta.getMedico().getEndereco().getRua() + " , "
                + consulta.getMedico().getEndereco().getBairro());
        lines.add("Telefone: (479921-00081)");
        lines.add("CRM " + consulta.getMedico().getCrm());
        lines.add("------------------------------------------------------------------");
        lines.add("Paciente " + consulta.getPaciente().getName());
        lines.add("Rua " + consulta.getPaciente().getAddress().getStreet() + " , "
                + consulta.getPaciente().getAddress().getNeighborhood());
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
        DAO dao = new ConsultaDAO();
        List<Consulta> consultas = dao.getList();
        int selectedIndex =
                FrameCadastroDiagnostico.getInstance().getCbConsulta().getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= consultas.size()) {
            JOptionPane.showMessageDialog(FrameCadastroDiagnostico.getInstance(), "Selecione uma consulta válida");
            return;
        }
        Consulta consulta = consultas.get(selectedIndex);

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
