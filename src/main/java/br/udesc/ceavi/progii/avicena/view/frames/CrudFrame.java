package br.udesc.ceavi.progii.avicena.view.frames;

import br.udesc.ceavi.progii.avicena.view.panels.CrudActionPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 * Base internal frame for CRUD screens, with the shared Novo/Gravar/Excluir/Cancelar bar.
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public abstract class CrudFrame extends JInternalFrame {

    private JPanel mainPanel;
    private Dimension dimension;
    private LayoutManager layout;

    private CrudActionPanel crudActionPanel;

    public CrudFrame(String title, Dimension dimension) throws HeadlessException {
        this.dimension = dimension;

        initializeComponents();
        addComponents();

        super.setSize(dimension);
        super.setTitle(title);
        super.setLayout(layout);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeComponents() {
        mainPanel = new JPanel();
        layout = new BorderLayout();

        mainPanel.setSize(dimension);
        mainPanel.setLayout(layout);

        crudActionPanel = new CrudActionPanel(this);
    }

    private void addComponents() {

        this.setContentPane(mainPanel);
        mainPanel.add(crudActionPanel, BorderLayout.SOUTH);
    }

    public void addForm(Container container) {
        mainPanel.add(container, BorderLayout.CENTER);
    }

    public void addFormAt(String layout, Container container) {
        mainPanel.add(layout, container);
    }

    public CrudActionPanel getCrudActionPanel() {
        return crudActionPanel;
    }

    public abstract void clearFields();
}
