package br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.JpaTransactions;
import br.udesc.ceavi.progii.avicena.control.dao.Lookups;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.domain.PatientRepository;
import java.util.List;
import java.util.Optional;

public class PatientJpaRepository implements PatientRepository {

    @Override
    public Patient save(Patient patient) {
        return JpaTransactions.write(entityManager -> {
            MaritalStatusEntity maritalStatus =
                    Lookups.byCode(entityManager, MaritalStatusEntity.class, patient.getMaritalStatus());
            PatientEntity entity =
                    patient.getId() == null ? null : entityManager.find(PatientEntity.class, patient.getId());
            if (entity == null) {
                entity = PatientMapper.toEntity(patient, maritalStatus);
                entityManager.persist(entity);
            } else {
                PatientMapper.update(entity, patient, maritalStatus);
            }
            return PatientMapper.toDomain(entity);
        });
    }

    @Override
    public List<Patient> findAll() {
        return JpaTransactions.read(
                entityManager -> entityManager
                        .createQuery("SELECT e FROM PatientEntity e ORDER BY e.id", PatientEntity.class)
                        .getResultList()
                        .stream()
                        .map(PatientMapper::toDomain)
                        .toList());
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return JpaTransactions.read(entityManager ->
                Optional.ofNullable(entityManager.find(PatientEntity.class, id)).map(PatientMapper::toDomain));
    }

    @Override
    public Optional<Patient> findByCpf(String cpf) {
        return JpaTransactions.read(entityManager -> entityManager
                .createQuery(
                        "SELECT e FROM PatientEntity e WHERE e.person.cpf = :cpf ORDER BY e.id", PatientEntity.class)
                .setParameter("cpf", cpf)
                .getResultStream()
                .findFirst()
                .map(PatientMapper::toDomain));
    }

    @Override
    public void delete(Patient patient) {
        JpaTransactions.write(entityManager -> {
            Optional.ofNullable(patient.getId())
                    .map(id -> entityManager.find(PatientEntity.class, id))
                    .ifPresent(entityManager::remove);
            return null;
        });
    }
}
