package br.udesc.ceavi.progii.avicena.doctor.usecase;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.DoctorRepository;
import java.util.ArrayList;
import java.util.List;

class InMemoryDoctorRepository implements DoctorRepository {

    private final List<Doctor> doctors = new ArrayList<>();

    @Override
    public Doctor save(Doctor doctor) {
        doctors.add(doctor);
        return doctor;
    }

    @Override
    public List<Doctor> findAll() {
        return doctors;
    }

    @Override
    public void delete(Doctor doctor) {
        doctors.remove(doctor);
    }
}
