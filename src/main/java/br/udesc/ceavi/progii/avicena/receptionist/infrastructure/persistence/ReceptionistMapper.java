package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.AddressEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;

final class ReceptionistMapper {

    private ReceptionistMapper() {}

    static ReceptionistEntity toEntity(Receptionist receptionist, MaritalStatusEntity maritalStatus) {
        PersonEntity person = new PersonEntity(
                null,
                receptionist.getName(),
                receptionist.getCpf(),
                receptionist.getPhone(),
                toEntity(receptionist.getAddress()),
                maritalStatus);
        return new ReceptionistEntity(
                receptionist.getId(),
                person,
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
                toDomain(person.getAddress()),
                toDomain(person.getMaritalStatus()),
                entity.getWorkHours(),
                entity.getSalary(),
                entity.getOvertimeHours(),
                entity.getLaborCardNumber());
    }

    private static AddressEntity toEntity(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressEntity(
                null,
                address.getNumber(),
                address.getComplement(),
                address.getZipCode(),
                address.getStreet(),
                address.getNeighborhood(),
                address.getCity());
    }

    private static Address toDomain(AddressEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Address(
                entity.getNumber(),
                entity.getComplement(),
                entity.getZipCode(),
                entity.getStreet(),
                entity.getNeighborhood(),
                entity.getCity());
    }

    private static MaritalStatus toDomain(MaritalStatusEntity entity) {
        return entity == null ? null : MaritalStatus.valueOf(entity.getCode());
    }
}
