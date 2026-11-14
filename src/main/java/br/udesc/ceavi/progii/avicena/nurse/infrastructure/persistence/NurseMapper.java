package br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonMapper;

final class NurseMapper {

    private NurseMapper() {}

    static NurseEntity toEntity(Nurse nurse, MaritalStatusEntity maritalStatus) {
        return new NurseEntity(
                null,
                PersonMapper.toEntity(
                        nurse.getName(), nurse.getCpf(), nurse.getPhone(), nurse.getAddress(), maritalStatus),
                nurse.getFormation(),
                nurse.getHoursCompleted());
    }

    static void update(NurseEntity entity, Nurse nurse, MaritalStatusEntity maritalStatus) {
        PersonMapper.update(
                entity.getPerson(),
                nurse.getName(),
                nurse.getCpf(),
                nurse.getPhone(),
                nurse.getAddress(),
                maritalStatus);
        entity.update(nurse.getFormation(), nurse.getHoursCompleted());
    }

    static Nurse toDomain(NurseEntity entity) {
        PersonEntity person = entity.getPerson();
        return new Nurse(
                entity.getId(),
                person.getName(),
                person.getCpf(),
                person.getPhone(),
                PersonMapper.toDomain(person.getAddress()),
                PersonMapper.toDomain(person.getMaritalStatus()),
                entity.getFormation(),
                entity.getHoursCompleted());
    }
}
