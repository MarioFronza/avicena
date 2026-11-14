package br.udesc.ceavi.progii.avicena.nurse.domain;

import java.util.List;
import java.util.Optional;

public interface NurseRepository {

    Nurse save(Nurse nurse);

    List<Nurse> findAll();

    Optional<Nurse> findById(Long id);

    Optional<Nurse> findByCpf(String cpf);

    void delete(Nurse nurse);
}
