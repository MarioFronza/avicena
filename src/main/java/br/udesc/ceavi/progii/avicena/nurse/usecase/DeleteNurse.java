package br.udesc.ceavi.progii.avicena.nurse.usecase;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.domain.NurseRepository;

public class DeleteNurse {

    private final NurseRepository repository;

    public DeleteNurse(NurseRepository repository) {
        this.repository = repository;
    }

    public void delete(Nurse nurse) {
        repository.delete(nurse);
    }
}
