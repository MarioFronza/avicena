package br.udesc.ceavi.progii.avicena.view.frames;

import br.udesc.ceavi.progii.avicena.control.dao.ConsultaDAO;
import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.model.Consulta;
import java.awt.Dimension;


import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Frame de tela de listagem de consultas
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */

public class FrameConsultaListar extends FrameSemCRUD{
    private static FrameConsultaListar instancia;
    
    
    private static final String titulo = "Lista de consultas";
    private static final Dimension dimension = new Dimension(800,500);
    
    private JPanel painelFundo;
    private JTable tabela;
    private JScrollPane barraRolagem;
    
    DefaultTableModel dtm; 
  
    
    public FrameConsultaListar(String titulo, Dimension dimension) throws HeadlessException {
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
   

    private void initializeComponents() {
        tabela = new JTable();
        tabela.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
            },
            new String [] {
                "Data", "Hora", "Paciente", "Médico", "Enfermeiro"
            }
        ));
        dtm = (DefaultTableModel) tabela.getModel();
        painelFundo = new JPanel();
        painelFundo.setLayout(new GridLayout(1,1));
        barraRolagem = new JScrollPane(tabela);
        DAO dao = new ConsultaDAO();
        List<Consulta> consultas = dao.getList();
        for (int i = 0; i < consultas.size(); i++) {
            String data = consultas.get(i).getData();
            String hora = consultas.get(i).getHora();
            String paciente = consultas.get(i).getPaciente().getNome();
            String medico = consultas.get(i).getMedico().getNome();
            String enfermeiro = consultas.get(i).getEnfermeiro().getNome();
            dtm.addRow(new String[]{data, hora, paciente, medico, enfermeiro});
        }
        
    }
    public static FrameConsultaListar getInstance(){
        if(instancia == null)
            instancia = new FrameConsultaListar(titulo, dimension);

        return instancia;
    }

    public void addLinha(String id, String data, String hora, String paciente, String medico, String enfermeiro){
        String[] dados = {id, data, hora, paciente, medico, enfermeiro};
        dtm.addRow(dados);
    }
    
    private void addComponents() {
        painelFundo.add(barraRolagem);
        super.addFormulario2("North",painelFundo);
    }
   
}
