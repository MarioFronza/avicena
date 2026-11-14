package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.UrgencyStatusEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AppointmentListFrameTest {

    @Test
    void buildsARowEvenWhenDoctorAndNurseAreMissing() {
        PersonEntity person = new PersonEntity(
                null,
                "Paciente Teste",
                "11111111111",
                "48900000000",
                null,
                new MaritalStatusEntity(1L, "SINGLE", "Single"));
        PatientEntity patient = new PatientEntity(null, person);
        AppointmentEntity appointment = new AppointmentEntity(
                null,
                LocalDate.of(2026, 8, 2),
                LocalTime.of(10, 0),
                "Febre",
                patient,
                null,
                null,
                new UrgencyStatusEntity(4L, "NOT_URGENT", "Not urgent"));

        String[] row = assertDoesNotThrow(() -> AppointmentListFrame.toTableRow(appointment));

        assertArrayEquals(new String[] {"02/08/2026", "10:00", "Paciente Teste", "-", "-"}, row);
    }
}
