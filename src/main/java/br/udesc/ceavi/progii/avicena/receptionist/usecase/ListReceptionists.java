package br.udesc.ceavi.progii.avicena.receptionist.usecase;

import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.domain.ReceptionistRepository;
import java.util.List;

public class ListReceptionists {

    private final ReceptionistRepository repository;

    public ListReceptionists(ReceptionistRepository repository) {
        this.repository = repository;
    }

    public List<Receptionist> list() {
        return repository.findAll();
    }
}
