package br.udesc.ceavi.progii.avicena.nurse.usecase;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.domain.NurseRepository;

public class RegisterNurse {

    private final NurseRepository repository;

    public RegisterNurse(NurseRepository repository) {
        this.repository = repository;
    }

    public Nurse register(Nurse nurse) {
        return repository.save(nurse);
    }
}
