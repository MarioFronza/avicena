package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.DiagnosisRepository;
import br.udesc.ceavi.progii.avicena.control.dao.JpaTransactions;
import java.util.List;
import java.util.Optional;

public class DiagnosisJpaRepository implements DiagnosisRepository {

    @Override
    public Diagnosis save(Diagnosis diagnosis) {
        return JpaTransactions.write(entityManager -> {
            AppointmentEntity appointment =
                    entityManager.getReference(AppointmentEntity.class, diagnosis.getAppointmentId());
            DiagnosisEntity entity =
                    diagnosis.getId() == null ? null : entityManager.find(DiagnosisEntity.class, diagnosis.getId());
            if (entity == null) {
                entity = DiagnosisMapper.toEntity(diagnosis, appointment);
                entityManager.persist(entity);
            } else {
                DiagnosisMapper.update(entity, diagnosis, appointment);
            }
            return DiagnosisMapper.toDomain(entity);
        });
    }

    @Override
    public List<Diagnosis> findAll() {
        return JpaTransactions.read(
                entityManager -> entityManager
                        .createQuery("SELECT e FROM DiagnosisEntity e ORDER BY e.id", DiagnosisEntity.class)
                        .getResultList()
                        .stream()
                        .map(DiagnosisMapper::toDomain)
                        .toList());
    }

    @Override
    public Optional<Diagnosis> findById(Long id) {
        return JpaTransactions.read(entityManager -> Optional.ofNullable(entityManager.find(DiagnosisEntity.class, id))
                .map(DiagnosisMapper::toDomain));
    }

    @Override
    public void delete(Diagnosis diagnosis) {
        JpaTransactions.write(entityManager -> {
            Optional.ofNullable(diagnosis.getId())
                    .map(id -> entityManager.find(DiagnosisEntity.class, id))
                    .ifPresent(entityManager::remove);
            return null;
        });
    }
}
