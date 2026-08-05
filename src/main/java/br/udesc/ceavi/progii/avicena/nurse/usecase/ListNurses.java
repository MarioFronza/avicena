package br.udesc.ceavi.progii.avicena.nurse.usecase;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.domain.NurseRepository;
import java.util.List;

public class ListNurses {

    private final NurseRepository repository;

    public ListNurses(NurseRepository repository) {
        this.repository = repository;
    }

    public List<Nurse> list() {
        return repository.findAll();
    }
}
