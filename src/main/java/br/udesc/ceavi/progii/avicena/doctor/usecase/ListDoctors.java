package br.udesc.ceavi.progii.avicena.doctor.usecase;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.DoctorRepository;
import java.util.List;

public class ListDoctors {

    private final DoctorRepository repository;

    public ListDoctors(DoctorRepository repository) {
        this.repository = repository;
    }

    public List<Doctor> list() {
        return repository.findAll();
    }
}
