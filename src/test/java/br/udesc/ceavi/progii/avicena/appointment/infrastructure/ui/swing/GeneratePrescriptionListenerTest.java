package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller.AppointmentController;
import java.util.List;
import org.junit.jupiter.api.Test;

class GeneratePrescriptionListenerTest {

    private static final AppointmentController.Prescription PRESCRIPTION = new AppointmentController.Prescription(
            "Doctor Test", "Doctor Street", "Downtown", "CRM-1", "Patient Test", "Patient Street", "Center");

    @Test
    void startsWithTheTitle() {
        assertEquals("Medical Prescription - AVICENA", lines().get(0));
    }

    @Test
    void printsTheDoctorNameAddressAndCrm() {
        assertTrue(lines().containsAll(List.of("Dr. Doctor Test", "Street: Doctor Street , Downtown", "CRM CRM-1")));
    }

    @Test
    void printsThePatientNameAndAddress() {
        assertTrue(lines().containsAll(List.of("Patient: Patient Test", "Street: Patient Street , Center")));
    }

    @Test
    void endsWithTheSignatureLine() {
        assertEquals("_______________________________________________________", lines().get(lines().size() - 1));
    }

    private static List<String> lines() {
        return GeneratePrescriptionListener.buildPrescriptionLines(PRESCRIPTION);
    }
}
