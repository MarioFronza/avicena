package br.udesc.ceavi.progii.avicena.control.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.function.Function;

/** Opens an EntityManager per unit of work on the shared factory and always closes it. */
public final class JpaTransactions {

    private JpaTransactions() {}

    public static <T> T read(Function<EntityManager, T> work) {
        EntityManager entityManager = PersistenceConfig.entityManagerFactory().createEntityManager();
        try {
            return work.apply(entityManager);
        } finally {
            entityManager.close();
        }
    }

    public static <T> T write(Function<EntityManager, T> work) {
        EntityManager entityManager = PersistenceConfig.entityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T result = work.apply(entityManager);
            transaction.commit();
            return result;
        } catch (RuntimeException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
