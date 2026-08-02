package br.udesc.ceavi.progii.avicena.patient.usecase;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.domain.PatientRepository;
import java.util.ArrayList;
import java.util.List;

class InMemoryPatientRepository implements PatientRepository {

    private final List<Patient> patients = new ArrayList<>();

    @Override
    public Patient save(Patient patient) {
        patients.add(patient);
        return patient;
    }

    @Override
    public List<Patient> findAll() {
        return patients;
    }

    @Override
    public void delete(Patient patient) {
        patients.remove(patient);
    }
}
