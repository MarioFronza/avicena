package br.udesc.ceavi.progii.avicena.view.frames;

import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.control.dao.MedicoDAO;
import br.udesc.ceavi.progii.avicena.model.EstadoPaciente;
import br.udesc.ceavi.progii.avicena.model.Medico;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 * Frame de tela de cadastro de consulta
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */

public class FrameConsultaNova extends FrameCRUD {
    
    private static FrameConsultaNova instancia;
    private static final String titulo = "Cadastro de Consulta";
    private static final Dimension dimension = new Dimension(630, 230);
    private List listaMedicos = new ArrayList();
    private Medico medico;
    private JLabel lbData;
    private JLabel lbHora;
    private JLabel lbSintomas;
    private JLabel lbId;
    private JLabel lbPaciente;
    private JLabel lbEnfermeiro;
    private JLabel lbMedico;
    private JLabel lbDiagnosticoPrimario;
    private JLabel lbDiagnosticoFinal;
    private JLabel lbEstadoPaciente;

    private JTextField tfData;
    private JTextField tfHora;
    private JTextField tfSintomas;
    private JTextField tfId;
    private JTextField tfPaciente;
    private JComboBox cbEnfermeiro;
    private JComboBox cbMedico;
    private JTextField tfDiagnosticoPrimario;
    private JTextField tfDiagnosticoFinal;
    private JComboBox cbEstadoPaciente;
    
    private JPanel panelFormulario;
    private LayoutManager layout;
    private GridBagConstraints cons;    


    public FrameConsultaNova(String titulo, Dimension dimension){
        super(titulo, dimension);
        initializeComponents();
        addComponents();        
    }        
    
    @Override
    public void limparCampos() {
        tfData.setText(null);
        tfHora.setText(null);
        tfSintomas.setText(null);
        tfId.setText(null);
        tfPaciente.setText(null);
    }

    @Override
    public void carregarCampos() {
        
    }
    
    public static FrameConsultaNova getInstance(){
            if(instancia == null)
                instancia = new FrameConsultaNova(titulo, dimension);
                
            return instancia;
    }    

     private void initializeComponents() {
        lbData = new JLabel("Data:");
        lbHora = new JLabel("Hora:");
        lbSintomas = new JLabel("Sintomas:");
        lbId = new JLabel("Observação:");
        lbPaciente = new JLabel("Paciente (CPF):");
        lbEnfermeiro = new JLabel("Enfermeiro:");
        lbMedico = new JLabel("Médico:");
        lbDiagnosticoPrimario = new JLabel("Diagnóstico Primario:");
        lbDiagnosticoFinal = new JLabel("Diagnóstico Final:");
        lbEstadoPaciente = new JLabel("Estado do Paciente:");

        tfData = new JTextField();
        tfHora = new JTextField();
        tfSintomas = new JTextField();
        tfId = new JTextField();
        tfPaciente = new JTextField();
        cbEnfermeiro = new JComboBox();
        cbMedico = new JComboBox();
        tfDiagnosticoPrimario = new JTextField();
        tfDiagnosticoFinal = new JTextField();
        
        try{
            MaskFormatter hora= new MaskFormatter("##:##");
            MaskFormatter data= new MaskFormatter("##/##/####");
            tfData = new JFormattedTextField(data);
            tfHora = new JFormattedTextField(hora);
        }
        catch (ParseException e){
        }
        
        cbEstadoPaciente = new JComboBox(EstadoPaciente.values());
        
        layout = new GridBagLayout();
        panelFormulario = new JPanel(layout);

        panelFormulario.setBorder(BorderFactory.createTitledBorder("Dados da Consulta"));        
    }
    
    private void addComponents() {
        
        //Data
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(lbData, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 200;
        panelFormulario.add(tfData, cons);        
        
        //================================
        
        //Hora
        cons = new GridBagConstraints();
        cons.gridx = 3;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        cons.insets = new Insets(0,20,0,0);
        panelFormulario.add(lbHora, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 4;
        cons.gridy = 0;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfHora, cons);                
        
        //ID
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbId, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 1;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfId, cons);           
        
        //================================         
        
        //Paciente
        cons = new GridBagConstraints();
        cons.gridx = 3;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        cons.insets = new Insets(0,20,0,0);
        panelFormulario.add(lbPaciente, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 4;
        cons.gridy = 1;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfPaciente, cons);        
        
        //================================
        
        //Sintomas
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbSintomas, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 2;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(tfSintomas, cons);        
        
        //================================   
        
        //EstadoPaciente
        cons = new GridBagConstraints();
        cons.gridx = 3;
        cons.gridy = 2;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        cons.insets = new Insets(0,20,0,0);
        panelFormulario.add(lbEstadoPaciente, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 4;
        cons.gridy = 2;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(cbEstadoPaciente, cons);        
        
        //================================    
        
        //Médico
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 3;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 20;
        panelFormulario.add(lbMedico, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 3;
        cons.gridwidth = 5;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(cbMedico, cons);
        
         //================================    
   
        
        //Enfermeiro
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 4;
        cons.gridwidth = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 10;
        cons.ipady = 10;
        panelFormulario.add(lbEnfermeiro, cons);
        
        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 4;
        cons.gridwidth = 5;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipadx = 50;
        panelFormulario.add(cbEnfermeiro, cons);
        super.addFormulario(panelFormulario);        
    }

    public JTextField getTfData() {
        return tfData;
    }

    public JTextField getTfHora() {
        return tfHora;
    }

    public JTextField getTfSintomas() {
        return tfSintomas;
    }

    public JTextField getTfId() {
        return tfId;
    }

    public JTextField getTfPaciente() {
        return tfPaciente;
    }

    public JComboBox getCbMedico() {
        return cbMedico;
    }

    public JComboBox getCbEnfermeiro() {
        return cbEnfermeiro;
    }

    
    
    

    

   

}
