package br.udesc.ceavi.progii.avicena.receptionist.usecase;

import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.domain.ReceptionistRepository;

public class RegisterReceptionist {

    private final ReceptionistRepository repository;

    public RegisterReceptionist(ReceptionistRepository repository) {
        this.repository = repository;
    }

    public Receptionist register(Receptionist receptionist) {
        return repository.save(receptionist);
    }
}
