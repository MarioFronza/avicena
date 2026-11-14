package br.udesc.ceavi.progii.avicena.view.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 * Base internal frame for read-only screens, without the CRUD buttons.
 * @author Adroan, Mário, Vini, Raphael
 * @since 13/04/2018
 * @version 1.0
 */
public abstract class ReadOnlyFrame extends JInternalFrame {

    private JPanel mainPanel;
    private Dimension dimension;
    private LayoutManager layout;

    public ReadOnlyFrame(String title, Dimension dimension) throws HeadlessException {
        this.dimension = dimension;

        initializeComponents();
        addComponents();

        super.setSize(dimension);
        super.setTitle(title);
        super.setLayout(layout);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.setClosable(true);
    }

    private void initializeComponents() {
        mainPanel = new JPanel();
        layout = new BorderLayout();

        mainPanel.setSize(dimension);
        mainPanel.setLayout(layout);
    }

    private void addComponents() {

        this.setContentPane(mainPanel);
    }

    public void addForm(Container container) {
        mainPanel.add(container, BorderLayout.CENTER);
    }

    public void addFormAt(String layout, Container container) {
        mainPanel.add(layout, container);
    }
}
