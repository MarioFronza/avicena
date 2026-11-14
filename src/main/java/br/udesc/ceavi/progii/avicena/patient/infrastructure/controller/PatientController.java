package br.udesc.ceavi.progii.avicena.patient.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.usecase.DeletePatient;
import br.udesc.ceavi.progii.avicena.patient.usecase.ListPatients;
import br.udesc.ceavi.progii.avicena.patient.usecase.RegisterPatient;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import java.util.Optional;

public class PatientController {

    public record Form(String name, String cpf, String phone, Address address, MaritalStatus maritalStatus) {}

    private final RegisterPatient registerPatient;
    private final DeletePatient deletePatient;
    private final ListPatients listPatients;

    public PatientController(RegisterPatient registerPatient, DeletePatient deletePatient, ListPatients listPatients) {
        this.registerPatient = registerPatient;
        this.deletePatient = deletePatient;
        this.listPatients = listPatients;
    }

    public Patient register(Form form) {
        return registerPatient.register(
                new Patient(form.name(), form.cpf(), form.phone(), form.address(), form.maritalStatus()));
    }

    public void delete(Patient patient) {
        deletePatient.delete(patient);
    }

    public Optional<Patient> findByCpf(String cpf) {
        return listPatients.list().stream()
                .filter(patient -> cpf.equals(patient.getCpf()))
                .findFirst();
    }
}
