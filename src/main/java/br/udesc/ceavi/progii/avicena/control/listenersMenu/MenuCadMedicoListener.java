/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.control.listenersCRUD.ListenerCRUDMedico;
import br.udesc.ceavi.progii.avicena.model.Medico;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroMedico;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

/**
 * Listener para o item de menu Cadastro de Médico
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class MenuCadMedicoListener extends MenuActionListener{
    
    public MenuCadMedicoListener(FrameSistema tela) {
        super(tela);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        frame = FrameCadastroMedico.getInstance();
        
        Medico medico = new Medico();
     
        ListenerCRUDMedico listenerMedico = ListenerCRUDMedico.getInstance(medico, frame);
        
        if(frame.isVisible()){
            
        }else{
            tela.adicionarFrameInterno(frame);
            frame.setVisible(true);
        }
    }
    
    
    
}
