package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import org.junit.jupiter.api.Test;

class JPADAOTest {

    @Test
    void supportsSequentialOperationsOnTheSameInstance() {
        JPADAO<PatientEntity> dao = new JPADAO<>();
        PatientEntity first = new PatientEntity(null, "Primeiro", "11111111111", "111", null, MaritalStatus.SINGLE);
        PatientEntity second = new PatientEntity(null, "Segundo", "22222222222", "222", null, MaritalStatus.SINGLE);

        assertDoesNotThrow(() -> dao.inserir(first));
        assertDoesNotThrow(() -> dao.inserir(second));
    }
}
