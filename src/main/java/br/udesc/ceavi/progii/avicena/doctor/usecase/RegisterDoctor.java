package br.udesc.ceavi.progii.avicena.doctor.usecase;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.DoctorRepository;

public class RegisterDoctor {

    private final DoctorRepository repository;

    public RegisterDoctor(DoctorRepository repository) {
        this.repository = repository;
    }

    public Doctor register(Doctor doctor) {
        return repository.save(doctor);
    }
}
