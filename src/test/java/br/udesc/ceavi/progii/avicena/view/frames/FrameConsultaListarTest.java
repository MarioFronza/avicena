package br.udesc.ceavi.progii.avicena.view.frames;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import br.udesc.ceavi.progii.avicena.model.Consulta;
import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import br.udesc.ceavi.progii.avicena.model.Paciente;
import org.junit.jupiter.api.Test;

class FrameConsultaListarTest {

    @Test
    void buildsARowEvenWhenMedicoAndEnfermeiroAreMissing() {
        Consulta consulta = new Consulta();
        consulta.setData("02/08/2026");
        consulta.setHora("10:00");
        consulta.setPaciente(new Paciente("Paciente Teste", "11111111111", 0, "48900000000", null, EstadoCivil.SOLTEIRO));

        String[] row = assertDoesNotThrow(() -> FrameConsultaListar.toTableRow(consulta));

        assertArrayEquals(new String[]{"02/08/2026", "10:00", "Paciente Teste", "-", "-"}, row);
    }
}
