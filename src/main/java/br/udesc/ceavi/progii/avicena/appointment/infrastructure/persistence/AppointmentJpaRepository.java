package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.appointment.domain.Appointment;
import br.udesc.ceavi.progii.avicena.appointment.domain.AppointmentRepository;
import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorEntity;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence.NurseEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class AppointmentJpaRepository implements AppointmentRepository {

    private final EntityManagerFactory entityManagerFactory;

    public AppointmentJpaRepository() {
        this.entityManagerFactory = PersistenceConfig.createEntityManagerFactory();
    }

    @Override
    public Appointment save(Appointment appointment) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            PatientEntity patient = entityManager.getReference(PatientEntity.class, appointment.getPatientId());
            DoctorEntity doctor = appointment.getDoctorId() == null
                    ? null
                    : entityManager.getReference(DoctorEntity.class, appointment.getDoctorId());
            NurseEntity nurse = appointment.getNurseId() == null
                    ? null
                    : entityManager.getReference(NurseEntity.class, appointment.getNurseId());
            AppointmentEntity entity = AppointmentMapper.toEntity(appointment, patient, doctor, nurse);
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return AppointmentMapper.toDomain(entity);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Appointment> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT a FROM AppointmentEntity a", AppointmentEntity.class)
                    .getResultList()
                    .stream()
                    .map(AppointmentMapper::toDomain)
                    .toList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Appointment appointment) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            AppointmentEntity entity = entityManager.find(AppointmentEntity.class, appointment.getId());
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
