package br.udesc.ceavi.progii.avicena.receptionist.usecase;

import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.domain.ReceptionistRepository;
import java.util.ArrayList;
import java.util.List;

class InMemoryReceptionistRepository implements ReceptionistRepository {

    private final List<Receptionist> receptionists = new ArrayList<>();

    @Override
    public Receptionist save(Receptionist receptionist) {
        receptionists.add(receptionist);
        return receptionist;
    }

    @Override
    public List<Receptionist> findAll() {
        return receptionists;
    }

    @Override
    public void delete(Receptionist receptionist) {
        receptionists.remove(receptionist);
    }
}
