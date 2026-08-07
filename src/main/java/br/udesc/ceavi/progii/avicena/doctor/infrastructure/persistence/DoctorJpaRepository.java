package br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.DoctorRepository;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class DoctorJpaRepository implements DoctorRepository {

    private final EntityManagerFactory entityManagerFactory;

    public DoctorJpaRepository() {
        this.entityManagerFactory = PersistenceConfig.createEntityManagerFactory();
    }

    @Override
    public Doctor save(Doctor doctor) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            MaritalStatusEntity maritalStatus = findMaritalStatus(entityManager, doctor);
            DoctorEntity entity = DoctorMapper.toEntity(doctor, maritalStatus);
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return DoctorMapper.toDomain(entity);
        } finally {
            entityManager.close();
        }
    }

    private MaritalStatusEntity findMaritalStatus(EntityManager entityManager, Doctor doctor) {
        if (doctor.getMaritalStatus() == null) {
            return null;
        }
        return entityManager
                .createQuery("SELECT m FROM MaritalStatusEntity m WHERE m.code = :code", MaritalStatusEntity.class)
                .setParameter("code", doctor.getMaritalStatus().name())
                .getSingleResult();
    }

    @Override
    public List<Doctor> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT d FROM DoctorEntity d", DoctorEntity.class)
                    .getResultList()
                    .stream()
                    .map(DoctorMapper::toDomain)
                    .toList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Doctor doctor) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            DoctorEntity entity = entityManager.find(DoctorEntity.class, doctor.getId());
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
