package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import br.udesc.ceavi.progii.avicena.model.Paciente;
import org.junit.jupiter.api.Test;

class JPADAOTest {

    @Test
    void supportsSequentialOperationsOnTheSameInstance() {
        JPADAO<Paciente> dao = new JPADAO<>();
        Paciente first = new Paciente("Primeiro", "11111111111", 0, "111", null, EstadoCivil.SOLTEIRO);
        Paciente second = new Paciente("Segundo", "22222222222", 0, "222", null, EstadoCivil.SOLTEIRO);

        assertDoesNotThrow(() -> dao.inserir(first));
        assertDoesNotThrow(() -> dao.inserir(second));
    }
}
