package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.view.frames.*;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

/**
 * Listener para o item de menu Consulta
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */

public class MenuConsultaListarListener extends MenuActionListener{
    
    public MenuConsultaListarListener(FrameSistema tela){
        super(tela);
    }    

    @Override
    public void actionPerformed(ActionEvent e) {
        frame2 = FrameConsultaListar.getInstance();
        
        if(frame2.isVisible()){
            
        }else{
            tela.adicionarFrameInterno(frame2);
            frame2.setVisible(true);
        }    
    }

}
