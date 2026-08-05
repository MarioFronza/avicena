package br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.domain.NurseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class NurseJpaRepository implements NurseRepository {

    private final EntityManagerFactory entityManagerFactory;

    public NurseJpaRepository() {
        this.entityManagerFactory = PersistenceConfig.createEntityManagerFactory();
    }

    @Override
    public Nurse save(Nurse nurse) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            NurseEntity entity = NurseMapper.toEntity(nurse);
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return NurseMapper.toDomain(entity);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Nurse> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT n FROM NurseEntity n", NurseEntity.class).getResultList().stream()
                    .map(NurseMapper::toDomain)
                    .toList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Nurse nurse) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            NurseEntity entity = entityManager.find(NurseEntity.class, nurse.getId());
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
