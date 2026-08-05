package br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.AddressEntity;

final class NurseMapper {

    private NurseMapper() {}

    static NurseEntity toEntity(Nurse nurse) {
        return new NurseEntity(
                nurse.getId(),
                nurse.getName(),
                nurse.getCpf(),
                nurse.getPhone(),
                toEntity(nurse.getAddress()),
                nurse.getMaritalStatus(),
                nurse.getFormation(),
                nurse.getHoursCompleted());
    }

    static Nurse toDomain(NurseEntity entity) {
        return new Nurse(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                entity.getPhone(),
                toDomain(entity.getAddress()),
                entity.getMaritalStatus(),
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
}
