package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.model.Enfermeiro;
import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import org.junit.jupiter.api.Test;

class EnfermeiroDAOTest {

    @Test
    void gravarPersistsAnEnfermeiroThatIsRetrievableAfterward() throws Exception {
        Enfermeiro enfermeiro = enfermeiro();

        new EnfermeiroDAO().inserir(enfermeiro);
        new JPADAO<Enfermeiro>().inserir(enfermeiro);

        assertEquals(enfermeiro.getNome(), findByCodigo(enfermeiro.getCodigo()).getNome());
    }

    @Test
    void excluirRemovesAPreviouslyPersistedEnfermeiro() throws Exception {
        Enfermeiro enfermeiro = enfermeiro();
        new JPADAO<Enfermeiro>().inserir(enfermeiro);
        assertTrue(enfermeiro.getCodigo() != 0);

        new JPADAO<Enfermeiro>().deletar(enfermeiro);

        assertNull(findByCodigo(enfermeiro.getCodigo()));
    }

    private Enfermeiro enfermeiro() {
        Enfermeiro enfermeiro = new Enfermeiro(0, "Tecnico em Enfermagem", 1200, List.of());
        enfermeiro.setNome("Enfermeiro Teste");
        enfermeiro.setCpf("45645645600");
        enfermeiro.setTelefone("48933334444");
        enfermeiro.setEstadoCivil(EstadoCivil.SOLTEIRO);
        return enfermeiro;
    }

    private Enfermeiro findByCodigo(long codigo) {
        EntityManagerFactory emf = PersistenceConfig.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Enfermeiro.class, codigo);
        } finally {
            em.close();
            emf.close();
        }
    }
}
