package br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.domain.PatientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class PatientJpaRepository implements PatientRepository {

    private final EntityManagerFactory entityManagerFactory;

    public PatientJpaRepository() {
        this.entityManagerFactory = PersistenceConfig.createEntityManagerFactory();
    }

    @Override
    public Patient save(Patient patient) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            PatientEntity entity = PatientMapper.toEntity(patient);
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return PatientMapper.toDomain(entity);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Patient> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT p FROM PatientEntity p", PatientEntity.class)
                    .getResultList()
                    .stream()
                    .map(PatientMapper::toDomain)
                    .toList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Patient patient) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            PatientEntity entity = entityManager.find(PatientEntity.class, patient.getId());
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
