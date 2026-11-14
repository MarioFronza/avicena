package br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.JpaTransactions;
import br.udesc.ceavi.progii.avicena.control.dao.Lookups;
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
            MaritalStatusEntity maritalStatus =
                    Lookups.byCode(entityManager, MaritalStatusEntity.class, doctor.getMaritalStatus());
            SpecialtyEntity specialty = findOrCreateSpecialty(entityManager, doctor);
            DoctorEntity entity =
                    doctor.getId() == null ? null : entityManager.find(DoctorEntity.class, doctor.getId());
            if (entity == null) {
                entity = DoctorMapper.toEntity(doctor, maritalStatus, specialty);
                entityManager.persist(entity);
            } else {
                DoctorMapper.update(entity, doctor, maritalStatus, specialty);
            }
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
