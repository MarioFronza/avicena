/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;


import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import br.udesc.ceavi.progii.avicena.view.principal.MenuPrincipal;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * Listener para o item de menu Sobre
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class MenuSobreListener extends MenuActionListener{

    public MenuSobreListener(FrameSistema tela) {
        super(tela);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        JOptionPane.showMessageDialog(MenuPrincipal.getInstance().getFrame(), "Desenvolvido na disciplina de Programação II - PRO2\n"
        +"Universidade do Estado de Santa Catarina \n"
        +"Centro de Educação Superior do Alto Vale do Itajaí");
    }
    
}
