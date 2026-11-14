package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import br.udesc.ceavi.progii.avicena.view.principal.MenuPrincipal.Section;
import java.awt.event.ActionListener;

/**
 * Base for every main menu item: what it shows (section, label, icon, tooltip) and what
 * it does when clicked.
 */
public abstract class MenuActionListener implements ActionListener {

    protected final FrameSistema tela;
    private final Section section;
    private final String label;
    private final String icon;
    private final String tooltip;

    protected MenuActionListener(FrameSistema tela, Section section, String label, String icon, String tooltip) {
        this.tela = tela;
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
