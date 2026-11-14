package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.receptionist.domain.InvalidReceptionistDataException;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.DeleteReceptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.FindReceptionistByCpf;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.RegisterReceptionist;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.shared.infrastructure.controller.FormNumbers;
import java.util.Optional;

public class ReceptionistController {

    public record Form(
            String name,
            String cpf,
            String phone,
            Address address,
            MaritalStatus maritalStatus,
            String workHours,
            String salary,
            String overtimeHours,
            String laborCardNumber) {}

    private final RegisterReceptionist registerReceptionist;
    private final DeleteReceptionist deleteReceptionist;
    private final FindReceptionistByCpf findReceptionistByCpf;

    public ReceptionistController(
            RegisterReceptionist registerReceptionist,
            DeleteReceptionist deleteReceptionist,
            FindReceptionistByCpf findReceptionistByCpf) {
        this.registerReceptionist = registerReceptionist;
        this.deleteReceptionist = deleteReceptionist;
        this.findReceptionistByCpf = findReceptionistByCpf;
    }

    public Receptionist register(Form form) {
        return registerReceptionist.register(new Receptionist(
                form.name(),
                form.cpf(),
                form.phone(),
                form.address(),
                form.maritalStatus(),
                FormNumbers.parse(
                        form.workHours(), "Work hours", Integer::parseInt, InvalidReceptionistDataException::new),
                FormNumbers.parse(form.salary(), "Salary", Float::parseFloat, InvalidReceptionistDataException::new),
                FormNumbers.parse(
                        form.overtimeHours(),
                        "Overtime hours",
                        Integer::parseInt,
                        InvalidReceptionistDataException::new),
                FormNumbers.parse(
                        form.laborCardNumber(),
                        "Labor card number",
                        Integer::parseInt,
                        InvalidReceptionistDataException::new)));
    }

    public void delete(Receptionist receptionist) {
        deleteReceptionist.delete(receptionist);
    }

    public Optional<Receptionist> findByCpf(String cpf) {
        return findReceptionistByCpf.find(cpf);
    }
}
