package br.udesc.ceavi.progii.avicena.view.frames;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import br.udesc.ceavi.progii.avicena.model.Consulta;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import org.junit.jupiter.api.Test;

class FrameConsultaListarTest {

    @Test
    void buildsARowEvenWhenMedicoAndEnfermeiroAreMissing() {
        Consulta consulta = new Consulta();
        consulta.setData("02/08/2026");
        consulta.setHora("10:00");
        consulta.setPaciente(
                new PatientEntity(null, "Paciente Teste", "11111111111", "48900000000", null, MaritalStatus.SINGLE));

        String[] row = assertDoesNotThrow(() -> FrameConsultaListar.toTableRow(consulta));

        assertArrayEquals(new String[] {"02/08/2026", "10:00", "Paciente Teste", "-", "-"}, row);
    }
}
