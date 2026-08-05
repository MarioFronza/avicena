package br.udesc.ceavi.progii.avicena.receptionist.domain;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;

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
        if (name == null || name.isBlank()) {
            throw new InvalidReceptionistDataException("Receptionist name must not be blank");
        }
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.workHours = workHours;
        this.salary = salary;
        this.overtimeHours = overtimeHours;
        this.laborCardNumber = laborCardNumber;
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
