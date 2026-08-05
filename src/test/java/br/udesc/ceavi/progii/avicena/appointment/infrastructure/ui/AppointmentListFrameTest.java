package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import org.junit.jupiter.api.Test;

class AppointmentListFrameTest {

    @Test
    void buildsARowEvenWhenMedicoAndEnfermeiroAreMissing() {
        PatientEntity paciente =
                new PatientEntity(null, "Paciente Teste", "11111111111", "48900000000", null, MaritalStatus.SINGLE);
        AppointmentEntity consulta = new AppointmentEntity(
                null, "02/08/2026", "10:00", "Febre", paciente, null, null, UrgencyStatus.NOT_URGENT);

        String[] row = assertDoesNotThrow(() -> AppointmentListFrame.toTableRow(consulta));

        assertArrayEquals(new String[] {"02/08/2026", "10:00", "Paciente Teste", "-", "-"}, row);
    }
}
