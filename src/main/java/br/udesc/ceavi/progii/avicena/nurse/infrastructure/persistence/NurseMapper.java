package br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.AddressEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;

final class NurseMapper {

    private NurseMapper() {}

    static NurseEntity toEntity(Nurse nurse, MaritalStatusEntity maritalStatus) {
        PersonEntity person = new PersonEntity(
                null, nurse.getName(), nurse.getCpf(), nurse.getPhone(), toEntity(nurse.getAddress()), maritalStatus);
        return new NurseEntity(nurse.getId(), person, nurse.getFormation(), nurse.getHoursCompleted());
    }

    static Nurse toDomain(NurseEntity entity) {
        PersonEntity person = entity.getPerson();
        return new Nurse(
                entity.getId(),
                person.getName(),
                person.getCpf(),
                person.getPhone(),
                toDomain(person.getAddress()),
                toDomain(person.getMaritalStatus()),
                entity.getFormation(),
                entity.getHoursCompleted());
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
