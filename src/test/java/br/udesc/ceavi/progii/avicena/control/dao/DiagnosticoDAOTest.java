package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.model.DiagnosticoFinal;
import br.udesc.ceavi.progii.avicena.model.DiagnosticoPrimario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;

class DiagnosticoDAOTest {

    @Test
    void gravarPersistsPrimarioAndCascadesTheLinkedFinal() throws Exception {
        DiagnosticoFinal diagnosticoFinal = new DiagnosticoFinal("Gripe", "Repouso", "Padrao");
        DiagnosticoPrimario diagnosticoPrimario = new DiagnosticoPrimario(120f, 37, 170, 70, "Padrao");
        diagnosticoPrimario.setDiagnosticoFinal(diagnosticoFinal);

        new JPADAO<DiagnosticoPrimario>().inserir(diagnosticoPrimario);

        assertTrue(diagnosticoFinal.getCodigo() != 0);
        assertEquals("Gripe", findFinalByCodigo(diagnosticoFinal.getCodigo()).getDoenca());
    }

    @Test
    void excluirRemovesAPreviouslyPersistedDiagnosticoPrimario() throws Exception {
        DiagnosticoPrimario diagnosticoPrimario = new DiagnosticoPrimario(120f, 37, 170, 70, "Padrao");
        new JPADAO<DiagnosticoPrimario>().inserir(diagnosticoPrimario);
        assertTrue(diagnosticoPrimario.getCodigo() != 0);

        new JPADAO<DiagnosticoPrimario>().deletar(diagnosticoPrimario);

        assertNull(findPrimarioByCodigo(diagnosticoPrimario.getCodigo()));
    }

    private DiagnosticoFinal findFinalByCodigo(long codigo) {
        EntityManagerFactory emf = PersistenceConfig.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(DiagnosticoFinal.class, codigo);
        } finally {
            em.close();
            emf.close();
        }
    }

    private DiagnosticoPrimario findPrimarioByCodigo(long codigo) {
        EntityManagerFactory emf = PersistenceConfig.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(DiagnosticoPrimario.class, codigo);
        } finally {
            em.close();
            emf.close();
        }
    }
}
