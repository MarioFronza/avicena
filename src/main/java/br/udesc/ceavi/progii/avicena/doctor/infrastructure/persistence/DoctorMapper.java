package br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.AddressEntity;

final class DoctorMapper {

    private DoctorMapper() {}

    static DoctorEntity toEntity(Doctor doctor) {
        return new DoctorEntity(
                doctor.getId(),
                doctor.getName(),
                doctor.getCpf(),
                doctor.getPhone(),
                toEntity(doctor.getAddress()),
                doctor.getMaritalStatus(),
                doctor.getCrm(),
                doctor.getSpecialty());
    }

    static Doctor toDomain(DoctorEntity entity) {
        return new Doctor(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                entity.getPhone(),
                toDomain(entity.getAddress()),
                entity.getMaritalStatus(),
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
}
