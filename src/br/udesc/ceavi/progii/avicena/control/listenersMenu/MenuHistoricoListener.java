/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.view.frames.FrameHistoricoPaciente;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

/**
 * Listener genérico para Histórico do paciente
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class MenuHistoricoListener extends MenuActionListener{
    
    public MenuHistoricoListener(FrameSistema tela) {
        super(tela);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        frame2 = FrameHistoricoPaciente.getInstance();
     
        if(frame2.isVisible()){
            
        }else{
            tela.adicionarFrameInterno(frame2);
            frame2.setVisible(true);
        }
    }
    
}
