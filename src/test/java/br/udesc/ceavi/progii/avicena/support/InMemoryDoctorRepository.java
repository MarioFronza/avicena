package br.udesc.ceavi.progii.avicena.support;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.DoctorRepository;
import java.util.Optional;

public class InMemoryDoctorRepository extends InMemoryRepository<Doctor> implements DoctorRepository {

    @Override
    protected Long idOf(Doctor doctor) {
        return doctor.getId();
    }

    @Override
    protected Doctor withId(Doctor doctor, Long id) {
        return new Doctor(
                id,
                doctor.getName(),
                doctor.getCpf(),
                doctor.getPhone(),
                doctor.getAddress(),
                doctor.getMaritalStatus(),
                doctor.getCrm(),
                doctor.getSpecialty());
    }

    @Override
    public Optional<Doctor> findByCpf(String cpf) {
        return findAll().stream().filter(doctor -> doctor.getCpf().equals(cpf)).findFirst();
    }
}
