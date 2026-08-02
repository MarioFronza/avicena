package br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;

final class PatientMapper {

    private PatientMapper() {}

    static PatientEntity toEntity(Patient patient) {
        return new PatientEntity(
                patient.getId(),
                patient.getName(),
                patient.getCpf(),
                patient.getPhone(),
                toEntity(patient.getAddress()),
                patient.getMaritalStatus());
    }

    static Patient toDomain(PatientEntity entity) {
        return new Patient(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                entity.getPhone(),
                toDomain(entity.getAddress()),
                entity.getMaritalStatus());
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
