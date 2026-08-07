package br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.AddressEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;

final class DoctorMapper {

    private DoctorMapper() {}

    static DoctorEntity toEntity(Doctor doctor, MaritalStatusEntity maritalStatus) {
        PersonEntity person = new PersonEntity(
                null,
                doctor.getName(),
                doctor.getCpf(),
                doctor.getPhone(),
                toEntity(doctor.getAddress()),
                maritalStatus);
        return new DoctorEntity(doctor.getId(), person, doctor.getCrm(), doctor.getSpecialty());
    }

    static Doctor toDomain(DoctorEntity entity) {
        PersonEntity person = entity.getPerson();
        return new Doctor(
                entity.getId(),
                person.getName(),
                person.getCpf(),
                person.getPhone(),
                toDomain(person.getAddress()),
                toDomain(person.getMaritalStatus()),
                entity.getCrm(),
                entity.getSpecialty());
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
