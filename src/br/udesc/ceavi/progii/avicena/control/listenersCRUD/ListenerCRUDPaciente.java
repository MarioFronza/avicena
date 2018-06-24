/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersCRUD;

import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.control.dao.JPADAO;
import br.udesc.ceavi.progii.avicena.control.dao.PacienteDAO;
import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import br.udesc.ceavi.progii.avicena.model.Paciente;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroPaciente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Classe que define a implementação do Listener referente a Paciente
 * do tipo Agenda.
 * @author Adroan, Mário, Vini, Raphael
 * @since 18/05/2018
 * @version 1.0
 */
public class ListenerCRUDPaciente {
    
    private static ListenerCRUDPaciente instancia;
    
    private Paciente paciente;
    private final FrameCRUD tela;
    
    public void setPaciente(Paciente paciente){
        this.paciente = paciente; 
    }

    public ListenerCRUDPaciente(Paciente paciente, FrameCRUD tela) {
        this.paciente = paciente;
        this.paciente = new Paciente();
        this.tela = tela;
        
        addCRUDListeners();
    }
    
    public Paciente getPaciente(){
                return paciente;
     }
    
    public static ListenerCRUDPaciente getInstance(Paciente paciente, FrameCRUD tela){
        if(instancia == null){
            instancia = new ListenerCRUDPaciente(paciente, tela);    
        }
        return instancia;
    }
    
    public static ListenerCRUDPaciente getInstance(){

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
            int resposta = JOptionPane.showConfirmDialog(tela, "Deseja exluir este Paciente?", 
                                                                "Confirmar Exclusão", 
                                                                JOptionPane.YES_NO_OPTION, 
                                                                JOptionPane.QUESTION_MESSAGE);
            if (resposta == JOptionPane.YES_OPTION) {
                JPADAO jpadao = new JPADAO();
                    try {
                    jpadao.deletar(paciente);
                } catch (Exception ex) {
                    Logger.getLogger(ListenerCRUDPaciente.class.getName()).log(Level.SEVERE, null, ex);
                }
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

            JPADAO jpadao = new JPADAO();
            DAO dao = new PacienteDAO();
            paciente.setNome(FrameCadastroPaciente.getInstance().getTfNome().getText());
            paciente.setCpf(FrameCadastroPaciente.getInstance().getTfCpf().getText());
            paciente.setTelefone(FrameCadastroPaciente.getInstance().getTfTelefone().getText());
            int estado = FrameCadastroPaciente.getInstance().getCbEstadoCivil().getSelectedIndex();
            switch (estado) {
                case 0:
                    paciente.setEstadoCivil(EstadoCivil.SOLTEIRO);
                    break;
                case 1:
                    paciente.setEstadoCivil(EstadoCivil.CASADO);
                    break;
                case 2:
                    paciente.setEstadoCivil(EstadoCivil.DIVORCIADO);
                    break;
                case 3:
                    paciente.setEstadoCivil(EstadoCivil.VIUVO);
                    break;
                case 4:
                    paciente.setEstadoCivil(EstadoCivil.OUTRO);
                    break;
            }
            ListenerCRUDEndereco frameCRUD = ListenerCRUDEndereco.getInstance();
            paciente.setEndereco(frameCRUD.getEndereco());
 
            try {
                dao.inserir(paciente);
                jpadao.inserir(paciente);
                JOptionPane.showMessageDialog(tela, "Paciente Gravado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(tela, ex.getMessage());
               
            }
            
            tela.limparCampos();
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
            DAO dao = new PacienteDAO();
            JPADAO jpadao = new JPADAO();
            paciente = new Paciente();
 
            paciente.setNome(FrameCadastroPaciente.getInstance().getTfNome().getText());
            paciente.setCpf(FrameCadastroPaciente.getInstance().getTfCpf().getText());
            paciente.setTelefone(FrameCadastroPaciente.getInstance().getTfTelefone().getText());
            int estado = FrameCadastroPaciente.getInstance().getCbEstadoCivil().getSelectedIndex();
            switch (estado) {
                case 0:
                    paciente.setEstadoCivil(EstadoCivil.SOLTEIRO);
                    break;
                case 1:
                    paciente.setEstadoCivil(EstadoCivil.CASADO);
                    break;
                case 2:
                    paciente.setEstadoCivil(EstadoCivil.DIVORCIADO);
                    break;
                case 3:
                    paciente.setEstadoCivil(EstadoCivil.VIUVO);
                    break;
                case 4:
                    paciente.setEstadoCivil(EstadoCivil.OUTRO);
                    break;
            }
            ListenerCRUDEndereco frameCRUD = ListenerCRUDEndereco.getInstance();
            paciente.setEndereco(frameCRUD.getEndereco());
            //verificar estado
            //paciente.setEstadoCivil();
            try {
                dao.inserir(paciente);
                jpadao.inserir(paciente);
                JOptionPane.showMessageDialog(tela, "Paciente Gravado");
                tela.limparCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(tela, ex.getMessage());
               
            }

            
        }
        
    
    }
    
    
    
}
