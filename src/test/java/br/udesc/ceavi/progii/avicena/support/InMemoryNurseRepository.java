package br.udesc.ceavi.progii.avicena.support;

import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.domain.NurseRepository;
import java.util.Optional;

public class InMemoryNurseRepository extends InMemoryRepository<Nurse> implements NurseRepository {

    @Override
    protected Long idOf(Nurse nurse) {
        return nurse.getId();
    }

    @Override
    protected Nurse withId(Nurse nurse, Long id) {
        return new Nurse(
                id,
                nurse.getName(),
                nurse.getCpf(),
                nurse.getPhone(),
                nurse.getAddress(),
                nurse.getMaritalStatus(),
                nurse.getQualification(),
                nurse.getTrainingHours());
    }

    @Override
    public Optional<Nurse> findByCpf(String cpf) {
        return findAll().stream().filter(nurse -> nurse.getCpf().equals(cpf)).findFirst();
    }
}
