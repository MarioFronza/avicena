package br.udesc.ceavi.progii.avicena.nurse.usecase;

import br.udesc.ceavi.progii.avicena.nurse.domain.InvalidNurseDataException;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.domain.NurseRepository;
import java.util.Objects;

public class RegisterNurse {

    private final NurseRepository repository;

    public RegisterNurse(NurseRepository repository) {
        this.repository = repository;
    }

    public Nurse register(Nurse nurse) {
        repository
                .findByCpf(nurse.getCpf())
                .filter(existing -> !Objects.equals(existing.getId(), nurse.getId()))
                .ifPresent(existing -> {
                    throw new InvalidNurseDataException("Nurse cpf " + nurse.getCpf() + " is already registered");
                });
        return repository.save(nurse);
    }
}
