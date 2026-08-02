package br.udesc.ceavi.progii.avicena.patient.domain;

public class Patient {

    private final String name;
    private final String cpf;
    private final String phone;
    private final Address address;
    private final MaritalStatus maritalStatus;

    public Patient(String name, String cpf, String phone, Address address, MaritalStatus maritalStatus) {
        if (name == null || name.isBlank()) {
            throw new InvalidPatientDataException("Patient name must not be blank");
        }
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.address = address;
        this.maritalStatus = maritalStatus;
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
