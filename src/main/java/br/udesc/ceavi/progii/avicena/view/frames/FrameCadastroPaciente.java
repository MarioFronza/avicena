/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.view.frames;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.BtEnderecoListener;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.ListenerBtPesquisarPaciente;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import br.udesc.ceavi.progii.avicena.model.Paciente;
import br.udesc.ceavi.progii.avicena.view.principal.MenuPrincipal;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 * Frame de tela de cadastro de Paciente
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class FrameCadastroPaciente extends FrameCRUD implements ActionListener{
    
    private static FrameCadastroPaciente instancia;
    private Paciente paciente;
    private static final String titulo = "Cadastro de Paciente";
    private static final Dimension dimension = new Dimension(360, 240);
    
    private JLabel lbNome;
    private JLabel lbCpf;
    private JLabel lbTelefone;
    private JLabel lbEndereco;
    private JLabel lbEnderecoInfo;
    private JLabel lbEstadoCivil;
    private JButton btPesquisar;
    private JTextField tfNome;
    private JTextField tfCpf;
    private JTextField tfTelefone;
    private JButton btEndereco;
    private JComboBox cbEstadoCivil;
    
    private JPanel panelFormulario;
    private LayoutManager layout;
    private GridBagConstraints cons;
    
    private FrameSistema frame = MenuPrincipal.getInstance().getFrame();
    
    
    public FrameCadastroPaciente(String titulo, Dimension dimension) {
        super(titulo, dimension);
        
        initializeComponents();
        addComponents();
        adicionarListener();
    }

    @Override
    public void limparCampos() {
        tfNome.setText(null);
        tfCpf.setText(null);
        tfTelefone.setText(null);
        
    }
    
    

    @Override
    public void carregarCampos() {
    
    }
    
    public Paciente getPaciente(){
        return paciente;
    }

    private void initializeComponents() {
        lbNome = new JLabel("Nome:");
        lbCpf = new JLabel("CPF:");
        lbTelefone = new JLabel("Telefone:");
        lbEndereco = new JLabel("Endereço:");
        lbEnderecoInfo = new JLabel("");
        lbEstadoCivil = new JLabel("Estado Cívil:");
        btPesquisar = new JButton("Buscar");
        
        
        tfNome = new JTextField();
        tfCpf = new JTextField();
        tfTelefone = new JTextField();
        btEndereco = new JButton("Adicionar");
        btEndereco.setSize(100,20);
        cbEstadoCivil = new JComboBox(EstadoCivil.values());
        cbEstadoCivil.setSelectedIndex(-1);

        
        layout = new GridBagLayout();
        panelFormulario = new JPanel(layout);

        panelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Paciente"));
    }
    
    public JButton getBtEndereco(){
        return btEndereco;
    }

    public static FrameCadastroPaciente getInstance(){
        if(instancia == null)
            instancia = new FrameCadastroPaciente(titulo, dimension);

        return instancia;
    }
    private void addComponents() {
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbNome, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        panelFormulario.add(tfNome, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbCpf, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 105;
        cons.insets = new Insets(0,0,0,10);
        panelFormulario.add(tfCpf, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 2;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        panelFormulario.add(btPesquisar, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbTelefone, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 2;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 100;
        panelFormulario.add(tfTelefone, cons);
       
        //================================

        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 6;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 20;
        panelFormulario.add(lbEstadoCivil, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 6;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(cbEstadoCivil, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 7;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        panelFormulario.add(lbEndereco, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 7;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        btEndereco.addActionListener(this);
        panelFormulario.add(btEndereco, cons);
        
        //================================
                
        super.addFormulario(panelFormulario);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == btEndereco) {
          
        }
    }

    private void adicionarListener() {
        ActionListener listenerEndereco = new BtEnderecoListener(frame);
        ActionListener listenerBtPesquisar = new ListenerBtPesquisarPaciente();
        btPesquisar.addActionListener(listenerBtPesquisar);
        btEndereco.addActionListener(listenerEndereco);
    }

    public JTextField getTfNome() {
        return tfNome;
    }

    public void setTfNome(JTextField tfNome) {
        this.tfNome = tfNome;
    }

    public JTextField getTfCpf() {
        return tfCpf;
    }

    public void setTfCpf(JTextField tfCpf) {
        this.tfCpf = tfCpf;
    }

    public JTextField getTfTelefone() {
        return tfTelefone;
    }

    public void setTfTelefone(JTextField tfTelefone) {
        this.tfTelefone = tfTelefone;
    }

 
    public JComboBox getCbEstadoCivil() {
        return cbEstadoCivil;
    }

    public void setCbEstadoCivil(JComboBox cbEstadoCivil) {
        this.cbEstadoCivil = cbEstadoCivil;
    }

    public JButton getBtPesquisar() {
        return btPesquisar;
    }
    
    
    
    
}
