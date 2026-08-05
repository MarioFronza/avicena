package br.udesc.ceavi.progii.avicena.doctor.usecase;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.DoctorRepository;

public class DeleteDoctor {

    private final DoctorRepository repository;

    public DeleteDoctor(DoctorRepository repository) {
        this.repository = repository;
    }

    public void delete(Doctor doctor) {
        repository.delete(doctor);
    }
}
