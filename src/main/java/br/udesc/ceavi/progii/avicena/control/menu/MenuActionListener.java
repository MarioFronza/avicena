package br.udesc.ceavi.progii.avicena.control.menu;

import br.udesc.ceavi.progii.avicena.view.main.MainFrame;
import br.udesc.ceavi.progii.avicena.view.main.MainMenu.Section;
import java.awt.event.ActionListener;

/**
 * Base for every main menu item: what it shows (section, label, icon, tooltip) and what
 * it does when clicked.
 */
public abstract class MenuActionListener implements ActionListener {

    protected final MainFrame mainFrame;
    private final Section section;
    private final String label;
    private final String icon;
    private final String tooltip;

    protected MenuActionListener(MainFrame mainFrame, Section section, String label, String icon, String tooltip) {
        this.mainFrame = mainFrame;
        this.section = section;
        this.label = label;
        this.icon = icon;
        this.tooltip = tooltip;
    }

    public Section getSection() {
        return section;
    }

    public String getLabel() {
        return label;
    }

    public String getIcon() {
        return icon;
    }

    public String getTooltip() {
        return tooltip;
    }
}
