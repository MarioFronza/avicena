package br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;

/** The person part shared by the four person verticals' mappers. */
public final class PersonMapper {

    private PersonMapper() {}

    public static PersonEntity toEntity(
            String name, String cpf, String phone, Address address, MaritalStatusEntity maritalStatus) {
        return new PersonEntity(null, name, cpf, phone, toEntity(address), maritalStatus);
    }

    public static void update(
            PersonEntity person,
            String name,
            String cpf,
            String phone,
            Address address,
            MaritalStatusEntity maritalStatus) {
        person.update(name, cpf, phone, update(person.getAddress(), address), maritalStatus);
    }

    public static Address toDomain(AddressEntity entity) {
        if (entity == null) return null;
        return new Address(
                entity.getNumber(),
                entity.getComplement(),
                entity.getZipCode(),
                entity.getStreet(),
                entity.getNeighborhood(),
                entity.getCity());
    }

    public static MaritalStatus toDomain(MaritalStatusEntity entity) {
        return entity == null ? null : MaritalStatus.valueOf(entity.getCode());
    }

    private static AddressEntity update(AddressEntity current, Address address) {
        if (address == null) return null;
        if (current == null) return toEntity(address);
        current.update(
                address.getNumber(),
                address.getComplement(),
                address.getZipCode(),
                address.getStreet(),
                address.getNeighborhood(),
                address.getCity());
        return current;
    }

    private static AddressEntity toEntity(Address address) {
        if (address == null) return null;
        return new AddressEntity(
                null,
                address.getNumber(),
                address.getComplement(),
                address.getZipCode(),
                address.getStreet(),
                address.getNeighborhood(),
                address.getCity());
    }
}
