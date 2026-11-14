package br.udesc.ceavi.progii.avicena.receptionist.usecase;

import br.udesc.ceavi.progii.avicena.receptionist.domain.InvalidReceptionistDataException;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.domain.ReceptionistRepository;
import java.util.Objects;

public class RegisterReceptionist {

    private final ReceptionistRepository repository;

    public RegisterReceptionist(ReceptionistRepository repository) {
        this.repository = repository;
    }

    public Receptionist register(Receptionist receptionist) {
        repository
                .findByCpf(receptionist.getCpf())
                .filter(existing -> !Objects.equals(existing.getId(), receptionist.getId()))
                .ifPresent(existing -> {
                    throw new InvalidReceptionistDataException(
                            "Receptionist cpf " + receptionist.getCpf() + " is already registered");
                });
        return repository.save(receptionist);
    }
}
