package br.udesc.ceavi.progii.avicena.receptionist.usecase;

import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.domain.ReceptionistRepository;

public class DeleteReceptionist {

    private final ReceptionistRepository repository;

    public DeleteReceptionist(ReceptionistRepository repository) {
        this.repository = repository;
    }

    public void delete(Receptionist receptionist) {
        repository.delete(receptionist);
    }
}
