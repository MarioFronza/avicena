package br.udesc.ceavi.progii.avicena.nurse.domain;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;

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
        if (name == null || name.isBlank()) {
            throw new InvalidNurseDataException("Nurse name must not be blank");
        }
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.formation = formation;
        this.hoursCompleted = hoursCompleted;
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
