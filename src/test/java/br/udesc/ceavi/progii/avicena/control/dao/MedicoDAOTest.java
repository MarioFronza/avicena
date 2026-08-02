package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import br.udesc.ceavi.progii.avicena.model.Medico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;

class MedicoDAOTest {

    @Test
    void gravarPersistsAMedicoThatIsRetrievableAfterward() throws Exception {
        Medico medico = medico("CRM-0001");

        new MedicoDAO().inserir(medico);
        new JPADAO<Medico>().inserir(medico);

        assertEquals(medico.getNome(), findByCodigo(medico.getCodigo()).getNome());
    }

    @Test
    void excluirRemovesAPreviouslyPersistedMedico() throws Exception {
        Medico medico = medico("CRM-0002");
        new JPADAO<Medico>().inserir(medico);
        assertTrue(medico.getCodigo() != 0);

        new JPADAO<Medico>().deletar(medico);

        assertNull(findByCodigo(medico.getCodigo()));
    }

    private Medico medico(String crm) {
        Medico medico = new Medico(crm, "Clinico Geral", List.of());
        medico.setNome("Dr Teste");
        medico.setCpf("12312312312");
        medico.setTelefone("48911112222");
        medico.setEstadoCivil(EstadoCivil.SOLTEIRO);
        return medico;
    }

    private Medico findByCodigo(long codigo) {
        EntityManagerFactory emf = PersistenceConfig.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Medico.class, codigo);
        } finally {
            em.close();
            emf.close();
        }
    }
}
