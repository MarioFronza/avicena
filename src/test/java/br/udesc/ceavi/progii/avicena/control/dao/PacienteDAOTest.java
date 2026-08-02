package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.model.EstadoCivil;
import br.udesc.ceavi.progii.avicena.model.Paciente;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;

class PacienteDAOTest {

    @Test
    void gravarPersistsAPacienteThatIsRetrievableAfterward() throws Exception {
        Paciente paciente = new Paciente("Maria Teste", "12345678900", 0, "48999990000", null, EstadoCivil.SOLTEIRO);

        new PacienteDAO().inserir(paciente);
        new JPADAO<Paciente>().inserir(paciente);

        Paciente found = findByCpf(paciente.getCpf());
        assertEquals(paciente.getNome(), found.getNome());
    }

    @Test
    void excluirRemovesAPreviouslyPersistedPaciente() throws Exception {
        Paciente paciente = new Paciente("Joao Teste", "98765432100", 0, "48988880000", null, EstadoCivil.SOLTEIRO);
        new JPADAO<Paciente>().inserir(paciente);
        assertTrue(paciente.getCodigo() != 0);

        new JPADAO<Paciente>().deletar(paciente);

        assertNull(findByCodigo(paciente.getCodigo()));
    }

    private Paciente findByCpf(String cpf) {
        EntityManagerFactory emf = PersistenceConfig.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Paciente p WHERE p.cpf = :cpf", Paciente.class)
                    .setParameter("cpf", cpf)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
            emf.close();
        }
    }

    private Paciente findByCodigo(long codigo) {
        EntityManagerFactory emf = PersistenceConfig.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Paciente.class, codigo);
        } finally {
            em.close();
            emf.close();
        }
    }
}
