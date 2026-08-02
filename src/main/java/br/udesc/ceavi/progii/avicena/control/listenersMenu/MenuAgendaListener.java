/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.control.listenersCRUD.ListenerCRUDAgenda;
import br.udesc.ceavi.progii.avicena.model.Consulta;
import br.udesc.ceavi.progii.avicena.view.frames.FrameAgenda;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroMedico;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

/**
 * Listener genérico para menu Agenda
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class MenuAgendaListener extends MenuActionListener{
    
    public MenuAgendaListener(FrameSistema tela) {
        super(tela);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        frame = FrameAgenda.getInstance();
     
        Consulta consulta = new Consulta();
        
        ListenerCRUDAgenda listenerAgenda = ListenerCRUDAgenda.getInstance(consulta, frame);
        
        if(frame.isVisible()){
            
        }else{
            tela.adicionarFrameInterno(frame);
            frame.setVisible(true);
        }
    }
    
}
