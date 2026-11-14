package br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.JpaTransactions;
import br.udesc.ceavi.progii.avicena.control.dao.Lookups;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.domain.NurseRepository;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import java.util.List;
import java.util.Optional;

public class NurseJpaRepository implements NurseRepository {

    @Override
    public Nurse save(Nurse nurse) {
        return JpaTransactions.write(entityManager -> {
            MaritalStatusEntity maritalStatus =
                    Lookups.byCode(entityManager, MaritalStatusEntity.class, nurse.getMaritalStatus());
            NurseEntity entity = nurse.getId() == null ? null : entityManager.find(NurseEntity.class, nurse.getId());
            if (entity == null) {
                entity = NurseMapper.toEntity(nurse, maritalStatus);
                entityManager.persist(entity);
            } else {
                NurseMapper.update(entity, nurse, maritalStatus);
            }
            return NurseMapper.toDomain(entity);
        });
    }

    @Override
    public List<Nurse> findAll() {
        return JpaTransactions.read(entityManager ->
                entityManager
                        .createQuery("SELECT e FROM NurseEntity e ORDER BY e.id", NurseEntity.class)
                        .getResultList()
                        .stream()
                        .map(NurseMapper::toDomain)
                        .toList());
    }

    @Override
    public Optional<Nurse> findById(Long id) {
        return JpaTransactions.read(entityManager ->
                Optional.ofNullable(entityManager.find(NurseEntity.class, id)).map(NurseMapper::toDomain));
    }

    @Override
    public Optional<Nurse> findByCpf(String cpf) {
        return JpaTransactions.read(entityManager -> entityManager
                .createQuery("SELECT e FROM NurseEntity e WHERE e.person.cpf = :cpf ORDER BY e.id", NurseEntity.class)
                .setParameter("cpf", cpf)
                .getResultStream()
                .findFirst()
                .map(NurseMapper::toDomain));
    }

    @Override
    public void delete(Nurse nurse) {
        JpaTransactions.write(entityManager -> {
            Optional.ofNullable(nurse.getId())
                    .map(id -> entityManager.find(NurseEntity.class, id))
                    .ifPresent(entityManager::remove);
            return null;
        });
    }
}
