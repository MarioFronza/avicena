/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionListener;

/**
 * Listener genérico para todas as telas internas da aplicação
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public abstract class MenuActionListener implements ActionListener {

    protected final FrameSistema tela;

    public MenuActionListener(FrameSistema tela) {
        this.tela = tela;
    }
}
