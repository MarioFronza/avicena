/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersCRUD;

import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.control.dao.EnfermeiroDAO;
import br.udesc.ceavi.progii.avicena.control.dao.JPADAO;
import static br.udesc.ceavi.progii.avicena.model.Consulta_.medico;
import br.udesc.ceavi.progii.avicena.model.Enfermeiro;
import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroEnfermeiro;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Classe que define a implementação do Listener referente a Enfermeiro
 * do tipo Agenda.
 * @author Adroan, Mário, Vini, Raphael
 * @since 18/05/2018
 * @version 1.0
 */
public class ListenerCRUDEnfermeiro {
    
    private static ListenerCRUDEnfermeiro instancia;
    
    private Enfermeiro enfermeiro;
    private final FrameCRUD tela;

    public ListenerCRUDEnfermeiro(Enfermeiro enfermeiro, FrameCRUD tela) {
        this.enfermeiro = enfermeiro;
        this.tela = tela;
        
        addCRUDListeners();
    }
    
    public static ListenerCRUDEnfermeiro getInstance(Enfermeiro atendente, FrameCRUD tela){
        if(instancia == null){
            instancia = new ListenerCRUDEnfermeiro(atendente, tela);    
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
            
            DAO dao = new EnfermeiroDAO();
            JPADAO jpadao = new JPADAO();
            
            enfermeiro = new Enfermeiro();
            FrameCadastroEnfermeiro tela = FrameCadastroEnfermeiro.getInstance();
            enfermeiro.setNome(tela.getTfNome().getText());
            enfermeiro.setCpf(tela.getTfCpf().getText());
            enfermeiro.setTelefone(tela.getTfTelefone().getText());
            int hrCursadas = Integer.parseInt(tela.getTfHrCursadas().getText());
            enfermeiro.setHrCursadas(hrCursadas);
            enfermeiro.setFormacao(tela.getTfFormacao().getText());
            int estado = FrameCadastroEnfermeiro.getInstance().getCbEstadoCivil().getSelectedIndex();
            switch (estado) {
                case 0:
                    enfermeiro.setEstadoCivil(EstadoCivil.SOLTEIRO);
                    break;
                case 1:
                    enfermeiro.setEstadoCivil(EstadoCivil.CASADO);
                    break;
                case 2:
                    enfermeiro.setEstadoCivil(EstadoCivil.DIVORCIADO);
                    break;
                case 3:
                    enfermeiro.setEstadoCivil(EstadoCivil.VIUVO);
                    break;
                case 4:
                    enfermeiro.setEstadoCivil(EstadoCivil.OUTRO);
                    break;
            }
            ListenerCRUDEndereco frameCRUD = ListenerCRUDEndereco.getInstance();
            enfermeiro.setEndereco(frameCRUD.getEndereco());
            try {
                dao.inserir(enfermeiro);
                jpadao.inserir(enfermeiro);
            } catch (Exception ex) {
                Logger.getLogger(ListenerCRUDEnfermeiro.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            JOptionPane.showMessageDialog(tela, "Enfermeiro Gravado");
            tela.limparCampos();
        }
    
    }
    
}
