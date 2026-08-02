package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.model.Atendente;
import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;

class AtendenteDAOTest {

    @Test
    void gravarPersistsAnAtendenteThatIsRetrievableAfterward() throws Exception {
        Atendente atendente = atendente();

        new AtendenteDAO().inserir(atendente);
        new JPADAO<Atendente>().inserir(atendente);

        assertEquals(atendente.getNome(), findByCodigo(atendente.getCodigo()).getNome());
    }

    @Test
    void excluirRemovesAPreviouslyPersistedAtendente() throws Exception {
        Atendente atendente = atendente();
        new JPADAO<Atendente>().inserir(atendente);
        assertTrue(atendente.getCodigo() != 0);

        new JPADAO<Atendente>().deletar(atendente);

        assertNull(findByCodigo(atendente.getCodigo()));
    }

    private Atendente atendente() {
        Atendente atendente = new Atendente(40, 3500f, 0, 12345);
        atendente.setNome("Atendente Teste");
        atendente.setCpf("32132132100");
        atendente.setTelefone("48922223333");
        atendente.setEstadoCivil(EstadoCivil.SOLTEIRO);
        return atendente;
    }

    private Atendente findByCodigo(long codigo) {
        EntityManagerFactory emf = PersistenceConfig.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Atendente.class, codigo);
        } finally {
            em.close();
            emf.close();
        }
    }
}
