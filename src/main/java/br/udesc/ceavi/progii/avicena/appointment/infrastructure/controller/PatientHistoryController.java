package br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentEntity;
import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import jakarta.persistence.EntityManager;
import java.util.List;

public class PatientHistoryController {

    public List<AppointmentEntity> findByPatientCpf(String cpf) {
        return filterByPatientCpf(findAll(), cpf);
    }

    static List<AppointmentEntity> filterByPatientCpf(List<AppointmentEntity> appointments, String cpf) {
        return appointments.stream()
                .filter(appointment -> appointment.getPatient() != null
                        && cpf.equals(appointment.getPatient().getCpf()))
                .toList();
    }

    private static List<AppointmentEntity> findAll() {
        EntityManager entityManager = PersistenceConfig.entityManagerFactory().createEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT a FROM AppointmentEntity a", AppointmentEntity.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }
}
