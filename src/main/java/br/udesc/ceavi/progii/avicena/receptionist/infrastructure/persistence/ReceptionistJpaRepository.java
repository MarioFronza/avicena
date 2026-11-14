package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.JpaTransactions;
import br.udesc.ceavi.progii.avicena.control.dao.Lookups;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.domain.ReceptionistRepository;
import java.util.List;
import java.util.Optional;

public class ReceptionistJpaRepository implements ReceptionistRepository {

    @Override
    public Receptionist save(Receptionist receptionist) {
        return JpaTransactions.write(entityManager -> {
            MaritalStatusEntity maritalStatus =
                    Lookups.byCode(entityManager, MaritalStatusEntity.class, receptionist.getMaritalStatus());
            ReceptionistEntity entity = receptionist.getId() == null
                    ? null
                    : entityManager.find(ReceptionistEntity.class, receptionist.getId());
            if (entity == null) {
                entity = ReceptionistMapper.toEntity(receptionist, maritalStatus);
                entityManager.persist(entity);
            } else {
                ReceptionistMapper.update(entity, receptionist, maritalStatus);
            }
            return ReceptionistMapper.toDomain(entity);
        });
    }

    @Override
    public List<Receptionist> findAll() {
        return JpaTransactions.read(entityManager -> entityManager
                .createQuery("SELECT e FROM ReceptionistEntity e ORDER BY e.id", ReceptionistEntity.class)
                .getResultList()
                .stream()
                .map(ReceptionistMapper::toDomain)
                .toList());
    }

    @Override
    public Optional<Receptionist> findById(Long id) {
        return JpaTransactions.read(
                entityManager -> Optional.ofNullable(entityManager.find(ReceptionistEntity.class, id))
                        .map(ReceptionistMapper::toDomain));
    }

    @Override
    public Optional<Receptionist> findByCpf(String cpf) {
        return JpaTransactions.read(entityManager -> entityManager
                .createQuery(
                        "SELECT e FROM ReceptionistEntity e WHERE e.person.cpf = :cpf ORDER BY e.id",
                        ReceptionistEntity.class)
                .setParameter("cpf", cpf)
                .getResultStream()
                .findFirst()
                .map(ReceptionistMapper::toDomain));
    }

    @Override
    public void delete(Receptionist receptionist) {
        JpaTransactions.write(entityManager -> {
            Optional.ofNullable(receptionist.getId())
                    .map(id -> entityManager.find(ReceptionistEntity.class, id))
                    .ifPresent(entityManager::remove);
            return null;
        });
    }
}
