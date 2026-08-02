package br.udesc.ceavi.progii.avicena.control.listenersMenu;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.udesc.ceavi.progii.avicena.model.Consulta;
import br.udesc.ceavi.progii.avicena.model.Endereco;
import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import br.udesc.ceavi.progii.avicena.model.Medico;
import br.udesc.ceavi.progii.avicena.model.Paciente;
import java.util.List;
import org.junit.jupiter.api.Test;

class BtGerarReceiraListenerTest {

    @Test
    void rejectsAConsultaWhoseMedicoHasNoEndereco() {
        Consulta consulta = new Consulta();
        Medico medico = new Medico("CRM-1", "Clinica Geral", List.of());
        medico.setNome("Dr Teste");
        consulta.setMedico(medico);
        consulta.setPaciente(paciente());

        assertThrows(IllegalStateException.class, () -> BtGerarReceiraListener.buildReceitaLines(consulta));
    }

    @Test
    void rejectsAConsultaWhoseMedicoIsMissing() {
        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente());

        assertThrows(IllegalStateException.class, () -> BtGerarReceiraListener.buildReceitaLines(consulta));
    }

    @Test
    void rejectsAConsultaWhosePacienteHasNoEndereco() {
        Consulta consulta = new Consulta();
        consulta.setMedico(medico());
        consulta.setPaciente(new Paciente("Paciente Teste", "11111111111", 0, "480000000", null, EstadoCivil.SOLTEIRO));

        assertThrows(IllegalStateException.class, () -> BtGerarReceiraListener.buildReceitaLines(consulta));
    }

    @Test
    void buildsReceitaLinesForAFullyPopulatedConsulta() {
        Consulta consulta = new Consulta();
        consulta.setMedico(medico());
        consulta.setPaciente(paciente());

        List<String> lines = BtGerarReceiraListener.buildReceitaLines(consulta);

        assertFalse(lines.isEmpty());
    }

    private Medico medico() {
        Medico medico = new Medico("CRM-1", "Clinica Geral", List.of());
        medico.setNome("Dr Teste");
        medico.setEndereco(new Endereco(1, "Sala 1", "88000000", "Rua Medico", "Centro", "Florianopolis"));
        return medico;
    }

    private Paciente paciente() {
        Paciente paciente = new Paciente("Paciente Teste", "11111111111", 0, "480000000", null, EstadoCivil.SOLTEIRO);
        paciente.setEndereco(new Endereco(2, "Casa", "88000001", "Rua Paciente", "Centro", "Florianopolis"));
        return paciente;
    }
}
