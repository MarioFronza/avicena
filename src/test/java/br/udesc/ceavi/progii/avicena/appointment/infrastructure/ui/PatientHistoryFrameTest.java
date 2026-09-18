package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.UrgencyStatusEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class PatientHistoryFrameTest {

    private static final UrgencyStatusEntity NOT_URGENT = new UrgencyStatusEntity(4L, "NOT_URGENT", "Not urgent");

    @Test
    void buildsARowWithDateAndSymptoms() {
        PatientEntity patient = newPatient("Paciente Teste", "11111111111", "48900000000");
        AppointmentEntity appointment = new AppointmentEntity(
                null, LocalDate.of(2026, 8, 2), LocalTime.of(10, 0), "Febre", patient, null, null, NOT_URGENT);

        String[] row = PatientHistoryFrame.toTableRow(appointment);

        assertArrayEquals(new String[] {"02/08/2026", "Febre"}, row);
    }

    @Test
    void filterByPatientCpfKeepsOnlyMatchingPatientAndIgnoresAppointmentsWithoutAPatient() {
        PatientEntity matching = newPatient("Match", "11111111111", "48900000000");
        PatientEntity other = newPatient("Other", "22222222222", "48900000001");
        AppointmentEntity matchingAppointment = new AppointmentEntity(
                null, LocalDate.of(2026, 8, 2), LocalTime.of(10, 0), "Febre", matching, null, null, NOT_URGENT);
        AppointmentEntity otherAppointment = new AppointmentEntity(
                null, LocalDate.of(2026, 8, 3), LocalTime.of(11, 0), "Tosse", other, null, null, NOT_URGENT);
        AppointmentEntity noPatientAppointment = new AppointmentEntity(
                null, LocalDate.of(2026, 8, 4), LocalTime.of(12, 0), "Dor", null, null, null, NOT_URGENT);

        List<AppointmentEntity> filtered = PatientHistoryFrame.filterByPatientCpf(
                List.of(matchingAppointment, otherAppointment, noPatientAppointment), "11111111111");

        assertEquals(1, filtered.size());
        assertEquals(matchingAppointment, filtered.get(0));
    }

    private static PatientEntity newPatient(String name, String cpf, String phone) {
        PersonEntity person =
                new PersonEntity(null, name, cpf, phone, null, new MaritalStatusEntity(1L, "SINGLE", "Single"));
        return new PatientEntity(null, person);
    }
}
