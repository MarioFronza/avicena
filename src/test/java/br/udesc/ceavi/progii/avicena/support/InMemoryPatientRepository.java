package br.udesc.ceavi.progii.avicena.support;

import br.udesc.ceavi.progii.avicena.patient.domain.Patient;
import br.udesc.ceavi.progii.avicena.patient.domain.PatientRepository;
import java.util.Optional;

public class InMemoryPatientRepository extends InMemoryRepository<Patient> implements PatientRepository {

    @Override
    protected Long idOf(Patient patient) {
        return patient.getId();
    }

    @Override
    protected Patient withId(Patient patient, Long id) {
        return new Patient(
                id,
                patient.getName(),
                patient.getCpf(),
                patient.getPhone(),
                patient.getAddress(),
                patient.getMaritalStatus());
    }

    @Override
    public Optional<Patient> findByCpf(String cpf) {
        return findAll().stream()
                .filter(patient -> patient.getCpf().equals(cpf))
                .findFirst();
    }
}
