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
 * Frame de tela de cadastro de Atendente
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class FrameCadastroAtendente extends FrameCRUD{
    
    private static FrameCadastroAtendente instancia;
    
    private static final String titulo = "Cadastro de Atendente";
    private static final Dimension dimension = new Dimension(430, 330);
    
    private JLabel lbNome;
    private JLabel lbCpf;
    private JLabel lbTelefone;
    private JLabel lbEndereco;
    private JLabel lbEnderecoInfo;
    private JLabel lbEstadoCivil;
    private JLabel lbCargaHoraria;
    private JLabel lbSalario;
    private JLabel lbHoraExtra;
    private JLabel lbNumCartTrabalho;
    private JButton btPesquisar;
    private JTextField tfNome;
    private JTextField tfCpf;
    private JTextField tfTelefone;
    private JTextField tfCargaHoraria;
    private JTextField tfSalario;
    private JTextField tfHoraExtra;
    private JTextField tfNumCartTrabalho;
    private JButton btEndereco;
    private JComboBox cbEstadoCivil;
    
    private JPanel panelFormulario;
    private LayoutManager layout;
    private GridBagConstraints cons;
    
    
    private FrameSistema frame = MenuPrincipal.getInstance().getFrame();
    
    public FrameCadastroAtendente(String titulo, Dimension dimension){
        super(titulo, dimension);
        
        initializeComponents();
        addComponents();
        adicionarListener();
        
    }

    
    
    
    private void initializeComponents() {
        lbNome = new JLabel("Nome:");
        lbCpf = new JLabel("CPF:");
        lbTelefone = new JLabel("Telefone:");
        lbEndereco = new JLabel("Endereço:");
        lbEnderecoInfo = new JLabel("");
        lbEstadoCivil = new JLabel("Estado Cívil:");
        lbCargaHoraria = new JLabel ("Carga horária:");
        lbHoraExtra = new JLabel("Hora extra:");
        lbSalario = new JLabel("Salario:");
        lbNumCartTrabalho = new JLabel("Nº Carteira de Trabalho:");
        btPesquisar = new JButton("Buscar"); 
        tfNome = new JTextField();
        tfCpf = new JTextField();
        tfTelefone = new JTextField();
        tfCargaHoraria = new JFormattedTextField();
        tfHoraExtra = new JTextField();
        tfSalario = new JTextField();
        tfNumCartTrabalho = new JTextField();
        btEndereco = new JButton("Adicionar");
        btEndereco.setSize(100,20);
        cbEstadoCivil = new JComboBox(EstadoCivil.values());
        cbEstadoCivil.setSelectedIndex(-1);

        
        layout = new GridBagLayout();
        panelFormulario = new JPanel(layout);

        panelFormulario.setBorder(BorderFactory.createTitledBorder("Dados da Atendente"));
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
        panelFormulario.add(lbCargaHoraria, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 3;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfCargaHoraria, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 4;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbHoraExtra, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 4;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfHoraExtra, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 5;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbSalario, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 5;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfSalario, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 6;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbNumCartTrabalho, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 6;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfNumCartTrabalho, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 7;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 20;
        panelFormulario.add(lbEstadoCivil, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 7;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(cbEstadoCivil, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 8;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        panelFormulario.add(lbEndereco, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 8;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(btEndereco, cons);
        
        super.addFormulario(panelFormulario);
    }

    @Override
    public void limparCampos() {
        
    }

    @Override
    public void carregarCampos() {
        
    }
    
    public static FrameCadastroAtendente getInstance(){
        if(instancia == null)
            instancia = new FrameCadastroAtendente(titulo, dimension);
        
        return instancia;
    }
    
    
    
    private void adicionarListener(){
        ActionListener listenerEndereco = new BtEnderecoListener(frame);
        btEndereco.addActionListener(listenerEndereco);
    }

    public JTextField getTfNome() {
        return tfNome;
    }

    public JTextField getTfCpf() {
        return tfCpf;
    }

    public JTextField getTfTelefone() {
        return tfTelefone;
    }

    public JTextField getTfCargaHoraria() {
        return tfCargaHoraria;
    }

    public JTextField getTfSalario() {
        return tfSalario;
    }

    public JTextField getTfHoraExtra() {
        return tfHoraExtra;
    }

    public JTextField getTfNumCartTrabalho() {
        return tfNumCartTrabalho;
    }

    public JButton getBtPesquisar() {
        return btPesquisar;
    }

    public JComboBox getCbEstadoCivil() {
        return cbEstadoCivil;
    }
    
    
    
}
