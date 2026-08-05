package br.udesc.ceavi.progii.avicena.receptionist.domain;

import java.util.List;

public interface ReceptionistRepository {

    Receptionist save(Receptionist receptionist);

    List<Receptionist> findAll();

    void delete(Receptionist receptionist);
}
