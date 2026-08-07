package br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.domain.Patient;

final class PatientMapper {

    private PatientMapper() {}

    static PatientEntity toEntity(Patient patient, MaritalStatusEntity maritalStatus) {
        PersonEntity person = new PersonEntity(
                null,
                patient.getName(),
                patient.getCpf(),
                patient.getPhone(),
                toEntity(patient.getAddress()),
                maritalStatus);
        return new PatientEntity(patient.getId(), person);
    }

    static Patient toDomain(PatientEntity entity) {
        PersonEntity person = entity.getPerson();
        return new Patient(
                entity.getId(),
                person.getName(),
                person.getCpf(),
                person.getPhone(),
                toDomain(person.getAddress()),
                toDomain(person.getMaritalStatus()));
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
