package br.udesc.ceavi.progii.avicena.nurse.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.nurse.domain.InvalidNurseDataException;
import br.udesc.ceavi.progii.avicena.nurse.domain.Nurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.DeleteNurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.ListNurses;
import br.udesc.ceavi.progii.avicena.nurse.usecase.RegisterNurse;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import java.util.Optional;
import java.util.function.Function;

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
    private final ListNurses listNurses;

    public NurseController(RegisterNurse registerNurse, DeleteNurse deleteNurse, ListNurses listNurses) {
        this.registerNurse = registerNurse;
        this.deleteNurse = deleteNurse;
        this.listNurses = listNurses;
    }

    public Nurse register(Form form) {
        return registerNurse.register(new Nurse(
                form.name(),
                form.cpf(),
                form.phone(),
                form.address(),
                form.maritalStatus(),
                form.formation(),
                parseNumber(form.hoursCompleted(), "Hours completed", Integer::parseInt)));
    }

    public void delete(Nurse nurse) {
        deleteNurse.delete(nurse);
    }

    public Optional<Nurse> findByCpf(String cpf) {
        return listNurses.list().stream()
                .filter(nurse -> cpf.equals(nurse.getCpf()))
                .findFirst();
    }

    private static <T> T parseNumber(String text, String field, Function<String, T> parser) {
        try {
            return parser.apply(text);
        } catch (NumberFormatException e) {
            throw new InvalidNurseDataException(field + " must be a number");
        }
    }
}
