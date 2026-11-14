package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import br.udesc.ceavi.progii.avicena.support.Fixtures;
import org.junit.jupiter.api.Test;

class PatientHistoryFrameTest {

    @Test
    void buildsARowWithTheFormattedDateAndTheSymptoms() {
        assertArrayEquals(
                new String[] {"05/08/2026", "Fever"}, PatientHistoryFrame.toTableRow(Fixtures.appointment(1L)));
    }
}
