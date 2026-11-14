package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonMapper;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;

final class ReceptionistMapper {

    private ReceptionistMapper() {}

    static ReceptionistEntity toEntity(Receptionist receptionist, MaritalStatusEntity maritalStatus) {
        return new ReceptionistEntity(
                null,
                PersonMapper.toEntity(
                        receptionist.getName(),
                        receptionist.getCpf(),
                        receptionist.getPhone(),
                        receptionist.getAddress(),
                        maritalStatus),
                receptionist.getWorkHours(),
                receptionist.getSalary(),
                receptionist.getOvertimeHours(),
                receptionist.getLaborCardNumber());
    }

    static void update(ReceptionistEntity entity, Receptionist receptionist, MaritalStatusEntity maritalStatus) {
        PersonMapper.update(
                entity.getPerson(),
                receptionist.getName(),
                receptionist.getCpf(),
                receptionist.getPhone(),
                receptionist.getAddress(),
                maritalStatus);
        entity.update(
                receptionist.getWorkHours(),
                receptionist.getSalary(),
                receptionist.getOvertimeHours(),
                receptionist.getLaborCardNumber());
    }

    static Receptionist toDomain(ReceptionistEntity entity) {
        PersonEntity person = entity.getPerson();
        return new Receptionist(
                entity.getId(),
                person.getName(),
                person.getCpf(),
                person.getPhone(),
                PersonMapper.toDomain(person.getAddress()),
                PersonMapper.toDomain(person.getMaritalStatus()),
                entity.getWorkHours(),
                entity.getSalary(),
                entity.getOvertimeHours(),
                entity.getLaborCardNumber());
    }
}
