/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;


import br.udesc.ceavi.progii.avicena.control.listenersCRUD.ListenerCRUDEndereco;
import br.udesc.ceavi.progii.avicena.model.Endereco;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroEndereco;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

/**
 * Listener para evento de clique do botão adicionarEndereco
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class BtEnderecoListener extends MenuActionListener {
    
     public BtEnderecoListener(FrameSistema tela){
        super(tela);
    }
     
     @Override
    public void actionPerformed(ActionEvent e) {
        
         frame = FrameCadastroEndereco.getInstance();
      
         
         ListenerCRUDEndereco.getInstance(new Endereco(), frame);
         
         tela.adicionarFrameInterno(frame);
         frame.setVisible(true);
    }

   
}
