package br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;

final class PatientMapper {

    private PatientMapper() {}

    static PatientEntity toEntity(Patient patient, MaritalStatusEntity maritalStatus) {
        return new PatientEntity(
                null,
                PersonMapper.toEntity(
                        patient.getName(), patient.getCpf(), patient.getPhone(), patient.getAddress(), maritalStatus));
    }

    static void update(PatientEntity entity, Patient patient, MaritalStatusEntity maritalStatus) {
        PersonMapper.update(
                entity.getPerson(),
                patient.getName(),
                patient.getCpf(),
                patient.getPhone(),
                patient.getAddress(),
                maritalStatus);
    }

    static Patient toDomain(PatientEntity entity) {
        PersonEntity person = entity.getPerson();
        return new Patient(
                entity.getId(),
                person.getName(),
                person.getCpf(),
                person.getPhone(),
                PersonMapper.toDomain(person.getAddress()),
                PersonMapper.toDomain(person.getMaritalStatus()));
    }
}
