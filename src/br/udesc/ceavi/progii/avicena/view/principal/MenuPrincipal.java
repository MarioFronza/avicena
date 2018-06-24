/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.view.principal;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuAgendaListener;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuSobreListener;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuCadAtendenteListener;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuCadDiagnosticoListener;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuCadEnfermeiroListener;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuCadMedicoListener;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuCadPacienteListener;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuConsultaListarListener;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuConsultaNovaListener;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuHistoricoListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Esta classe define o menu principal da aplicação
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class MenuPrincipal extends JMenuBar{
    
    private static MenuPrincipal instancia;
    
    private JMenu menuPaciente;
    private JMenuItem menuItemCadDiagnostico;
    private JMenuItem menuItemCadAgenda;
    private JMenuItem menuItemCadHistorico;
    
    private JMenu menuCadastro;
    private JMenuItem menuItemCadPaciente;
    private JMenuItem menuItemCadAtendente;
    private JMenuItem menuItemCadMedico;
    private JMenuItem menuItemCadEnfermeiro;
    
    private JMenu menuConsulta;
    private JMenuItem menuItemNovaConsulta;
    private JMenuItem menuItemListaConsulta;

    private JMenu menuAjuda;
    private JMenuItem menuItemSobre;
    
    private static FrameSistema frame;

    public FrameSistema getFrame() {
        return frame;
    }
    
     public static MenuPrincipal getInstance(){
         if(instancia == null)
            instancia = new MenuPrincipal(frame);
         
        return instancia;
    }
    
    public MenuPrincipal(FrameSistema frame) {
        super();
        
        this.frame = frame;
        
        initalizeComponets();
        ConstruirMenu();
        
        adicionaListenersMenu();
        
    }

    private void initalizeComponets() {
        
        this.setPreferredSize(new Dimension(30,30));
        
        menuPaciente = new JMenu("Paciente");
        menuItemCadDiagnostico = new JMenuItem("Diagnóstico");
        menuItemCadHistorico = new JMenuItem("Histórico");
        menuItemCadAgenda = new JMenuItem("Agenda");
        
        menuCadastro = new JMenu("Cadastro");
        menuItemCadPaciente = new JMenuItem("Cadastro Paciente");
        menuItemCadAtendente = new JMenuItem("Cadastro Atendente");
        menuItemCadMedico = new JMenuItem("Cadastro Médico");
        menuItemCadEnfermeiro = new JMenuItem("Cadastro Enfermeiro");
        
        menuConsulta = new JMenu("Consulta");
        menuItemNovaConsulta = new JMenuItem("Nova Consulta");
        menuItemListaConsulta = new JMenuItem("Lista Consulta");
        
        menuAjuda = new JMenu("Ajuda");
        menuItemSobre = new JMenuItem("Sobre");
    }

    private void ConstruirMenu() {
        
        menuPaciente.setIcon(new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/user_red.png")));
        menuItemCadDiagnostico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/report_edit.png")));
        menuItemCadHistorico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/status_away.png")));
        menuItemCadAgenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/report_user.png")));
        menuPaciente.setToolTipText("Cadastro de Diagnóstico,...");
        menuItemCadDiagnostico.setToolTipText("Cadastro dos diagnósticos primário e final");
        menuItemCadHistorico.setToolTipText("Em desenvolvimento...");
        menuItemCadAgenda.setToolTipText("Em desenvolvimento...");
        menuItemCadAgenda.setEnabled(false);
        menuItemCadHistorico.setEnabled(false);
        menuPaciente.add(menuItemCadDiagnostico);
        menuPaciente.add(menuItemCadAgenda);
        menuPaciente.add(menuItemCadHistorico);
        
        super.add(menuPaciente);
        
        menuCadastro.setIcon(new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/user_add.png")));
        menuItemCadPaciente.setIcon(new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/vcard_add.png")));
        menuItemCadAtendente.setIcon(new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/vcard_add.png")));
        menuItemCadMedico.setIcon(new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/vcard_add.png")));
        menuItemCadEnfermeiro.setIcon(new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/vcard_add.png")));
        menuCadastro.setToolTipText("Cadastros gerais");
        menuItemCadPaciente.setToolTipText("Cadastra o paciente e busca por seu CPF");
        menuItemCadAtendente.setToolTipText("Cadastra a atendente, secretaria,...");
        menuItemCadMedico.setToolTipText("Cadastra o médico e sua especialidade");
        menuItemCadEnfermeiro.setToolTipText("Cadastra o enfermeiro ");
        menuCadastro.add(menuItemCadPaciente);
        menuCadastro.add(menuItemCadAtendente);
        menuCadastro.add(menuItemCadMedico);
        menuCadastro.add(menuItemCadEnfermeiro);
        
        super.add(menuCadastro);
        
        menuConsulta.setIcon(new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/pill_add.png")));
        menuItemListaConsulta.setIcon(new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/table_gear.png")));
        menuItemNovaConsulta.setIcon(new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/table_add.png")));
        menuConsulta.setToolTipText("Cadastro e lista de consultas");
        menuItemListaConsulta.setToolTipText("Mostra as consultas cadastradas");
        menuItemNovaConsulta.setToolTipText("Cadastra uma nova consulta");
        menuConsulta.add(menuItemNovaConsulta);
        menuConsulta.add(menuItemListaConsulta);
        
        super.add(menuConsulta);
        
        menuAjuda.setIcon(new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/help.png")));
        menuItemSobre.setIcon(new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/information.png")));
        menuAjuda.setToolTipText("Não ajuda poha nenhuma, eu ajudo mais que isso!!!");
        menuAjuda.add(menuItemSobre);
        
        super.add(menuAjuda);
    }

    private void adicionaListenersMenu() {
        //Istancia o Listener para o item de menu Cadastro de Endereço
        ActionListener listenerCadMedico = new MenuCadMedicoListener(frame);
        ActionListener listenerCadPaciente = new MenuCadPacienteListener(frame);
        ActionListener listenerCadEnfermeiro = new MenuCadEnfermeiroListener(frame);
        ActionListener listenerCadAtendente = new MenuCadAtendenteListener(frame);
        ActionListener listenerSobreListener = new MenuSobreListener(frame);
        ActionListener listenerCadDiagnostico = new MenuCadDiagnosticoListener(frame);
        ActionListener listenerAgenda = new MenuAgendaListener(frame);
        ActionListener listenerHistorico = new MenuHistoricoListener(frame);
        
        //Listeners para os itens do Menu Consula
        ActionListener listenerConsultaNova = new MenuConsultaNovaListener(frame);
        ActionListener listenerConsultaListar = new MenuConsultaListarListener(frame);
        

        //Adiciona o Listener para o item
        menuItemCadHistorico.addActionListener(listenerHistorico);
        menuItemCadDiagnostico.addActionListener(listenerCadDiagnostico);
        menuItemCadAgenda.addActionListener(listenerAgenda);
        menuItemCadMedico.addActionListener(listenerCadMedico);
        menuItemCadEnfermeiro.addActionListener(listenerCadEnfermeiro);
        menuItemCadPaciente.addActionListener(listenerCadPaciente);
        menuItemCadAtendente.addActionListener(listenerCadAtendente);
                
        menuItemNovaConsulta.addActionListener(listenerConsultaNova);
        menuItemListaConsulta.addActionListener(listenerConsultaListar);
        
        menuItemSobre.addActionListener(listenerSobreListener);
        
        
    }
    
}
