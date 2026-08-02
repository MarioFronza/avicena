package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.model.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;

class EnderecoDAOTest {

    @Test
    void gravarPersistsAnEnderecoThatIsRetrievableAfterward() throws Exception {
        Endereco endereco = endereco();

        new EnderecoDAO().inserir(endereco);
        new JPADAO<Endereco>().inserir(endereco);

        assertEquals("Centro", findByCodigo(endereco.getCodigo()).getBairro());
    }

    @Test
    void excluirRemovesAPreviouslyPersistedEndereco() throws Exception {
        Endereco endereco = endereco();
        new JPADAO<Endereco>().inserir(endereco);
        assertTrue(endereco.getCodigo() != 0);

        new JPADAO<Endereco>().deletar(endereco);

        assertNull(findByCodigo(endereco.getCodigo()));
    }

    private Endereco endereco() {
        return new Endereco(100, "Apto 1", "88000000", "Rua Teste", "Centro", "Florianopolis");
    }

    private Endereco findByCodigo(long codigo) {
        EntityManagerFactory emf = PersistenceConfig.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Endereco.class, codigo);
        } finally {
            em.close();
            emf.close();
        }
    }
}
