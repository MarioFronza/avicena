package br.udesc.ceavi.progii.avicena.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class MedicoTest {

    @Test
    void twoMedicosWithEqualCrmValuesAreEqualEvenAsDifferentStringInstances() {
        String crm = new String("CRM-1234");
        String otherCrm = new String("CRM-1234");
        Medico medico = new Medico(crm, "Cardiologia", List.of());
        Medico other = new Medico(otherCrm, "Cardiologia", List.of());

        assertEquals(medico, other);
    }
}
