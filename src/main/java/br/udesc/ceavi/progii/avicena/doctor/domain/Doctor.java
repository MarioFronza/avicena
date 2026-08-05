package br.udesc.ceavi.progii.avicena.doctor.domain;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;

public class Doctor {

    private final Long id;
    private final String name;
    private final String cpf;
    private final String phone;
    private final Address address;
    private final MaritalStatus maritalStatus;
    private final String crm;
    private final String specialty;

    public Doctor(
            String name,
            String cpf,
            String phone,
            Address address,
            MaritalStatus maritalStatus,
            String crm,
            String specialty) {
        this(null, name, cpf, phone, address, maritalStatus, crm, specialty);
    }

    public Doctor(
            Long id,
            String name,
            String cpf,
            String phone,
            Address address,
            MaritalStatus maritalStatus,
            String crm,
            String specialty) {
        if (name == null || name.isBlank()) {
            throw new InvalidDoctorDataException("Doctor name must not be blank");
        }
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.crm = crm;
        this.specialty = specialty;
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

    public String getCrm() {
        return crm;
    }

    public String getSpecialty() {
        return specialty;
    }
}
