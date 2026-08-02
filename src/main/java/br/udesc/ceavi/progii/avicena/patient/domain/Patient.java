package br.udesc.ceavi.progii.avicena.patient.domain;

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
        if (name == null || name.isBlank()) {
            throw new InvalidPatientDataException("Patient name must not be blank");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new InvalidPatientDataException("Patient cpf must not be blank");
        }
        if (phone == null || phone.isBlank()) {
            throw new InvalidPatientDataException("Patient phone must not be blank");
        }
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
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
