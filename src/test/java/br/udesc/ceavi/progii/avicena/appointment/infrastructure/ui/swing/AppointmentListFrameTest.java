package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller.AppointmentController;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AppointmentListFrameTest {

    @Test
    void buildsARowWithFormattedDateTimeAndNames() {
        AppointmentController.Row row = new AppointmentController.Row(
                1L, LocalDate.of(2026, 8, 2), LocalTime.of(10, 0), "Patient Test", "Doctor Test", "Nurse Test");

        assertArrayEquals(
                new String[] {"02/08/2026", "10:00", "Patient Test", "Doctor Test", "Nurse Test"},
                AppointmentListFrame.toTableRow(row));
    }

    @Test
    void showsAPlaceholderForAMissingDoctorOrNurse() {
        AppointmentController.Row row = new AppointmentController.Row(
                1L, LocalDate.of(2026, 8, 2), LocalTime.of(10, 0), "Patient Test", null, null);

        assertArrayEquals(
                new String[] {"02/08/2026", "10:00", "Patient Test", "-", "-"}, AppointmentListFrame.toTableRow(row));
    }
}
