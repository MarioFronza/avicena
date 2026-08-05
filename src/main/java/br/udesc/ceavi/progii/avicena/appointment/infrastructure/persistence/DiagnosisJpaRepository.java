package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.DiagnosisRepository;
import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class DiagnosisJpaRepository implements DiagnosisRepository {

    private final EntityManagerFactory entityManagerFactory;

    public DiagnosisJpaRepository() {
        this.entityManagerFactory = PersistenceConfig.createEntityManagerFactory();
    }

    @Override
    public Diagnosis save(Diagnosis diagnosis) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            AppointmentEntity appointment =
                    entityManager.getReference(AppointmentEntity.class, diagnosis.getAppointmentId());
            DiagnosisEntity entity = DiagnosisMapper.toEntity(diagnosis, appointment);
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return DiagnosisMapper.toDomain(entity);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Diagnosis diagnosis) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            DiagnosisEntity entity = entityManager.find(DiagnosisEntity.class, diagnosis.getId());
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
