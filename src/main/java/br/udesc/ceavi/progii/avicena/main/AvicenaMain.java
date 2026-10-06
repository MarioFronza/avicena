/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.main;

import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import javax.swing.JOptionPane;

/**
 * Classe principal da aplicação
 * @author Adroan, Mário, Vini, Raphael
 * @since 10/04/2018
 * @version 1.0
 */
public class AvicenaMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            PersistenceConfig.initialize();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(
                    null, "Could not connect to the database: " + e.getMessage(), "Avicena", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        FrameSistema frameSistema = new FrameSistema();
        frameSistema.setVisible(true);
    }
}
