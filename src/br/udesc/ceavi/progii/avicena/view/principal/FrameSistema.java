/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.view.principal;


import br.udesc.ceavi.progii.avicena.view.frames.FrameCadastroEndereco;
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
import javax.swing.JMenuBar;


/**
 * Esta é a tela principal da aplicação
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class FrameSistema extends JFrame{
    
    private static Dimension dimensaoTelaPrincipal;
    private JDesktopPane desktop;
    private Container contentPanel;
    private Image fundo;
    
    private JMenuBar menuPrincipal;
    
    public FrameSistema() {
        super();
        //Seta o icone da aplicação
        super.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/logo.png")));
        //Nome da aplicação
        super.setTitle("Avicena - Software Gerenciador de Clínica Geral");
        //Alterar o tema do swing
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        //Dimensão da aplicação
        dimensaoTelaPrincipal = new Dimension(900, 600);
        //Faz com que a aplicação se encerre ao fechar
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Inicializa os componentes 
        initalizeDesktopPane();
        //Método para adiconar o menu 
        adicionarMenu();
        
    }

    private void initalizeDesktopPane() {
        //Captura a imagem para o fundo
        ImageIcon referencia = new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/Avicena2.jpg")); // chama a imagem
        fundo = referencia.getImage(); // seta a imagem em $fundo
        desktop = new JDesktopPane(){
             @Override
             public void paintComponent(Graphics g) {
		g.drawImage(fundo, 0, 0, getWidth(), getHeight(),null); //seta a imagem de forma responsiva 
    }
        };
        //adiciona o container 
        contentPanel = super.getContentPane();
        contentPanel.add(desktop, BorderLayout.CENTER);
       
        super.setSize(dimensaoTelaPrincipal);
    }
    
   

    private void adicionarMenu() {
        menuPrincipal = new MenuPrincipal(this);
        super.setJMenuBar(menuPrincipal);
    }
    //Adcionar frames internos
    public void adicionarFrameInterno(JInternalFrame frame){
        desktop.add(frame);
    }
    //Adiconar frame de endereço
    public void adicionarFrameInterno2(FrameCadastroEndereco frame){
        desktop.add(frame);
    }
    
}
