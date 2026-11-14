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
    private final String qualification;
    private final int trainingHours;

    public Nurse(
            String name,
            String cpf,
            String phone,
            Address address,
            MaritalStatus maritalStatus,
            String qualification,
            int trainingHours) {
        this(null, name, cpf, phone, address, maritalStatus, qualification, trainingHours);
    }

    public Nurse(
            Long id,
            String name,
            String cpf,
            String phone,
            Address address,
            MaritalStatus maritalStatus,
            String qualification,
            int trainingHours) {
        this.id = id;
        this.name = Invariants.notBlank(name, "Nurse name", InvalidNurseDataException::new);
        this.cpf = Invariants.notBlank(cpf, "Nurse cpf", InvalidNurseDataException::new);
        this.phone = Invariants.notBlank(phone, "Nurse phone", InvalidNurseDataException::new);
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.qualification = Invariants.notBlank(qualification, "Nurse qualification", InvalidNurseDataException::new);
        this.trainingHours =
                Invariants.notNegative(trainingHours, "Nurse training hours", InvalidNurseDataException::new);
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

    public String getQualification() {
        return qualification;
    }

    public int getTrainingHours() {
        return trainingHours;
    }
}
