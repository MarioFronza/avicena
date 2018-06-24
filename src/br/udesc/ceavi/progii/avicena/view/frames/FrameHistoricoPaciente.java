/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.view.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Frame de tela de Histórico do paciente
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class FrameHistoricoPaciente extends FrameSemCRUD{
    
    private static FrameHistoricoPaciente instancia;
    
    private static final String titulo = "Lista de Consultas";
    private static final Dimension dimension = new Dimension(720, 480);
    
    private JLabel lbCpf;
    private JButton btCpf;
    private JTextField tfCpf; 
    private JPanel panelFormulario;
    private JPanel panel;
    private JTable tabela;
    private JScrollPane barraRolagem;   
    private GridBagConstraints cons;
    private LayoutManager layout;


    public FrameHistoricoPaciente(String titulo, Dimension dimension) throws HeadlessException {
        super(titulo, dimension);
        
        initializeComponents();
        addComponents();
    }

    @Override
    public void limparCampos() {

    }

    @Override
    public void carregarCampos() {

    }
    
    String[] colHeads = {"Data", "Sintomas"};
    Object[][] data = {{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},
    {"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},
    {"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},
    {"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},{"19/04","dor nas articulações"},};
    public static FrameHistoricoPaciente getInstance(){
        if(instancia == null)
            instancia = new FrameHistoricoPaciente(titulo, dimension);
       
        return instancia;
    }

    private void initializeComponents() {
       panelFormulario = new JPanel();
       panelFormulario.setLayout(new GridLayout(1,1));
        tabela = new JTable(data, colHeads);
        barraRolagem = new JScrollPane(tabela);
        lbCpf = new JLabel("Cpf: ");
        btCpf = new JButton("Buscar");
        tfCpf = new JTextField();
        layout = new GridBagLayout();
        panel = new JPanel(layout);
    }

    private void addComponents() {

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.HORIZONTAL;
        panel.add(lbCpf, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        panel.add(tfCpf, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 3;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.HORIZONTAL;
        panel.add(btCpf, cons);
        

        
        panelFormulario.add(barraRolagem); 
        super.addFormulario2("Center",panelFormulario);
        super.addFormulario2("North",panel);
    }
    
}
