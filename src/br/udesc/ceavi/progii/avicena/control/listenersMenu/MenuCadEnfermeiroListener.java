/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.control.listenersCRUD.ListenerCRUDEnfermeiro;
import br.udesc.ceavi.progii.avicena.model.Enfermeiro;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroEnfermeiro;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

/**
 * Listener para o item de menu Cadastro de Enfermeiro
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class MenuCadEnfermeiroListener extends MenuActionListener{

    public MenuCadEnfermeiroListener(FrameSistema tela) {
        super(tela);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame = FrameCadastroEnfermeiro.getInstance();
        
        Enfermeiro enfermeiro = new Enfermeiro();
        
        ListenerCRUDEnfermeiro listenerEnfermeiro = ListenerCRUDEnfermeiro.getInstance(enfermeiro, frame);
     
        if(frame.isVisible()){
        }else{
            tela.adicionarFrameInterno(frame);
            frame.setVisible(true);
        }
        
        
    }
    
}
