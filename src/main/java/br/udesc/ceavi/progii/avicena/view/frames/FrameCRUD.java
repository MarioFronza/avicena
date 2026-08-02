/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.view.frames;

import br.udesc.ceavi.progii.avicena.view.painels.CRUDActionPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 * LEsta classe define o JInternalFrame padrão para telas do tipo CRUD
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public abstract class FrameCRUD extends JInternalFrame{
    
    private JPanel panelPrincipal;
    private Dimension dimension;
    private LayoutManager layout;
    
    private CRUDActionPanel panelBotoesCRUD;

    public FrameCRUD(String titulo, Dimension dimension) throws HeadlessException{
        this.dimension = dimension;
        
        initializeComponets();
        addComponets();
        
        super.setSize(dimension);
        super.setTitle(titulo);
        super.setLayout(layout);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeComponets() {
        panelPrincipal = new JPanel();
        layout = new BorderLayout();
        
        panelPrincipal.setSize(dimension);
        panelPrincipal.setLayout(layout);
        
        panelBotoesCRUD = new CRUDActionPanel(this);
    }

    private void addComponets() {
        
       this.setContentPane(panelPrincipal);
       panelPrincipal.add(panelBotoesCRUD, BorderLayout.SOUTH); 
    }
    
    public void addFormulario(Container container){
        panelPrincipal.add(container, BorderLayout.CENTER);
    }
    
    public void addFormulario2(String layout, Container container){
        panelPrincipal.add(layout, container);
    }
   
    
    public CRUDActionPanel getPanelBotoesCRUD() {
        return panelBotoesCRUD;
    }
    
    public abstract void limparCampos();
    
    public abstract void carregarCampos();
    
}
