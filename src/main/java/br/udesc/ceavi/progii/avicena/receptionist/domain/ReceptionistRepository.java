package br.udesc.ceavi.progii.avicena.receptionist.domain;

import java.util.List;
import java.util.Optional;

public interface ReceptionistRepository {

    Receptionist save(Receptionist receptionist);

    List<Receptionist> findAll();

    Optional<Receptionist> findById(Long id);

    Optional<Receptionist> findByCpf(String cpf);

    void delete(Receptionist receptionist);
}
