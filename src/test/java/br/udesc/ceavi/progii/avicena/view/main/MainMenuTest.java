package br.udesc.ceavi.progii.avicena.view.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.control.menu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.main.MainMenu.Section;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.junit.jupiter.api.Test;

class MainMenuTest {

    private final MainMenu menu = new MainMenu();

    @Test
    void addPutsTheItemInItsSection() {
        menu.add(item(Section.REGISTRATION, "Registration Test"));

        assertEquals(
                "Registration Test",
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
        menu.add(item(Section.APPOINTMENT, "New Appointment", () -> clicked.set(true)));

        menu.getMenu(Section.APPOINTMENT.ordinal()).getItem(0).doClick();

        assertTrue(clicked.get());
    }

    @Test
    void sectionsAppearInAFixedOrder() {
        menu.add(item(Section.HELP, "About"));
        menu.add(item(Section.PATIENT, "Diagnosis"));

        assertEquals("Patient", menu.getMenu(0).getText());
        assertEquals("Registration", menu.getMenu(1).getText());
        assertEquals("Appointments", menu.getMenu(2).getText());
        assertEquals("Help", menu.getMenu(3).getText());
    }

    @Test
    void itemShowsItsTooltipAndIcon() {
        menu.add(item(Section.REGISTRATION, "Registration Test"));

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
