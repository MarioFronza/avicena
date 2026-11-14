package br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.JpaTransactions;
import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.DoctorRepository;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class DoctorJpaRepository implements DoctorRepository {

    @Override
    public Doctor save(Doctor doctor) {
        return JpaTransactions.write(entityManager -> {
            DoctorEntity entity = DoctorMapper.toEntity(
                    doctor, findMaritalStatus(entityManager, doctor), findOrCreateSpecialty(entityManager, doctor));
            entityManager.persist(entity);
            return DoctorMapper.toDomain(entity);
        });
    }

    @Override
    public List<Doctor> findAll() {
        return JpaTransactions.read(entityManager ->
                entityManager
                        .createQuery("SELECT e FROM DoctorEntity e ORDER BY e.id", DoctorEntity.class)
                        .getResultList()
                        .stream()
                        .map(DoctorMapper::toDomain)
                        .toList());
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return JpaTransactions.read(entityManager ->
                Optional.ofNullable(entityManager.find(DoctorEntity.class, id)).map(DoctorMapper::toDomain));
    }

    @Override
    public Optional<Doctor> findByCpf(String cpf) {
        return JpaTransactions.read(entityManager -> entityManager
                .createQuery("SELECT e FROM DoctorEntity e WHERE e.person.cpf = :cpf ORDER BY e.id", DoctorEntity.class)
                .setParameter("cpf", cpf)
                .getResultStream()
                .findFirst()
                .map(DoctorMapper::toDomain));
    }

    @Override
    public void delete(Doctor doctor) {
        JpaTransactions.write(entityManager -> {
            Optional.ofNullable(doctor.getId())
                    .map(id -> entityManager.find(DoctorEntity.class, id))
                    .ifPresent(entityManager::remove);
            return null;
        });
    }

    private static MaritalStatusEntity findMaritalStatus(EntityManager entityManager, Doctor doctor) {
        if (doctor.getMaritalStatus() == null) {
            return null;
        }
        return entityManager
                .createQuery("SELECT m FROM MaritalStatusEntity m WHERE m.code = :code", MaritalStatusEntity.class)
                .setParameter("code", doctor.getMaritalStatus().name())
                .getSingleResult();
    }

    private static SpecialtyEntity findOrCreateSpecialty(EntityManager entityManager, Doctor doctor) {
        if (doctor.getSpecialty() == null) {
            return null;
        }
        return entityManager
                .createQuery("SELECT s FROM SpecialtyEntity s WHERE s.name = :name", SpecialtyEntity.class)
                .setParameter("name", doctor.getSpecialty())
                .getResultStream()
                .findFirst()
                .orElseGet(() -> {
                    SpecialtyEntity specialty = new SpecialtyEntity(null, doctor.getSpecialty());
                    entityManager.persist(specialty);
                    return specialty;
                });
    }
}
