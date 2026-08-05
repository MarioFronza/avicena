package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.AddressEntity;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;

final class ReceptionistMapper {

    private ReceptionistMapper() {}

    static ReceptionistEntity toEntity(Receptionist receptionist) {
        return new ReceptionistEntity(
                receptionist.getId(),
                receptionist.getName(),
                receptionist.getCpf(),
                receptionist.getPhone(),
                toEntity(receptionist.getAddress()),
                receptionist.getMaritalStatus(),
                receptionist.getWorkHours(),
                receptionist.getSalary(),
                receptionist.getOvertimeHours(),
                receptionist.getLaborCardNumber());
    }

    static Receptionist toDomain(ReceptionistEntity entity) {
        return new Receptionist(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                entity.getPhone(),
                toDomain(entity.getAddress()),
                entity.getMaritalStatus(),
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
}
