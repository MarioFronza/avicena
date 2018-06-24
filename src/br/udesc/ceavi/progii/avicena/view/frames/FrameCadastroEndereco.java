/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.view.frames;

import br.udesc.ceavi.progii.avicena.model.Endereco;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Frame de tela de cadastro de Endereço
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */

public class FrameCadastroEndereco extends FrameCRUD{
    
    private static FrameCadastroEndereco instancia;
    
    private static final String titulo = "Cadastro de Endereço";
    private static final Dimension dimension = new Dimension(300, 200);

    private Endereco endereco;

    private JLabel lbCep;
    private JLabel lbRua;
    private JLabel lbNumero;
    private JLabel lbComplemento;
    private JLabel lbBairro;
    private JLabel lbCidade;


    private JTextField tfCep;
    private JTextField tfRua;
    private JTextField tfNumero;
    private JTextField tfComplemento;
    private JTextField tfBairro;
    private JTextField tfCidade;


    private JPanel panelFormulario;
    private LayoutManager layout;
    private GridBagConstraints cons;

    public FrameCadastroEndereco(String titulo, Dimension dimension) {
        super(titulo, dimension);

        endereco = new Endereco();

        initializeComponents();
        addComponents();

        
    }
    
    


    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
        carregarCampos();
    }
    
    private void initializeComponents() {
        lbCep = new JLabel("Cep:");
        lbRua = new JLabel("             Rua:");
        lbNumero = new JLabel("Numero:");
        lbComplemento = new JLabel("Complemento:");
        lbBairro = new JLabel("Bairro:");
        lbCidade = new JLabel("Cidade:");

        tfCep = new JTextField();
        tfRua = new JTextField();
        tfNumero = new JTextField();
        tfComplemento = new JTextField();
        tfBairro = new JTextField();
        tfCidade = new JTextField();

        layout = new GridBagLayout();
        panelFormulario = new JPanel(layout);

        panelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Endereço"));
    }


    @Override
    public void limparCampos() {
        
    }
    
       public static FrameCadastroEndereco getInstance(){
        if(instancia == null)
            instancia = new FrameCadastroEndereco(titulo, dimension);

        return instancia;
    }
    
    private void addComponents() {
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipady = 10;
        panelFormulario.add(lbCep, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 70;
        panelFormulario.add(tfCep, cons);

        cons = new GridBagConstraints();
        cons.gridx = 2;
        cons.gridy = 0;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(lbRua, cons);

        cons = new GridBagConstraints();
        cons.gridx = 3;
        cons.gridy = 0;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfRua, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipady = 10;
        panelFormulario.add(lbNumero, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfNumero, cons);

        cons = new GridBagConstraints();
        cons.gridx = 2;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipady = 10;
        panelFormulario.add(lbComplemento, cons);

        cons = new GridBagConstraints();
        cons.gridx = 3;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfComplemento, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipady = 10;
        panelFormulario.add(lbBairro, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 2;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 100;
        panelFormulario.add(tfBairro, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 3;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(lbCidade, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 3;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 100;
        panelFormulario.add(tfCidade, cons);


        super.addFormulario(panelFormulario);
    }

    @Override
    public void carregarCampos() {

    }

    public JTextField getTfCep() {
        return tfCep;
    }

    public JTextField getTfRua() {
        return tfRua;
    }

    public JTextField getTfNumero() {
        return tfNumero;
    }

    public JTextField getTfComplemento() {
        return tfComplemento;
    }

    public JTextField getTfBairro() {
        return tfBairro;
    }

    public JTextField getTfCidade() {
        return tfCidade;
    }
    
    
    
}
