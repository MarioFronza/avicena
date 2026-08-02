/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersCRUD;

import br.udesc.ceavi.progii.avicena.control.dao.ConsultaDAO;
import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.control.dao.EnderecoDAO;
import br.udesc.ceavi.progii.avicena.control.dao.EnfermeiroDAO;
import br.udesc.ceavi.progii.avicena.control.dao.JPADAO;
import br.udesc.ceavi.progii.avicena.control.dao.MedicoDAO;
import br.udesc.ceavi.progii.avicena.control.dao.PacienteDAO;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.ListenerBtPesquisarPaciente;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuConsultaNovaListener;
import br.udesc.ceavi.progii.avicena.model.Consulta;
import br.udesc.ceavi.progii.avicena.model.Enfermeiro;
import br.udesc.ceavi.progii.avicena.model.EstadoPaciente;
import br.udesc.ceavi.progii.avicena.model.Medico;
import br.udesc.ceavi.progii.avicena.model.Paciente;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import br.udesc.ceavi.progii.avicena.view.frames.FrameConsultaNova;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Classe que define a implementação do Listener referente a Consulta
 * do tipo Agenda.
 * @author Adroan, Mário, Vini, Raphael
 * @since 18/05/2018
 * @version 1.0
 */
public class ListenerCRUDConsulta {
    
    private static ListenerCRUDConsulta instancia;
    
    private Consulta consulta;
    private final FrameCRUD tela;

    public ListenerCRUDConsulta(Consulta consulta, FrameCRUD tela) {
        this.consulta = consulta;
        this.tela = tela;
        
        addCRUDListeners();
    }
    
    public static ListenerCRUDConsulta getInstance(Consulta consulta, FrameCRUD tela){
        if(instancia == null){
            instancia = new ListenerCRUDConsulta(consulta, tela);    
        }
        return instancia;
    }
    
    private void addCRUDListeners(){
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
            int resposta = JOptionPane.showConfirmDialog(tela, "Deseja exluir este Consulta?", 
                                                                "Confirmar Exclusão", 
                                                                JOptionPane.YES_NO_OPTION, 
                                                                JOptionPane.QUESTION_MESSAGE);
            if (resposta == JOptionPane.YES_OPTION) {
                //chama a classe controller  que faz a exclusão do objeto  
                tela.limparCampos();
            }
        }
    
    }
    
    /**
     * Classe interna que define o Listener para o botão Novo
     * @author Mário, Vini, Adroan, Raphael
     * @version 1.0
     * @since 06/05/2018
     */
    private class btNovoActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(tela, "Nova consulta");
        }
    
    }
    
    /**
     * Classe interna que define o Listener para o botão Gravar
     * @author Mário, Vini, Adroan, Raphael
     * @version 1.0
     * @since 06/05/2018
     */
    List<Enfermeiro> enfermeiros;
    List<Medico> medicos;
    private class btGravarActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            
            DAO dao = new ConsultaDAO();
            JPADAO jpadao = new JPADAO();
            DAO daoMedico = new MedicoDAO();
            DAO daoEnfermeiro = new EnfermeiroDAO();
            DAO daoPaciente = new PacienteDAO();
            enfermeiros = daoEnfermeiro.getList();
            medicos = daoMedico.getList();
            consulta = new Consulta();
            FrameConsultaNova tela = FrameConsultaNova.getInstance();
            consulta.setHora(tela.getTfHora().getText());
            consulta.setData(tela.getTfData().getText());
            consulta.setSintomas(tela.getTfSintomas().getText());
            consulta.setEstadoPaciente(EstadoPaciente.NAOURGENTE);
            consulta.setEnfermeiro(enfermeiros.get(tela.getCbEnfermeiro().getSelectedIndex()));
            System.out.println(tela.getCbEnfermeiro().getSelectedIndex());
            consulta.setMedico(medicos.get(tela.getCbMedico().getSelectedIndex()));
            System.out.println(tela.getCbMedico().getSelectedIndex());
            List<Paciente> pacientes = daoPaciente.getList();
                for (int i = 0; i < pacientes.size(); i++) {
                     if(pacientes.get(i).getCpf().equals(tela.getTfPaciente().getText())){
                           consulta.setPaciente(pacientes.get(i));
                           System.out.println("paciente encontrado");
                           break;
                         }
                 }
            try {
                dao.inserir(consulta);
                jpadao.inserir(consulta);
                JOptionPane.showMessageDialog(tela, "Consulta Gravada");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(tela, "CPF incorreto");
            }
            
            
           
            tela.limparCampos();
        }
    
    }
    
}
