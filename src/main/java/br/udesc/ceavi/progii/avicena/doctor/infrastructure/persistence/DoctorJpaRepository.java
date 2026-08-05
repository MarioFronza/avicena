package br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.DoctorRepository;
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
            DoctorEntity entity = DoctorMapper.toEntity(doctor);
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return DoctorMapper.toDomain(entity);
        } finally {
            entityManager.close();
        }
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
