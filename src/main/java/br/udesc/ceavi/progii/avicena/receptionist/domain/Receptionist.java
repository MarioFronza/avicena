package br.udesc.ceavi.progii.avicena.receptionist.domain;

import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.Invariants;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;

public class Receptionist {

    private final Long id;
    private final String name;
    private final String cpf;
    private final String phone;
    private final Address address;
    private final MaritalStatus maritalStatus;
    private final int workHours;
    private final float salary;
    private final int overtimeHours;
    private final int laborCardNumber;

    public Receptionist(
            String name,
            String cpf,
            String phone,
            Address address,
            MaritalStatus maritalStatus,
            int workHours,
            float salary,
            int overtimeHours,
            int laborCardNumber) {
        this(null, name, cpf, phone, address, maritalStatus, workHours, salary, overtimeHours, laborCardNumber);
    }

    public Receptionist(
            Long id,
            String name,
            String cpf,
            String phone,
            Address address,
            MaritalStatus maritalStatus,
            int workHours,
            float salary,
            int overtimeHours,
            int laborCardNumber) {
        this.id = id;
        this.name = Invariants.notBlank(name, "Receptionist name", InvalidReceptionistDataException::new);
        this.cpf = Invariants.notBlank(cpf, "Receptionist cpf", InvalidReceptionistDataException::new);
        this.phone = Invariants.notBlank(phone, "Receptionist phone", InvalidReceptionistDataException::new);
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.workHours =
                Invariants.notNegative(workHours, "Receptionist work hours", InvalidReceptionistDataException::new);
        this.salary = Invariants.notNegative(salary, "Receptionist salary", InvalidReceptionistDataException::new);
        this.overtimeHours = Invariants.notNegative(
                overtimeHours, "Receptionist overtime hours", InvalidReceptionistDataException::new);
        this.laborCardNumber = Invariants.notNegative(
                laborCardNumber, "Receptionist labor card number", InvalidReceptionistDataException::new);
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

    public int getWorkHours() {
        return workHours;
    }

    public float getSalary() {
        return salary;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public int getLaborCardNumber() {
        return laborCardNumber;
    }
}
