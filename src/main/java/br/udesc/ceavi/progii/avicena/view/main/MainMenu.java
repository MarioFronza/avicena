package br.udesc.ceavi.progii.avicena.view.main;

import br.udesc.ceavi.progii.avicena.control.menu.MenuActionListener;
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
public class MainMenu extends JMenuBar {

    public enum Section {
        PATIENT("Patient", "user_red.png", "Diagnosis and patient history"),
        REGISTRATION("Registration", "user_add.png", "General registrations"),
        APPOINTMENT("Appointments", "pill_add.png", "Register and list appointments"),
        HELP("Help", "help.png", "System help");

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

    public MainMenu() {
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
