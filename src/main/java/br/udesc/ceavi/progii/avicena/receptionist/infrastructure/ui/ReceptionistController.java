package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.receptionist.domain.InvalidReceptionistDataException;
import br.udesc.ceavi.progii.avicena.receptionist.domain.Receptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.DeleteReceptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.ListReceptionists;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.RegisterReceptionist;
import java.util.Optional;
import java.util.function.Function;

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
    private final ListReceptionists listReceptionists;

    public ReceptionistController(
            RegisterReceptionist registerReceptionist,
            DeleteReceptionist deleteReceptionist,
            ListReceptionists listReceptionists) {
        this.registerReceptionist = registerReceptionist;
        this.deleteReceptionist = deleteReceptionist;
        this.listReceptionists = listReceptionists;
    }

    public Receptionist register(Form form) {
        return registerReceptionist.register(new Receptionist(
                form.name(),
                form.cpf(),
                form.phone(),
                form.address(),
                form.maritalStatus(),
                parseNumber(form.workHours(), "Work hours", Integer::parseInt),
                parseNumber(form.salary(), "Salary", Float::parseFloat),
                parseNumber(form.overtimeHours(), "Overtime hours", Integer::parseInt),
                parseNumber(form.laborCardNumber(), "Labor card number", Integer::parseInt)));
    }

    public void delete(Receptionist receptionist) {
        deleteReceptionist.delete(receptionist);
    }

    public Optional<Receptionist> findByCpf(String cpf) {
        return listReceptionists.list().stream()
                .filter(receptionist -> cpf.equals(receptionist.getCpf()))
                .findFirst();
    }

    private static <T> T parseNumber(String text, String field, Function<String, T> parser) {
        try {
            return parser.apply(text);
        } catch (NumberFormatException e) {
            throw new InvalidReceptionistDataException(field + " must be a number");
        }
    }
}
