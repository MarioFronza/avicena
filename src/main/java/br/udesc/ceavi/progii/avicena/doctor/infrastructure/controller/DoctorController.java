package br.udesc.ceavi.progii.avicena.doctor.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.DeleteDoctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.FindDoctorByCpf;
import br.udesc.ceavi.progii.avicena.doctor.usecase.RegisterDoctor;
import br.udesc.ceavi.progii.avicena.shared.domain.Address;
import br.udesc.ceavi.progii.avicena.shared.domain.MaritalStatus;
import java.util.Optional;

public class DoctorController {

    public record Form(
            String name,
            String cpf,
            String phone,
            Address address,
            MaritalStatus maritalStatus,
            String crm,
            String specialty) {}

    private final RegisterDoctor registerDoctor;
    private final DeleteDoctor deleteDoctor;
    private final FindDoctorByCpf findDoctorByCpf;

    public DoctorController(RegisterDoctor registerDoctor, DeleteDoctor deleteDoctor, FindDoctorByCpf findDoctorByCpf) {
        this.registerDoctor = registerDoctor;
        this.deleteDoctor = deleteDoctor;
        this.findDoctorByCpf = findDoctorByCpf;
    }

    public Doctor register(Form form) {
        return registerDoctor.register(new Doctor(
                form.name(),
                form.cpf(),
                form.phone(),
                form.address(),
                form.maritalStatus(),
                form.crm(),
                form.specialty()));
    }

    public void delete(Doctor doctor) {
        deleteDoctor.delete(doctor);
    }

    public Optional<Doctor> findByCpf(String cpf) {
        return findDoctorByCpf.find(cpf);
    }
}
