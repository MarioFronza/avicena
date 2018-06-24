/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.control.listenersCRUD.ListenerCRUDPaciente;
import br.udesc.ceavi.progii.avicena.model.Paciente;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroPaciente;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;
import java.util.Random;

/**
 * Listener para o item de menu Cadastro de Paciente
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class MenuCadPacienteListener extends MenuActionListener {
    
    public MenuCadPacienteListener(FrameSistema tela){
        super(tela);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame = FrameCadastroPaciente.getInstance();
       
        
        Paciente paciente = new Paciente();
        ListenerCRUDPaciente listenerPaciente = ListenerCRUDPaciente.getInstance(paciente, frame);
        
        if(frame.isVisible()){
            
        }else{
            tela.adicionarFrameInterno(frame);
            frame.setVisible(true);
        }

    }
    
}
