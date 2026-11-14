package br.udesc.ceavi.progii.avicena.support;

import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.domain.ReceptionistRepository;
import java.util.Optional;

public class InMemoryReceptionistRepository extends InMemoryRepository<Receptionist> implements ReceptionistRepository {

    @Override
    protected Long idOf(Receptionist receptionist) {
        return receptionist.getId();
    }

    @Override
    protected Receptionist withId(Receptionist receptionist, Long id) {
        return new Receptionist(
                id,
                receptionist.getName(),
                receptionist.getCpf(),
                receptionist.getPhone(),
                receptionist.getAddress(),
                receptionist.getMaritalStatus(),
                receptionist.getWorkHours(),
                receptionist.getSalary(),
                receptionist.getOvertimeHours(),
                receptionist.getLaborCardNumber());
    }

    @Override
    public Optional<Receptionist> findByCpf(String cpf) {
        return findAll().stream()
                .filter(receptionist -> receptionist.getCpf().equals(cpf))
                .findFirst();
    }
}
