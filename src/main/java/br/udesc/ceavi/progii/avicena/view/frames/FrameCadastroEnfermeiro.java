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
 * Frame de tela de cadastro de Enfermeiro
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class FrameCadastroEnfermeiro extends FrameCRUD{
    
    private static FrameCadastroEnfermeiro instancia;
    
    private static final String titulo = "Cadastro de Enfermeiro";
    private static final Dimension dimension = new Dimension(370, 270);
    
    private JLabel lbNome;
    private JLabel lbCpf;
    private JLabel lbCodigo;
    private JLabel lbTelefone;
    private JLabel lbEndereco;
    private JLabel lbEnderecoInfo;
    private JLabel lbEstadoCivil;
    private JLabel lbFormacao;
    private JLabel lbHrCursadas;
    private JButton btPesquisar;
    private JTextField tfNome;
    private JTextField tfCpf;
    private JTextField tfTelefone;
    private JTextField tfFormacao;
    private JTextField tfHrCursadas;
    private JButton btEndereco;
    private JComboBox cbEstadoCivil;
    
    private JPanel panelFormulario;
    private LayoutManager layout;
    private GridBagConstraints cons;
    
    private FrameSistema frame = MenuPrincipal.getInstance().getFrame();

    public FrameCadastroEnfermeiro(String titulo, Dimension dimension) throws HeadlessException {
        super(titulo, dimension);
        
        initializeComponents();
        addComponents();
        adicionarListener();
    }

    @Override
    public void limparCampos() {

    }

    @Override
    public void carregarCampos() {

    }
    
    public static FrameCadastroEnfermeiro getInstance(){
        if(instancia == null)
            instancia = new FrameCadastroEnfermeiro(titulo, dimension);
       
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
        lbFormacao = new JLabel("Formação:");
        lbHrCursadas = new JLabel("Horas Cursadas:");
        btPesquisar = new JButton("Buscar");
        tfNome = new JTextField();
        tfCpf = new JTextField();
        tfTelefone = new JTextField();
        tfFormacao = new JTextField();
        tfHrCursadas = new JTextField();
        btEndereco = new JButton("Adicionar");
        btEndereco.setSize(100,20);
        cbEstadoCivil = new JComboBox(EstadoCivil.values());
        cbEstadoCivil.setSelectedIndex(-1);
        
      
        
        layout = new GridBagLayout();
        panelFormulario = new JPanel(layout);

        panelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Enfermeiro"));

    }

    private void addComponents() {
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbNome, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 1;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        panelFormulario.add(tfNome, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbCpf, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 2;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 105;
        cons.insets = new Insets(0,0,0,10);
        panelFormulario.add(tfCpf, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        btPesquisar.setEnabled(false);
        cons.gridx = 2;
        cons.gridy = 2;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        panelFormulario.add(btPesquisar, cons);
       
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 3;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbTelefone, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 3;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfTelefone, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 4;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbFormacao, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 4;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfFormacao, cons);
        
        //================================
        
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 5;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbHrCursadas, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 5;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(tfHrCursadas, cons);
        
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
        cons.ipady = 10;
        panelFormulario.add(lbEndereco, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 7;
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

    public JTextField getTfCpf() {
        return tfCpf;
    }

    public JTextField getTfTelefone() {
        return tfTelefone;
    }

    public JTextField getTfFormacao() {
        return tfFormacao;
    }

    public JTextField getTfHrCursadas() {
        return tfHrCursadas;
    }

    public JButton getBtPesquisar() {
        return btPesquisar;
    }

    public JComboBox getCbEstadoCivil() {
        return cbEstadoCivil;
    }
    
    
    
    
    
    
}
