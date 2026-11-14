package br.udesc.ceavi.progii.avicena.control.menu;

import br.udesc.ceavi.progii.avicena.view.main.MainFrame;
import br.udesc.ceavi.progii.avicena.view.main.MainMenu.Section;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * "About" menu item: shows the project credits.
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class AboutMenuListener extends MenuActionListener {

    public AboutMenuListener(MainFrame mainFrame) {
        super(mainFrame, Section.HELP, "Sobre", "information.png", null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JOptionPane.showMessageDialog(
                mainFrame,
                "Desenvolvido na disciplina de Programação II - PRO2\n"
                        + "Universidade do Estado de Santa Catarina \n"
                        + "Centro de Educação Superior do Alto Vale do Itajaí");
    }
}
