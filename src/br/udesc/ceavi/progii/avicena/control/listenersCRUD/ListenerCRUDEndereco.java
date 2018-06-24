/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersCRUD;

import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.control.dao.EnderecoDAO;
import br.udesc.ceavi.progii.avicena.control.dao.JPADAO;
import br.udesc.ceavi.progii.avicena.model.Endereco;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroEndereco;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Classe que define a implementação do Listener referente a Endereço
 * do tipo Agenda.
 * @author Adroan, Mário, Vini, Raphael
 * @since 18/05/2018
 * @version 1.0
 */
public class ListenerCRUDEndereco {
    
    private static ListenerCRUDEndereco instancia;
    
    private Endereco endereco;
    private final FrameCRUD tela;

    public ListenerCRUDEndereco(Endereco endereco, FrameCRUD tela) {
        this.endereco = endereco;
        this.endereco = new Endereco();
        this.tela = tela;
        
        addCRUDListeners();
    }
    
    public Endereco getEndereco(){
        return this.endereco;
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
    
     public static ListenerCRUDEndereco getInstance(Endereco endereco, FrameCRUD tela){
        if(instancia == null){
            instancia = new ListenerCRUDEndereco(endereco, tela);    
        }
        return instancia;
    }
     
     public static ListenerCRUDEndereco getInstance(){
       
        return instancia;
    }
    
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
            
            DAO dao = new EnderecoDAO();
            JPADAO jpadao = new JPADAO();
            endereco = new Endereco();
            FrameCadastroEndereco telaFrame = FrameCadastroEndereco.getInstance();
            endereco.setBairro(telaFrame.getTfBairro().getText());
            endereco.setCep(telaFrame.getTfCep().getText());
            endereco.setCidade(telaFrame.getTfCidade().getText());
            endereco.setComplemento(telaFrame.getTfComplemento().getText());
            endereco.setNumero(Integer.parseInt(telaFrame.getTfNumero().getText()));
            endereco.setRua(telaFrame.getTfRua().getText());
            try {
                dao.inserir(endereco);
                jpadao.inserir(endereco);
                JOptionPane.showMessageDialog(telaFrame, "Endereço gravado");
                tela.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(tela, ex.getMessage());
            }
         
            
            tela.limparCampos();
        }
    
    }
    
    
    
}
