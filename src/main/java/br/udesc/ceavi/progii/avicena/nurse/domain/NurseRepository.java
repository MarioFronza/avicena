package br.udesc.ceavi.progii.avicena.nurse.domain;

import java.util.List;

public interface NurseRepository {

    Nurse save(Nurse nurse);

    List<Nurse> findAll();

    void delete(Nurse nurse);
}
