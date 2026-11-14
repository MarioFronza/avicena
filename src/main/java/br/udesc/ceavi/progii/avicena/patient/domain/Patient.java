package br.udesc.ceavi.progii.avicena.patient.domain;

import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.Invariants;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;

public class Patient {

    private final Long id;
    private final String name;
    private final String cpf;
    private final String phone;
    private final Address address;
    private final MaritalStatus maritalStatus;

    public Patient(String name, String cpf, String phone, Address address, MaritalStatus maritalStatus) {
        this(null, name, cpf, phone, address, maritalStatus);
    }

    public Patient(Long id, String name, String cpf, String phone, Address address, MaritalStatus maritalStatus) {
        this.id = id;
        this.name = Invariants.notBlank(name, "Patient name", InvalidPatientDataException::new);
        this.cpf = Invariants.notBlank(cpf, "Patient cpf", InvalidPatientDataException::new);
        this.phone = Invariants.notBlank(phone, "Patient phone", InvalidPatientDataException::new);
        this.address = address;
        this.maritalStatus = maritalStatus;
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
}
