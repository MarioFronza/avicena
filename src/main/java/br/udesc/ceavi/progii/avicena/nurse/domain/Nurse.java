package br.udesc.ceavi.progii.avicena.nurse.domain;

import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.Invariants;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;

public class Nurse {

    private final Long id;
    private final String name;
    private final String cpf;
    private final String phone;
    private final Address address;
    private final MaritalStatus maritalStatus;
    private final String formation;
    private final int hoursCompleted;

    public Nurse(
            String name,
            String cpf,
            String phone,
            Address address,
            MaritalStatus maritalStatus,
            String formation,
            int hoursCompleted) {
        this(null, name, cpf, phone, address, maritalStatus, formation, hoursCompleted);
    }

    public Nurse(
            Long id,
            String name,
            String cpf,
            String phone,
            Address address,
            MaritalStatus maritalStatus,
            String formation,
            int hoursCompleted) {
        this.id = id;
        this.name = Invariants.notBlank(name, "Nurse name", InvalidNurseDataException::new);
        this.cpf = Invariants.notBlank(cpf, "Nurse cpf", InvalidNurseDataException::new);
        this.phone = Invariants.notBlank(phone, "Nurse phone", InvalidNurseDataException::new);
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.formation = Invariants.notBlank(formation, "Nurse formation", InvalidNurseDataException::new);
        this.hoursCompleted =
                Invariants.notNegative(hoursCompleted, "Nurse hours completed", InvalidNurseDataException::new);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public String getFormation() {
        return formation;
    }

    public int getHoursCompleted() {
        return hoursCompleted;
    }
}
