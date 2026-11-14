package br.udesc.ceavi.progii.avicena.doctor.usecase;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.DoctorRepository;
import br.udesc.ceavi.progii.avicena.doctor.domain.InvalidDoctorDataException;
import java.util.Objects;

public class RegisterDoctor {

    private final DoctorRepository repository;

    public RegisterDoctor(DoctorRepository repository) {
        this.repository = repository;
    }

    public Doctor register(Doctor doctor) {
        repository
                .findByCpf(doctor.getCpf())
                .filter(existing -> !Objects.equals(existing.getId(), doctor.getId()))
                .ifPresent(existing -> {
                    throw new InvalidDoctorDataException("Doctor cpf " + doctor.getCpf() + " is already registered");
                });
        return repository.save(doctor);
    }
}
