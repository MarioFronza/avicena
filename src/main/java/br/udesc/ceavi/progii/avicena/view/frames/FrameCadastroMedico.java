/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.view.frames;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.BtEnderecoListener;
import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import br.udesc.ceavi.progii.avicena.view.principal.MenuPrincipal;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.LayoutManager;
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
 * Frame de tela de cadastro de Médico
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class FrameCadastroMedico extends FrameCRUD{
    
    private static FrameCadastroMedico instancia;
    
    private static final String titulo = "Cadastro de Médico";
    private static int valorX = MenuPrincipal.getInstance().getFrame().getWidth();
    private static int valorY = MenuPrincipal.getInstance().getFrame().getHeight();
    private static final Dimension dimension = new Dimension(350, 270);
    
    private JLabel lbNome;
    private JLabel lbCpf;
    private JLabel lbCodigo;
    private JLabel lbTelefone;
    private JLabel lbEndereco;
    private JLabel lbEnderecoInfo;
    private JLabel lbEstadoCivil;
    private JLabel lbCrm;
    private JLabel lbEspecialidade;
    
    private JButton btPesquisar;
    
    private JTextField tfNome;
    private JTextField tfCpf;
    private JTextField tfTelefone;
    private JTextField tfCrm;
    private JTextField tfEspecialidade;
    private JButton btEndereco;
    private JComboBox cbEstadoCivil;
    
    private JPanel panelFormulario;
    private LayoutManager layout;
    private GridBagConstraints cons;
    
    
    
    private FrameSistema frame = MenuPrincipal.getInstance().getFrame();

    public FrameCadastroMedico(String titulo, Dimension dimension) throws HeadlessException {
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
        tfCrm.setText(null);
        tfEspecialidade.setText(null);

    }

    @Override
    public void carregarCampos() {

    }
    
    public static FrameCadastroMedico getInstance(){
        if(instancia == null)
            instancia = new FrameCadastroMedico(titulo, dimension);
       
        return instancia;
    }

    private void initializeComponents() {
        
        lbNome = new JLabel("Nome:");
        lbCpf = new JLabel("CPF:");
        lbCodigo = new JLabel("Código:");
        lbTelefone = new JLabel("Telefone:");
        lbEndereco = new JLabel("Endereço:");
        lbEnderecoInfo = new JLabel("");
        lbEstadoCivil = new JLabel("Estado Cívil:");
        lbCrm = new JLabel("CRM:");
        lbEspecialidade = new JLabel("Especialização:");
        btPesquisar = new JButton("Buscar");
        tfNome = new JTextField();
        tfCpf = new JTextField();
        tfTelefone = new JTextField();
        tfCrm = new JTextField();
        tfEspecialidade = new JTextField();
        btEndereco = new JButton("Adicionar");
        btEndereco.setSize(100,20);
        cbEstadoCivil = new JComboBox(EstadoCivil.values());
        cbEstadoCivil.setSelectedIndex(-1);
    
        
        layout = new GridBagLayout();
        panelFormulario = new JPanel(layout);

        panelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Médico"));

    }

    public JButton getBtEndereco(){
        return btEndereco;
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
        cons.ipadx = 100;
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
        btPesquisar.setEnabled(false);
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
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 3;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbCrm, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 3;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfCrm, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 4;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbEspecialidade, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 4;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfEspecialidade, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 5;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 20;
        panelFormulario.add(lbEstadoCivil, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 5;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(cbEstadoCivil, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 6;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbEndereco, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 6;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(btEndereco, cons);
       

        
        super.addFormulario(panelFormulario);


    }

    private void adicionarListener() {
        ActionListener listenerEndereco = new BtEnderecoListener(frame);
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

    public JTextField getTfCrm() {
        return tfCrm;
    }

    public void setTfCrm(JTextField tfCrm) {
        this.tfCrm = tfCrm;
    }

    public JTextField getTfEspecialidade() {
        return tfEspecialidade;
    }

    public void setTfEspecialidade(JTextField tfEspecialidade) {
        this.tfEspecialidade = tfEspecialidade;
    }

    public JComboBox getCbEstadoCivil() {
        return cbEstadoCivil;
    }
    
    
    
    
}
