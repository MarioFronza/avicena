package br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.DeleteDoctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.ListDoctors;
import br.udesc.ceavi.progii.avicena.doctor.usecase.RegisterDoctor;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
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
    private final ListDoctors listDoctors;

    public DoctorController(RegisterDoctor registerDoctor, DeleteDoctor deleteDoctor, ListDoctors listDoctors) {
        this.registerDoctor = registerDoctor;
        this.deleteDoctor = deleteDoctor;
        this.listDoctors = listDoctors;
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
        return listDoctors.list().stream()
                .filter(doctor -> cpf.equals(doctor.getCpf()))
                .findFirst();
    }
}
