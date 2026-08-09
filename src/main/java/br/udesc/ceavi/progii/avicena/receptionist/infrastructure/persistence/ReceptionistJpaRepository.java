package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
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
            MaritalStatusEntity maritalStatus = findMaritalStatus(entityManager, receptionist);
            ReceptionistEntity entity = ReceptionistMapper.toEntity(receptionist, maritalStatus);
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return ReceptionistMapper.toDomain(entity);
        } finally {
            entityManager.close();
        }
    }

    private MaritalStatusEntity findMaritalStatus(EntityManager entityManager, Receptionist receptionist) {
        if (receptionist.getMaritalStatus() == null) {
            return null;
        }
        return entityManager
                .createQuery("SELECT m FROM MaritalStatusEntity m WHERE m.code = :code", MaritalStatusEntity.class)
                .setParameter("code", receptionist.getMaritalStatus().name())
                .getSingleResult();
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
