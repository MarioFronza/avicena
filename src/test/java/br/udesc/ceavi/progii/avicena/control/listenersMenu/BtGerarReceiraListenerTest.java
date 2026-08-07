package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.appointment.domain.UrgencyStatus;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorEntity;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.AddressEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import java.util.List;
import org.junit.jupiter.api.Test;

class BtGerarReceiraListenerTest {

    @Test
    void rejectsAConsultaWhoseMedicoHasNoEndereco() {
        DoctorEntity medico = newDoctor("Dr Teste", "22222222222", "480000001", null, MaritalStatus.SINGLE);
        AppointmentEntity consulta = consulta(medico, paciente());

        assertThrows(IllegalStateException.class, () -> BtGerarReceiraListener.buildReceitaLines(consulta));
    }

    @Test
    void rejectsAConsultaWhoseMedicoIsMissing() {
        AppointmentEntity consulta = consulta(null, paciente());

        assertThrows(IllegalStateException.class, () -> BtGerarReceiraListener.buildReceitaLines(consulta));
    }

    @Test
    void rejectsAConsultaWhosePacienteHasNoEndereco() {
        PatientEntity paciente = newPatient("Paciente Teste", "11111111111", "480000000", null, MaritalStatus.SINGLE);
        AppointmentEntity consulta = consulta(medico(), paciente);

        assertThrows(IllegalStateException.class, () -> BtGerarReceiraListener.buildReceitaLines(consulta));
    }

    @Test
    void buildsReceitaLinesForAFullyPopulatedConsulta() {
        AppointmentEntity consulta = consulta(medico(), paciente());

        List<String> lines = BtGerarReceiraListener.buildReceitaLines(consulta);

        assertFalse(lines.isEmpty());
    }

    private AppointmentEntity consulta(DoctorEntity medico, PatientEntity paciente) {
        return new AppointmentEntity(
                null, "05/08/2026", "14:00", "Febre", paciente, medico, null, UrgencyStatus.NOT_URGENT);
    }

    private DoctorEntity medico() {
        AddressEntity address =
                new AddressEntity(null, 1, "Sala 1", "88000000", "Rua Medico", "Centro", "Florianopolis");
        return newDoctor("Dr Teste", "22222222222", "480000001", address, MaritalStatus.SINGLE);
    }

    private static DoctorEntity newDoctor(
            String name, String cpf, String phone, AddressEntity address, MaritalStatus maritalStatus) {
        PersonEntity person = new PersonEntity(
                null,
                name,
                cpf,
                phone,
                address,
                new MaritalStatusEntity(1L, maritalStatus.name(), maritalStatus.name()));
        return new DoctorEntity(null, person, "CRM-1", "Clinica Geral");
    }

    private PatientEntity paciente() {
        AddressEntity address =
                new AddressEntity(null, 2, "Casa", "88000001", "Rua Paciente", "Centro", "Florianopolis");
        return newPatient("Paciente Teste", "11111111111", "480000000", address, MaritalStatus.SINGLE);
    }

    private static PatientEntity newPatient(
            String name, String cpf, String phone, AddressEntity address, MaritalStatus maritalStatus) {
        PersonEntity person = new PersonEntity(
                null,
                name,
                cpf,
                phone,
                address,
                new MaritalStatusEntity(1L, maritalStatus.name(), maritalStatus.name()));
        return new PatientEntity(null, person);
    }
}
