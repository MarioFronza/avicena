package br.udesc.ceavi.progii.avicena.nurse.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.nurse.domain.InvalidNurseDataException;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.DeleteNurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.FindNurseByCpf;
import br.udesc.ceavi.progii.avicena.nurse.usecase.RegisterNurse;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.shared.infrastructure.controller.FormNumbers;
import java.util.Optional;

public class NurseController {

    public record Form(
            String name,
            String cpf,
            String phone,
            Address address,
            MaritalStatus maritalStatus,
            String formation,
            String hoursCompleted) {}

    private final RegisterNurse registerNurse;
    private final DeleteNurse deleteNurse;
    private final FindNurseByCpf findNurseByCpf;

    public NurseController(RegisterNurse registerNurse, DeleteNurse deleteNurse, FindNurseByCpf findNurseByCpf) {
        this.registerNurse = registerNurse;
        this.deleteNurse = deleteNurse;
        this.findNurseByCpf = findNurseByCpf;
    }

    public Nurse register(Form form) {
        return registerNurse.register(new Nurse(
                form.name(),
                form.cpf(),
                form.phone(),
                form.address(),
                form.maritalStatus(),
                form.formation(),
                FormNumbers.parse(
                        form.hoursCompleted(), "Hours completed", Integer::parseInt, InvalidNurseDataException::new)));
    }

    public void delete(Nurse nurse) {
        deleteNurse.delete(nurse);
    }

    public Optional<Nurse> findByCpf(String cpf) {
        return findNurseByCpf.find(cpf);
    }
}
