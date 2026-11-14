package br.udesc.ceavi.progii.avicena.view.principal;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import java.awt.Dimension;
import java.util.EnumMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Main menu bar. Knows only its top-level sections; each vertical adds its own items.
 */
public class MenuPrincipal extends JMenuBar {

    public enum Section {
        PATIENT("Paciente", "user_red.png", "Cadastro de Diagnóstico,..."),
        REGISTRATION("Cadastro", "user_add.png", "Cadastros gerais"),
        APPOINTMENT("Consulta", "pill_add.png", "Cadastro e lista de consultas"),
        HELP("Ajuda", "help.png", "Ajuda do sistema");

        private final String label;
        private final String icon;
        private final String tooltip;

        Section(String label, String icon, String tooltip) {
            this.label = label;
            this.icon = icon;
            this.tooltip = tooltip;
        }
    }

    private final Map<Section, JMenu> menus = new EnumMap<>(Section.class);

    public MenuPrincipal() {
        setPreferredSize(new Dimension(30, 30));
        for (Section section : Section.values()) {
            JMenu menu = new JMenu(section.label);
            menu.setIcon(icon(section.icon));
            menu.setToolTipText(section.tooltip);
            menus.put(section, menu);
            super.add(menu);
        }
    }

    public void add(MenuActionListener item) {
        JMenuItem menuItem = new JMenuItem(item.getLabel());
        menuItem.setIcon(icon(item.getIcon()));
        menuItem.setToolTipText(item.getTooltip());
        menuItem.addActionListener(item);
        menus.get(item.getSection()).add(menuItem);
    }

    private ImageIcon icon(String name) {
        return new ImageIcon(getClass().getResource("/br.udesc.ceavi.progii.avicena.view.image/" + name));
    }
}
