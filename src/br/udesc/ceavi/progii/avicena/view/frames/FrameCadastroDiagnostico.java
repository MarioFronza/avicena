/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.view.frames;

import br.udesc.ceavi.progii.avicena.control.listenersCRUD.ListenerCRUDDiagnostico;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.BtGerarReceiraListener;
import br.udesc.ceavi.progii.avicena.model.DiagnosticoPrimario;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.JTextField;

/**
 * Frame de tela de cadastro de Diagnóstico
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class FrameCadastroDiagnostico extends FrameCRUD implements ActionListener{
    
    private static FrameCadastroDiagnostico instancia;
    
    private static final String titulo = "Cadastro de Diagnósticos";
    private static final Dimension dimension = new Dimension(350, 300);
    
    private JLabel lbPressao;
    private JLabel lbTemperatura;
    private JLabel lbAltura;
    private JLabel lbPeso;
    private JLabel lbHistoricoRemedio;
    private JLabel lbHistoricoRemedio2;
    private JLabel lbDoenca;
    private JLabel lbRemedio;
    private JLabel lbExame;  
    private JLabel lbConsulta;
    private JTextField tfPressao;
    private JTextField tfTemperatura;
    private JTextField tfAltura;
    private JTextField tfPeso;
    private JTextField tfDoenca;
    private JTextField tfRemedio;
    private JComboBox cbConsulta;
    private JTextField jcExame;
    private JTextArea taHistoricoRemedio;
    private JTextArea taHistoricoRemedio2;
    private JScrollPane painel;
    private JScrollPane painel2;
    private JButton btRelatorio;
    
    private JPanel panelFormulario; // painel Diagnostico Primario
    private JPanel painelBotoes; // painel Norte com os 2 botoes
    private JPanel p2; // painel DiagnosticoFinal
    private JPanel painelCard; // painel principal
    
    private JButton btPainel1, btPainel2;
    
    
    private LayoutManager layout;
    private GridBagConstraints cons;
    
    public FrameCadastroDiagnostico(String titulo, Dimension dimension){
        super(titulo, dimension);
        
        initializeComponents();
        addComponents();
        adicionarListener();
        
    }

    private void initializeComponents() {
        lbPressao = new JLabel("Pressão:");
        lbTemperatura = new JLabel("Temperatura:");
        lbAltura = new JLabel("Altura:");
        lbPeso = new JLabel("Peso:");
        lbConsulta = new JLabel("Consulta");
        lbHistoricoRemedio = new JLabel("Descrição");
       //----------------------------------------------- 
       // atributos do painel DiagnosticoFinal
       
        lbDoenca= new JLabel("Doença:");
        lbRemedio = new JLabel("Remédio:");
        lbExame = new JLabel("Exame:");
        lbHistoricoRemedio2 = new JLabel("Descrição:");
        cbConsulta = new JComboBox();
        tfDoenca = new JTextField();
        tfRemedio = new JTextField();
        jcExame = new JTextField();
        btRelatorio = new JButton("Gerar Receita");
      //-----------------------------------------------
        
        tfPressao = new JTextField();
        tfTemperatura = new JTextField();
        tfAltura = new JTextField();
        tfPeso = new JFormattedTextField();
        taHistoricoRemedio = new JTextArea(4,4);
        taHistoricoRemedio2 = new JTextArea(4,4);
        
        btPainel1 = new JButton("Diagnostico Primario");
        btPainel2 = new JButton("Diagnostico Final");
        btPainel1.addActionListener(this);
        btPainel2.addActionListener(this);
        
        
        painel = new JScrollPane(taHistoricoRemedio);
        painel2 = new JScrollPane(taHistoricoRemedio2);
        painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(1,2));
        painelCard = new JPanel();
        painelCard.setLayout(new CardLayout());
        
        layout = new GridBagLayout();
        panelFormulario = new JPanel(layout);
        p2 = new JPanel(layout);
        

        panelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Diagnostico Primario"));
        p2.setBorder(BorderFactory.createTitledBorder("Dados do Diagnostico Final"));
    }
    

    private void addComponents() {
        painelBotoes.add(btPainel1);
        painelBotoes.add(btPainel2);
        
       
        
      //-------------------------------------------------------------  
      // Adicionando no painel Diagnostico Primario
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbPressao, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        panelFormulario.add(tfPressao, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbTemperatura, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 1;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        panelFormulario.add(tfTemperatura, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbAltura, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 2;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 100;
        panelFormulario.add(tfAltura, cons);
       
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 3;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbPeso, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 3;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfPeso, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 4;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbHistoricoRemedio, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 4;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        cons.ipady = 30;
        cons.insets = new Insets(5,0,5,0);
        panelFormulario.add(painel, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 5;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbConsulta, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 5;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(cbConsulta, cons);
        
        //================================
        // Adicionando no painel Diagnostico Final
        
         cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        p2.add(lbDoenca, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        p2.add(tfDoenca, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        p2.add(lbRemedio, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 1;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        p2.add(tfRemedio, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        p2.add(lbHistoricoRemedio2, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 2;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(0,0,5,0);
        cons.ipadx = 100;
        p2.add(painel2, cons);
       
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 3;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        p2.add(lbExame, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 3;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(0,0,5,0);
        cons.ipadx = 50;
        p2.add(jcExame, cons);
                
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 4;
        cons.gridwidth = 4;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        p2.add(btRelatorio, cons);
        
        
        //================================
        
         painelCard.add(panelFormulario,"p1");
         painelCard.add(p2,"p2");
        
        super.addFormulario2("North",painelBotoes); 
        super.addFormulario2("Center",painelCard);
        
    }

    @Override
    public void limparCampos() {
        
    }

    @Override
    public void carregarCampos() {
        
    }
    
    public static FrameCadastroDiagnostico getInstance(){
        if(instancia == null)
            instancia = new FrameCadastroDiagnostico(titulo, dimension);
        
        return instancia;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout c1 = (CardLayout) painelCard.getLayout();
        
        if(e.getSource() == btPainel1) {
            c1.show(painelCard,"p1");
        }
        if(e.getSource() == btPainel2){ 
            c1.show(painelCard,"p2");
    }
    }

    private void adicionarListener() {
        ActionListener listenerBtReceita = new BtGerarReceiraListener();
        btRelatorio.addActionListener(listenerBtReceita);
    }

    public JTextField getTfPressao() {
        return tfPressao;
    }

    public JTextField getTfTemperatura() {
        return tfTemperatura;
    }

    public JTextField getTfAltura() {
        return tfAltura;
    }

    public JTextField getTfPeso() {
        return tfPeso;
    }

    public JTextField getTfDoenca() {
        return tfDoenca;
    }

    public JTextField getTfRemedio() {
        return tfRemedio;
    }

    public JScrollPane getPainel() {
        return painel;
    }

    public JScrollPane getPainel2() {
        return painel2;
    }
    
    
    
    public JComboBox getCbConsulta() {
        return cbConsulta;
    }
    
    
    
}
