package br.udesc.ceavi.progii.avicena.receptionist.usecase;

import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.domain.ReceptionistRepository;
import java.util.Optional;

public class FindReceptionistByCpf {

    private final ReceptionistRepository repository;

    public FindReceptionistByCpf(ReceptionistRepository repository) {
        this.repository = repository;
    }

    public Optional<Receptionist> find(String cpf) {
        if (cpf == null || cpf.isBlank()) return Optional.empty();
        return repository.findByCpf(cpf.strip());
    }
}
