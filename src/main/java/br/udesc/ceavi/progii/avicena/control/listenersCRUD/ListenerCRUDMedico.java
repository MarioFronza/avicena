/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersCRUD;

import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.control.dao.JPADAO;
import br.udesc.ceavi.progii.avicena.control.dao.MedicoDAO;
import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import br.udesc.ceavi.progii.avicena.model.Medico;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroMedico;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroPaciente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Classe que define a implementação do Listener referente a Médico
 * do tipo Agenda.
 * @author Adroan, Mário, Vini, Raphael
 * @since 18/05/2018
 * @version 1.0
 */
public class ListenerCRUDMedico {
    
    private static ListenerCRUDMedico instancia;
    
    private Medico medico;
    private final FrameCRUD tela;

    public ListenerCRUDMedico(Medico medico, FrameCRUD tela) {
        this.medico = medico;
        this.tela = tela;
        
        addCRUDListeners();
    }
    
    public static ListenerCRUDMedico getInstance(Medico medico, FrameCRUD tela){
        if(instancia == null){
            instancia = new ListenerCRUDMedico(medico, tela);    
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
            int resposta = JOptionPane.showConfirmDialog(tela, "Deseja exluir este Médico?", 
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
            JOptionPane.showMessageDialog(tela, "Novo Medico");
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
            
            DAO dao = new MedicoDAO();
            JPADAO jpa = new JPADAO();
            medico = new Medico();
            medico.setNome(FrameCadastroMedico.getInstance().getTfNome().getText());
            medico.setCpf(FrameCadastroMedico.getInstance().getTfCpf().getText());
            medico.setTelefone(FrameCadastroMedico.getInstance().getTfTelefone().getText());
            medico.setEspecializacao(FrameCadastroMedico.getInstance().getTfEspecialidade().getText());
            medico.setCrm(FrameCadastroMedico.getInstance().getTfCrm().getText());
            int estado = FrameCadastroMedico.getInstance().getCbEstadoCivil().getSelectedIndex();
            switch (estado) {
                case 0:
                    medico.setEstadoCivil(EstadoCivil.SOLTEIRO);
                    break;
                case 1:
                    medico.setEstadoCivil(EstadoCivil.CASADO);
                    break;
                case 2:
                    medico.setEstadoCivil(EstadoCivil.DIVORCIADO);
                    break;
                case 3:
                    medico.setEstadoCivil(EstadoCivil.VIUVO);
                    break;
                case 4:
                    medico.setEstadoCivil(EstadoCivil.OUTRO);
                    break;
            }
            ListenerCRUDEndereco frameCRUD = ListenerCRUDEndereco.getInstance();
            medico.setEndereco(frameCRUD.getEndereco());
            try {
                dao.inserir(medico);
                jpa.inserir(medico);
                JOptionPane.showMessageDialog(tela, "Médico Gravado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(tela, ex.getMessage());
            }
            

            tela.limparCampos();
        }
    
    }
    
    
    
}
