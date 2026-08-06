package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import java.util.List;
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

    @Test
    void filterByPatientCpfKeepsOnlyMatchingPatientAndIgnoresAppointmentsWithoutAPatient() {
        PatientEntity matching =
                new PatientEntity(null, "Match", "11111111111", "48900000000", null, MaritalStatus.SINGLE);
        PatientEntity other =
                new PatientEntity(null, "Other", "22222222222", "48900000001", null, MaritalStatus.SINGLE);
        AppointmentEntity matchingAppointment = new AppointmentEntity(
                null, "02/08/2026", "10:00", "Febre", matching, null, null, UrgencyStatus.NOT_URGENT);
        AppointmentEntity otherAppointment = new AppointmentEntity(
                null, "03/08/2026", "11:00", "Tosse", other, null, null, UrgencyStatus.NOT_URGENT);
        AppointmentEntity noPatientAppointment = new AppointmentEntity(
                null, "04/08/2026", "12:00", "Dor", null, null, null, UrgencyStatus.NOT_URGENT);

        List<AppointmentEntity> filtered = PatientHistoryFrame.filterByPatientCpf(
                List.of(matchingAppointment, otherAppointment, noPatientAppointment), "11111111111");

        assertEquals(1, filtered.size());
        assertEquals(matchingAppointment, filtered.get(0));
    }
}
