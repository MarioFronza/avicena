package br.udesc.ceavi.progii.avicena.nurse.usecase;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.domain.NurseRepository;
import java.util.Optional;

public class FindNurseByCpf {

    private final NurseRepository repository;

    public FindNurseByCpf(NurseRepository repository) {
        this.repository = repository;
    }

    public Optional<Nurse> find(String cpf) {
        if (cpf == null || cpf.isBlank()) return Optional.empty();
        return repository.findByCpf(cpf.strip());
    }
}
