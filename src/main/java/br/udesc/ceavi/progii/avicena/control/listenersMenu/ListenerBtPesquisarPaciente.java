/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.control.dao.DAO;
import br.udesc.ceavi.progii.avicena.control.dao.PacienteDAO;
import br.udesc.ceavi.progii.avicena.control.listenersCRUD.ListenerCRUDPaciente;
import br.udesc.ceavi.progii.avicena.model.Paciente;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroPaciente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * 
 * Listener para evento de clique do botão Pesquisar Paciente
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class ListenerBtPesquisarPaciente implements ActionListener {

    private Paciente pacienteUsado;
    private static ListenerBtPesquisarPaciente instancia;
    
    public static ListenerBtPesquisarPaciente getInstance(){
        return instancia;
    }
    
//    public Paciente pesquisarPorCpf(String cpf){
//        DAO dao = new PacienteDAO();
//        List<Paciente> pacientes = dao.getList();
//        for (int i = 0; i < pacientes.size(); i++) {
//            if(pacientes.get(i).getCpf().equals(cpf)){
//                return pacientes.get(i);
//            }
//        }
//        return pacientes.get(0);
//    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {

        FrameCadastroPaciente frame = FrameCadastroPaciente.getInstance(); 
        if(frame.getTfCpf().getText().length() == 3)
            JOptionPane.showMessageDialog(frame, "CPF incorreto");

        String cpf = frame.getTfCpf().getText();
        System.out.println(cpf);
        DAO dao = new PacienteDAO();
        List<Paciente> pacientes = dao.getList();
        

        for (int i = 0; i < pacientes.size(); i++) {
            if(cpf.equals(pacientes.get(i).getCpf())){
                JOptionPane.showMessageDialog(frame, "Paciente encontrado");
                pacienteUsado = pacientes.get(i);
                ListenerCRUDPaciente.getInstance().setPaciente(pacienteUsado);
                break;
            }
        }
        
        frame.getTfNome().setText(pacienteUsado.getNome());
        frame.getTfTelefone().setText(pacienteUsado.getTelefone());
        frame.getCbEstadoCivil().setSelectedIndex(1);
        
        
    }
    
}
