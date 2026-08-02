/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;


import br.udesc.ceavi.progii.avicena.control.dao.ConsultaDAO;
import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.model.Consulta;
import br.udesc.ceavi.progii.avicena.model.DiagnosticoFinal;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Listener para evento de clique do botão Gerar Receita
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class BtGerarReceiraListener implements ActionListener{

    private DiagnosticoPrimario diagnosticoPrimario;
    DAO dao = new ConsultaDAO();
    List<Consulta> consultas = dao.getList();
    Consulta consulta;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        consulta = consultas.get(FrameCadastroDiagnostico.getInstance().getCbConsulta().getSelectedIndex());
        
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Receira-Avicena.pdf"));
            document.open();
            document.add(new Paragraph("Receira Médica - AVICENA"));
            document.add(new Paragraph("------------------------------------------------------------------"));
            document.add(new Paragraph("Dr. "+consulta.getMedico().getNome()));
            document.add(new Paragraph("Rua. "+consulta.getMedico().getEndereco().getRua()+" , "+consulta.getMedico().getEndereco().getBairro()));
            document.add(new Paragraph("Telefone: (479921-00081)"));
            document.add(new Paragraph("CRM "+consulta.getMedico().getCrm()));
            document.add(new Paragraph("------------------------------------------------------------------"));
            document.add(new Paragraph("Paciente "+ consulta.getPaciente().getNome()));
            document.add(new Paragraph("Rua "+consulta.getPaciente().getEndereco().getRua()+" , "+consulta.getPaciente().getEndereco().getBairro()));
            document.add(new Paragraph("Remédios:"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("________________________"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("________________________"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("________________________"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Assinatura do profissional:"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("_______________________________________________________"));
            
        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(BtGerarReceiraListener.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            document.close();
        }
        
        try {
            Desktop.getDesktop().open(new File("Receira-Avicena.pdf"));
        } catch (IOException ex) {
            Logger.getLogger(BtGerarReceiraListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    

    
}
