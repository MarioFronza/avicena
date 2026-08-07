package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import org.junit.jupiter.api.Test;

class AppointmentListFrameTest {

    @Test
    void buildsARowEvenWhenMedicoAndEnfermeiroAreMissing() {
        PersonEntity person = new PersonEntity(
                null,
                "Paciente Teste",
                "11111111111",
                "48900000000",
                null,
                new MaritalStatusEntity(1L, "SINGLE", "Single"));
        PatientEntity paciente = new PatientEntity(null, person);
        AppointmentEntity consulta = new AppointmentEntity(
                null, "02/08/2026", "10:00", "Febre", paciente, null, null, UrgencyStatus.NOT_URGENT);

        String[] row = assertDoesNotThrow(() -> AppointmentListFrame.toTableRow(consulta));

        assertArrayEquals(new String[] {"02/08/2026", "10:00", "Paciente Teste", "-", "-"}, row);
    }
}
