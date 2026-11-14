package br.udesc.ceavi.progii.avicena.patient.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.usecase.DeletePatient;
import br.udesc.ceavi.progii.avicena.patient.usecase.FindPatientByCpf;
import br.udesc.ceavi.progii.avicena.patient.usecase.RegisterPatient;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import java.util.Optional;

public class PatientController {

    public record Form(Long id, String name, String cpf, String phone, Address address, MaritalStatus maritalStatus) {

        public Form(String name, String cpf, String phone, Address address, MaritalStatus maritalStatus) {
            this(null, name, cpf, phone, address, maritalStatus);
        }

        public Form withId(Long id) {
            return new Form(id, name, cpf, phone, address, maritalStatus);
        }
    }

    private final RegisterPatient registerPatient;
    private final DeletePatient deletePatient;
    private final FindPatientByCpf findPatientByCpf;

    public PatientController(
            RegisterPatient registerPatient, DeletePatient deletePatient, FindPatientByCpf findPatientByCpf) {
        this.registerPatient = registerPatient;
        this.deletePatient = deletePatient;
        this.findPatientByCpf = findPatientByCpf;
    }

    public Patient register(Form form) {
        return registerPatient.register(
                new Patient(form.id(), form.name(), form.cpf(), form.phone(), form.address(), form.maritalStatus()));
    }

    public void delete(Patient patient) {
        deletePatient.delete(patient);
    }

    public Optional<Patient> findByCpf(String cpf) {
        return findPatientByCpf.find(cpf);
    }
}
