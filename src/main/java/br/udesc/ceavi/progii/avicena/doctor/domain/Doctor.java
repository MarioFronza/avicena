package br.udesc.ceavi.progii.avicena.doctor.domain;

import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.Invariants;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;

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
        this.id = id;
        this.name = Invariants.notBlank(name, "Doctor name", InvalidDoctorDataException::new);
        this.cpf = Invariants.notBlank(cpf, "Doctor cpf", InvalidDoctorDataException::new);
        this.phone = Invariants.notBlank(phone, "Doctor phone", InvalidDoctorDataException::new);
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
