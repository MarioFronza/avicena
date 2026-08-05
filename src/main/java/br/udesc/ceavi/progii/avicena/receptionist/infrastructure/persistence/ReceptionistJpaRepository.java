package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.domain.ReceptionistRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class ReceptionistJpaRepository implements ReceptionistRepository {

    private final EntityManagerFactory entityManagerFactory;

    public ReceptionistJpaRepository() {
        this.entityManagerFactory = PersistenceConfig.createEntityManagerFactory();
    }

    @Override
    public Receptionist save(Receptionist receptionist) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            ReceptionistEntity entity = ReceptionistMapper.toEntity(receptionist);
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return ReceptionistMapper.toDomain(entity);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Receptionist> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT r FROM ReceptionistEntity r", ReceptionistEntity.class)
                    .getResultList()
                    .stream()
                    .map(ReceptionistMapper::toDomain)
                    .toList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Receptionist receptionist) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            ReceptionistEntity entity = entityManager.find(ReceptionistEntity.class, receptionist.getId());
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
