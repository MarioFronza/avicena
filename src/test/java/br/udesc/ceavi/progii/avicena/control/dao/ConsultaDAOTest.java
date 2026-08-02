package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.model.Consulta;
import br.udesc.ceavi.progii.avicena.model.EstadoPaciente;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;

class ConsultaDAOTest {

    @Test
    void gravarPersistsAConsultaThatIsRetrievableAfterward() throws Exception {
        PatientEntity paciente =
                new PatientEntity(null, "Paciente Consulta", "65465465600", "48944445555", null, MaritalStatus.SINGLE);
        new JPADAO<PatientEntity>().inserir(paciente);

        Consulta consulta = new Consulta();
        consulta.setData("02/08/2026");
        consulta.setHora("10:00");
        consulta.setSintomas("Febre");
        consulta.setEstadoPaciente(EstadoPaciente.NAOURGENTE);
        consulta.setPaciente(paciente);

        new ConsultaDAO().inserir(consulta);
        new JPADAO<Consulta>().inserir(consulta);

        assertEquals("Febre", findByCodigo(consulta.getCodigo()).getSintomas());
    }

    @Test
    void excluirRemovesAPreviouslyPersistedConsulta() throws Exception {
        Consulta consulta = new Consulta();
        consulta.setData("02/08/2026");
        consulta.setHora("11:00");
        consulta.setSintomas("Dor de cabeca");
        consulta.setEstadoPaciente(EstadoPaciente.NAOURGENTE);
        new JPADAO<Consulta>().inserir(consulta);
        assertTrue(consulta.getCodigo() != 0);

        new JPADAO<Consulta>().deletar(consulta);

        assertNull(findByCodigo(consulta.getCodigo()));
    }

    private Consulta findByCodigo(long codigo) {
        EntityManagerFactory emf = PersistenceConfig.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Consulta.class, codigo);
        } finally {
            em.close();
            emf.close();
        }
    }
}
