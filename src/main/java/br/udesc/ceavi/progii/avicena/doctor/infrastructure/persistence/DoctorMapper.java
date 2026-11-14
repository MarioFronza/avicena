package br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.MaritalStatusEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonMapper;

final class DoctorMapper {

    private DoctorMapper() {}

    static DoctorEntity toEntity(Doctor doctor, MaritalStatusEntity maritalStatus, SpecialtyEntity specialty) {
        return new DoctorEntity(
                null,
                PersonMapper.toEntity(
                        doctor.getName(), doctor.getCpf(), doctor.getPhone(), doctor.getAddress(), maritalStatus),
                doctor.getCrm(),
                specialty);
    }

    static void update(
            DoctorEntity entity, Doctor doctor, MaritalStatusEntity maritalStatus, SpecialtyEntity specialty) {
        PersonMapper.update(
                entity.getPerson(),
                doctor.getName(),
                doctor.getCpf(),
                doctor.getPhone(),
                doctor.getAddress(),
                maritalStatus);
        entity.update(doctor.getCrm(), specialty);
    }

    static Doctor toDomain(DoctorEntity entity) {
        PersonEntity person = entity.getPerson();
        return new Doctor(
                entity.getId(),
                person.getName(),
                person.getCpf(),
                person.getPhone(),
                PersonMapper.toDomain(person.getAddress()),
                PersonMapper.toDomain(person.getMaritalStatus()),
                entity.getCrm(),
                entity.getSpecialty() == null ? null : entity.getSpecialty().getName());
    }
}
