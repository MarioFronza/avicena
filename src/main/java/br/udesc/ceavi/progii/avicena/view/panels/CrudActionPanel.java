package br.udesc.ceavi.progii.avicena.view.panels;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Shared Novo/Gravar/Excluir/Cancelar button bar for CRUD screens.
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public class CrudActionPanel extends JPanel {

    private JButton cancelButton;
    private JButton newButton;
    private JButton saveButton;
    private JButton deleteButton;

    private LayoutManager layout;

    private Dimension buttonSize;
    private Dimension panelSize;

    private Container parent;

    public CrudActionPanel(Container parent) {
        this.parent = parent;

        initComponents();
        addComponents();

        super.setVisible(true);
        super.setSize(panelSize);
    }

    private void initComponents() {
        buttonSize = new Dimension(100, 20);
        panelSize = new Dimension(parent.getWidth(), 30);

        layout = new FlowLayout(FlowLayout.CENTER);

        cancelButton = new JButton("Cancelar");
        cancelButton.setSize(buttonSize);

        newButton = new JButton("Novo");
        newButton.setSize(buttonSize);

        saveButton = new JButton("Gravar");
        saveButton.setSize(buttonSize);

        deleteButton = new JButton("Excluir");
        deleteButton.setSize(buttonSize);
    }

    private void addComponents() {
        this.setLayout(layout);
        this.add(cancelButton);
        this.add(newButton);
        this.add(deleteButton);
        this.add(saveButton);
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getNewButton() {
        return newButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
}
