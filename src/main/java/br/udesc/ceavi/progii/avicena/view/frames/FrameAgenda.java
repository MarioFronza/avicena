/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.view.frames;

import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Frame de tela de contula Agenda
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class FrameAgenda extends FrameCRUD{
    
    private static FrameAgenda instancia;
    
    private static final String titulo = "Agenda do paciente";
    private static final Dimension dimension = new Dimension(360, 315);
    
    private JLabel lbCpf;
    private JLabel lbData;
    private JLabel lbHora;
    private JLabel lbSintomas;
    private JLabel lbId;
    private JLabel lbPaciente;
    private JLabel lbEnfermeiro;
    private JLabel lbMedico;
    private JButton btPesquisar;
    private JTextField tfCpf;
    private JTextField tfData;
    private JTextField tfHora;
    private JTextField tfSintomas;
    private JTextField tfId;
    private JTextField tfPaciente;
    private JComboBox cbEnfermeiro;
    private JComboBox cbMedico;
    
    private JPanel panelFormulario;
    private LayoutManager layout;
    private GridBagConstraints cons;

    public FrameAgenda(String titulo, Dimension dimension) throws HeadlessException {
        super(titulo, dimension);
        
        initializeComponents();
        addComponents();
    }
    
    private void initializeComponents() {
        
        lbCpf = new JLabel("CPF:"); 
        tfCpf = new JTextField();
        lbData = new JLabel("Data:");
        lbHora = new JLabel("Hora:");
        lbSintomas = new JLabel("Sintomas:");
        lbId = new JLabel("ID:");
        lbPaciente = new JLabel("Paciente:");
        lbEnfermeiro = new JLabel("Enfermeiro:");
        lbMedico = new JLabel("Médico:");
        tfData = new JTextField();
        tfHora = new JTextField();
        tfSintomas = new JTextField();
        tfId = new JTextField();
        tfPaciente = new JTextField();
        cbEnfermeiro = new JComboBox();
        cbMedico = new JComboBox();
        btPesquisar = new JButton("Pesquisar");
        
        layout = new GridBagLayout();
        panelFormulario = new JPanel(layout);

        panelFormulario.setBorder(BorderFactory.createTitledBorder("Agenda"));

    }

    private void addComponents() {
        
        //Cpf
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbCpf, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        panelFormulario.add(tfCpf, cons);
        
        super.addFormulario(panelFormulario);
        
        //Id
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 01;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbId, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 1;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        panelFormulario.add(tfId, cons);
        
        //Data
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbData, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 2;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        panelFormulario.add(tfData, cons);
        
        //Hora
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 3;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbHora, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 3;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        panelFormulario.add(tfHora, cons);
        
        //Sintomas
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 4;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbSintomas, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 4;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        panelFormulario.add(tfSintomas, cons);
        
        //Enfermeiro
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 5;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbEnfermeiro, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 5;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        panelFormulario.add(cbEnfermeiro, cons);
        
        //Medico
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 6;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 20;
        panelFormulario.add(lbMedico, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 6;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        panelFormulario.add(cbMedico, cons);
        
        //Pesquisar
        cons = new GridBagConstraints();
        btPesquisar.setEnabled(false);
        cons.gridx = 0;
        cons.gridy = 7;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 230;
        cons.ipady = 2;
        panelFormulario.add(btPesquisar, cons);
        
        super.addFormulario(panelFormulario);

    }
    
    public static FrameAgenda getInstance(){
        if(instancia == null)
            instancia = new FrameAgenda(titulo, dimension);
       
        return instancia;
    }

    @Override
    public void limparCampos() {

    }

    @Override
    public void carregarCampos() {

    }
    
}
