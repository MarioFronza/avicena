/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.control.listenersCRUD.ListenerCRUDAtendente;
import br.udesc.ceavi.progii.avicena.model.Atendente;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroAtendente;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

/**
 * Listener para o item de menu Cadastro de Atendente
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */

public class MenuCadAtendenteListener extends MenuActionListener{
    
    public MenuCadAtendenteListener(FrameSistema tela){
        super(tela);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        frame = FrameCadastroAtendente.getInstance();
        
        Atendente atendente = new Atendente();
        
        ListenerCRUDAtendente listenerAtendente = ListenerCRUDAtendente.getInstance(atendente, frame);
    
        if(frame.isVisible()){
            
        }else{
            tela.adicionarFrameInterno(frame);
            frame.setVisible(true);
        }
    }
    
}
