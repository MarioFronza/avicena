package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.AppointmentRepository;
import br.udesc.ceavi.progii.avicena.control.dao.JpaTransactions;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorEntity;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence.NurseEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class AppointmentJpaRepository implements AppointmentRepository {

    @Override
    public Appointment save(Appointment appointment) {
        return JpaTransactions.write(entityManager -> {
            AppointmentEntity entity = AppointmentMapper.toEntity(
                    appointment,
                    entityManager.getReference(PatientEntity.class, appointment.getPatientId()),
                    reference(entityManager, DoctorEntity.class, appointment.getDoctorId()),
                    reference(entityManager, NurseEntity.class, appointment.getNurseId()),
                    entityManager
                            .createQuery(
                                    "SELECT u FROM UrgencyStatusEntity u WHERE u.code = :code",
                                    UrgencyStatusEntity.class)
                            .setParameter("code", appointment.getUrgencyStatus().name())
                            .getSingleResult());
            entityManager.persist(entity);
            return AppointmentMapper.toDomain(entity);
        });
    }

    @Override
    public List<Appointment> findAll() {
        return JpaTransactions.read(entityManager -> entityManager
                .createQuery("SELECT e FROM AppointmentEntity e ORDER BY e.id", AppointmentEntity.class)
                .getResultList()
                .stream()
                .map(AppointmentMapper::toDomain)
                .toList());
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return JpaTransactions.read(
                entityManager -> Optional.ofNullable(entityManager.find(AppointmentEntity.class, id))
                        .map(AppointmentMapper::toDomain));
    }

    @Override
    public void delete(Appointment appointment) {
        JpaTransactions.write(entityManager -> {
            Optional.ofNullable(appointment.getId())
                    .map(id -> entityManager.find(AppointmentEntity.class, id))
                    .ifPresent(entityManager::remove);
            return null;
        });
    }

    private static <T> T reference(EntityManager entityManager, Class<T> type, Long id) {
        return id == null ? null : entityManager.getReference(type, id);
    }
}
