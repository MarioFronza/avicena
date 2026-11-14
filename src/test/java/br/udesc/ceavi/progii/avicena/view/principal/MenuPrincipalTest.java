package br.udesc.ceavi.progii.avicena.view.principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.MenuPrincipal.Section;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.junit.jupiter.api.Test;

class MenuPrincipalTest {

    private final MenuPrincipal menu = new MenuPrincipal();

    @Test
    void addPutsTheItemInItsSection() {
        menu.add(item(Section.REGISTRATION, "Cadastro Teste"));

        assertEquals(
                "Cadastro Teste",
                menu.getMenu(Section.REGISTRATION.ordinal()).getItem(0).getText());
    }

    @Test
    void itemsKeepTheOrderTheyWereAddedIn() {
        menu.add(item(Section.REGISTRATION, "First"));
        menu.add(item(Section.REGISTRATION, "Second"));

        JMenu registration = menu.getMenu(Section.REGISTRATION.ordinal());
        assertEquals("First", registration.getItem(0).getText());
        assertEquals("Second", registration.getItem(1).getText());
    }

    @Test
    void clickingTheItemRunsTheListener() {
        AtomicBoolean clicked = new AtomicBoolean();
        menu.add(item(Section.APPOINTMENT, "Nova Consulta", () -> clicked.set(true)));

        menu.getMenu(Section.APPOINTMENT.ordinal()).getItem(0).doClick();

        assertTrue(clicked.get());
    }

    @Test
    void sectionsAppearInAFixedOrder() {
        menu.add(item(Section.HELP, "Sobre"));
        menu.add(item(Section.PATIENT, "Diagnóstico"));

        assertEquals("Paciente", menu.getMenu(0).getText());
        assertEquals("Cadastro", menu.getMenu(1).getText());
        assertEquals("Consulta", menu.getMenu(2).getText());
        assertEquals("Ajuda", menu.getMenu(3).getText());
    }

    @Test
    void itemShowsItsTooltipAndIcon() {
        menu.add(item(Section.REGISTRATION, "Cadastro Teste"));

        JMenuItem menuItem = menu.getMenu(Section.REGISTRATION.ordinal()).getItem(0);
        assertEquals("Tooltip", menuItem.getToolTipText());
        assertNotNull(menuItem.getIcon());
    }

    private static MenuActionListener item(Section section, String label) {
        return item(section, label, () -> {});
    }

    private static MenuActionListener item(Section section, String label, Runnable onClick) {
        return new MenuActionListener(null, section, label, "vcard_add.png", "Tooltip") {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClick.run();
            }
        };
    }
}
