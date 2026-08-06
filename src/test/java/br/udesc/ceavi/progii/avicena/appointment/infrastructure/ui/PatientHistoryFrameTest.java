package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import org.junit.jupiter.api.Test;

class PatientHistoryFrameTest {

    @Test
    void buildsARowWithDateAndSymptoms() {
        PatientEntity patient =
                new PatientEntity(null, "Paciente Teste", "11111111111", "48900000000", null, MaritalStatus.SINGLE);
        AppointmentEntity appointment = new AppointmentEntity(
                null, "02/08/2026", "10:00", "Febre", patient, null, null, UrgencyStatus.NOT_URGENT);

        String[] row = PatientHistoryFrame.toTableRow(appointment);

        assertArrayEquals(new String[] {"02/08/2026", "Febre"}, row);
    }
}
