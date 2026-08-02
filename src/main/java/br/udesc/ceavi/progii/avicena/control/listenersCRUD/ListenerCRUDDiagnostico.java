/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersCRUD;

import br.udesc.ceavi.progii.avicena.control.dao.ConsultaDAO;
import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.control.dao.JPADAO;
import br.udesc.ceavi.progii.avicena.model.Consulta;
import br.udesc.ceavi.progii.avicena.model.DiagnosticoFinal;
import br.udesc.ceavi.progii.avicena.model.DiagnosticoPrimario;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroDiagnostico;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Classe que define a implementação do Listener referente a Diagnóstico Final
 * do tipo Agenda.
 * @author Adroan, Mário, Vini, Raphael
 * @since 18/05/2018
 * @version 1.0
 */
public class ListenerCRUDDiagnostico {
    
    private static ListenerCRUDDiagnostico instancia;
    
    private DiagnosticoFinal diagnosticoFinal;
    private DiagnosticoPrimario diagnosticoPrimario;
    private final FrameCRUD tela;

    public ListenerCRUDDiagnostico(DiagnosticoPrimario diagnosticoPrimario, DiagnosticoFinal diagnosticoFinal, FrameCRUD tela) {
        this.diagnosticoFinal = diagnosticoFinal;
        this.diagnosticoPrimario = diagnosticoPrimario;
        this.tela = tela;
        
        addCRUDListeners();
    }

    public ListenerCRUDDiagnostico() {
        super();
        this.diagnosticoFinal = null;
        this.tela = null;
    }
    
    

    public DiagnosticoFinal getDiagnosticoFinal() {
        return diagnosticoFinal;
    }

    public DiagnosticoPrimario getDiagnosticoPrimario() {
        return diagnosticoPrimario;
    }
    
    
    
    public static ListenerCRUDDiagnostico getInstance(){
        return instancia;
    }
    
    public static ListenerCRUDDiagnostico getInstance(DiagnosticoPrimario diagnosticoPrimario, DiagnosticoFinal diagnosticoFinal, FrameCRUD tela){
        if(instancia == null){
            instancia = new ListenerCRUDDiagnostico(diagnosticoPrimario, diagnosticoFinal, tela);
        }
        return instancia;
    }

    private void addCRUDListeners() {
               JButton botao;
        
        //Carrega e define o comportamento para o botão Cancelar
        botao = tela.getPanelBotoesCRUD().getBtCancelar();
        botao.addActionListener(new btCancelarActionListener());
        //Carrega e define o comportamento para o botão Novo
        botao = tela.getPanelBotoesCRUD().getBtNovo();
        botao.addActionListener(new btNovoActionListener());
        //Carrega e define o comportamento para o botão Excluir
        botao = tela.getPanelBotoesCRUD().getBtExcluir();
        botao.addActionListener(new btExcluirActionListener());
        //Carrega e define o comportamento para o botão Gravar
        botao = tela.getPanelBotoesCRUD().getBtGravar();
        botao.addActionListener(new btGravarActionListener());
    }
    
    /**
     * Classe interna que define o Listener para o botão Cancelar
     * @author Mário, Vini, Adroan, Raphael
     * @version 1.0
     * @since 06/05/2018
     */
    private class btCancelarActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int resposta = JOptionPane.showConfirmDialog(tela, "Deseja realmente fechar a janela?", 
                                                                "Confirmar Fechamento", 
                                                                JOptionPane.YES_NO_OPTION, 
                                                                JOptionPane.QUESTION_MESSAGE);
            if (resposta == JOptionPane.YES_OPTION) {
                //chama a classe controller  que faz a exclusão do objeto    
                tela.dispose();
                tela.limparCampos();
            }        
        }
    }
   /**
     * Classe interna que define o Listener para o botão Excluir
     * @author Mário, Vini, Adroan, Raphael
     * @version 1.0
     * @since 06/05/2018
     */
    private class btExcluirActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int resposta = JOptionPane.showConfirmDialog(tela, "Deseja exluir este Diagnóstico final?", 
                                                                "Confirmar Exclusão", 
                                                                JOptionPane.YES_NO_OPTION, 
                                                                JOptionPane.QUESTION_MESSAGE);
            if (resposta == JOptionPane.YES_OPTION) {
                //chama a classe controller  que faz a exclusão do objeto  
                tela.limparCampos();
            }
        }
       }
        private class btNovoActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(tela, "Novo Diagnóstico");
        }
    
    }
    
    /**
     * Classe interna que define o Listener para o botão Gravar
     * @author Mário, Vini, Adroan, Raphael
     * @version 1.0
     * @since 06/05/2018
     */
    private class btGravarActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            FrameCadastroDiagnostico frame = FrameCadastroDiagnostico.getInstance();
            JPADAO jpadao = new JPADAO();
            DAO dao = new ConsultaDAO();
            List<Consulta> consultas = dao.getList();
            diagnosticoPrimario = new DiagnosticoPrimario();
            diagnosticoFinal = new DiagnosticoFinal();
            
            diagnosticoPrimario.setTemperatura(Integer.parseInt(frame.getTfTemperatura().getText()));
            diagnosticoPrimario.setDescricao("Padrão");
            diagnosticoPrimario.setHistoricoRemedio("Padrão");
            diagnosticoPrimario.setPressao(Integer.parseInt(frame.getTfPressao().getText()));
            diagnosticoPrimario.setPeso(Integer.parseInt(frame.getTfPeso().getText()));
            diagnosticoPrimario.setAltura(Integer.parseInt(frame.getTfAltura().getText()));
            
            diagnosticoFinal.setRemedios(frame.getTfRemedio().getText());
            diagnosticoFinal.setDescricao("Padrão");
            diagnosticoFinal.setDoenca(frame.getTfDoenca().getText());
            diagnosticoFinal.setExames("Exame paddão");
            
            diagnosticoPrimario.setDiagnosticoFinal(diagnosticoFinal);
            diagnosticoPrimario.setConsulta(consultas.get(frame.getCbConsulta().getSelectedIndex()));

            try {
                jpadao.inserir(diagnosticoPrimario);
                JOptionPane.showMessageDialog(tela, "Diganóstico gravado");
                tela.limparCampos();
            } catch (Exception ex) {
                Logger.getLogger(ListenerCRUDDiagnostico.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    
    }
    
    
    
    
    
   }
  
    
    
    

