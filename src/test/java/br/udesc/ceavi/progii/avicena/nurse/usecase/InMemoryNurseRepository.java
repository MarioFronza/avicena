package br.udesc.ceavi.progii.avicena.nurse.usecase;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.domain.NurseRepository;
import java.util.ArrayList;
import java.util.List;

class InMemoryNurseRepository implements NurseRepository {

    private final List<Nurse> nurses = new ArrayList<>();

    @Override
    public Nurse save(Nurse nurse) {
        nurses.add(nurse);
        return nurse;
    }

    @Override
    public List<Nurse> findAll() {
        return nurses;
    }

    @Override
    public void delete(Nurse nurse) {
        nurses.remove(nurse);
    }
}
