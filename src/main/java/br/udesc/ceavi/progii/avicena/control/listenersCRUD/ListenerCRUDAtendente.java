/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersCRUD;

import br.udesc.ceavi.progii.avicena.control.dao.AtendenteDAO;
import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.control.dao.JPADAO;
import br.udesc.ceavi.progii.avicena.model.Atendente;
import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroAtendente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Classe que define a implementação do Listener referente a Atendente
 * do tipo Agenda.
 * @author Adroan, Mário, Vini, Raphael
 * @since 18/05/2018
 * @version 1.0
 */
public class ListenerCRUDAtendente {
    
    private static ListenerCRUDAtendente instancia;
    
    private Atendente atendente;
    private final FrameCRUD tela;

    public ListenerCRUDAtendente(Atendente atendente, FrameCRUD tela) {
        this.atendente = atendente;
        this.tela = tela;
        
        addCRUDListeners();
    }
    
    public static ListenerCRUDAtendente getInstance(Atendente atendente, FrameCRUD tela){
        if(instancia == null){
            instancia = new ListenerCRUDAtendente(atendente, tela);    
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
            int resposta = JOptionPane.showConfirmDialog(tela, "Deseja exluir este Paciente?", 
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
            
            DAO dao = new AtendenteDAO();
            JPADAO jpadao = new JPADAO();
            
            atendente = new Atendente();
            FrameCadastroAtendente telaFrame = FrameCadastroAtendente.getInstance();
            atendente.setNome(telaFrame.getTfNome().getText());
            atendente.setTelefone(telaFrame.getTfTelefone().getText());
            atendente.setCpf(telaFrame.getTfCpf().getText());
            atendente.setHoraExtra(Integer.parseInt(telaFrame.getTfHoraExtra().getText()));
            atendente.setNumeroCarteiraDeTrabalho(Integer.parseInt(telaFrame.getTfNumCartTrabalho().getText()));
            atendente.setSalario(Float.parseFloat(telaFrame.getTfSalario().getText()));
            int estado = FrameCadastroAtendente.getInstance().getCbEstadoCivil().getSelectedIndex();
            switch (estado) {
                case 0:
                    atendente.setEstadoCivil(EstadoCivil.SOLTEIRO);
                    break;
                case 1:
                    atendente.setEstadoCivil(EstadoCivil.CASADO);
                    break;
                case 2:
                    atendente.setEstadoCivil(EstadoCivil.DIVORCIADO);
                    break;
                case 3:
                    atendente.setEstadoCivil(EstadoCivil.VIUVO);
                    break;
                case 4:
                    atendente.setEstadoCivil(EstadoCivil.OUTRO);
                    break;
            }
            ListenerCRUDEndereco frameCRUD = ListenerCRUDEndereco.getInstance();
            atendente.setEndereco(frameCRUD.getEndereco());
            try {
                dao.inserir(atendente);
                jpadao.inserir(atendente);
                JOptionPane.showMessageDialog(tela, "Atendente Gravado");
                tela.limparCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(tela, ex.getMessage());
            }
        }
    
    }
    
}
