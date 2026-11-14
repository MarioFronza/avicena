package br.udesc.ceavi.progii.avicena.doctor.usecase;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.DoctorRepository;
import java.util.Optional;

public class FindDoctorByCpf {

    private final DoctorRepository repository;

    public FindDoctorByCpf(DoctorRepository repository) {
        this.repository = repository;
    }

    public Optional<Doctor> find(String cpf) {
        if (cpf == null || cpf.isBlank()) return Optional.empty();
        return repository.findByCpf(cpf.strip());
    }
}
