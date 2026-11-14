package br.udesc.ceavi.progii.avicena.view.main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 * Application main window: MDI desktop plus the main menu.
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class MainFrame extends JFrame {

    private static Dimension mainFrameSize;
    private JDesktopPane desktop;
    private Container contentPanel;
    private Image background;

    private MainMenu mainMenu;

    public MainFrame() {
        super();
        super.setIconImage(Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/logo.png")));
        super.setTitle("Avicena - Software Gerenciador de Clínica Geral");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        mainFrameSize = new Dimension(900, 600);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeDesktopPane();
        addMainMenu();
    }

    private void initializeDesktopPane() {
        ImageIcon backgroundIcon =
                new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/Avicena2.jpg"));
        background = backgroundIcon.getImage();
        desktop = new JDesktopPane() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
            }
        };
        // adiciona o container
        contentPanel = super.getContentPane();
        contentPanel.add(desktop, BorderLayout.CENTER);

        super.setSize(mainFrameSize);
    }

    private void addMainMenu() {
        mainMenu = new MainMenu();
        super.setJMenuBar(mainMenu);
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public void addInternalFrame(JInternalFrame frame) {
        desktop.add(frame);
    }
}
