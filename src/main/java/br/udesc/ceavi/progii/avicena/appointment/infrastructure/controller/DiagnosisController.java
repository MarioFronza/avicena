package br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.InvalidDiagnosisDataException;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteDiagnosis;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterDiagnosis;
import br.udesc.ceavi.progii.avicena.shared.infrastructure.controller.FormNumbers;

public class DiagnosisController {

    public record Form(
            Long appointmentId,
            String bloodPressure,
            String temperature,
            String height,
            String weight,
            String notes,
            String disease,
            String medications,
            String examNotes,
            String finalDescription) {}

    private final RegisterDiagnosis registerDiagnosis;
    private final DeleteDiagnosis deleteDiagnosis;

    public DiagnosisController(RegisterDiagnosis registerDiagnosis, DeleteDiagnosis deleteDiagnosis) {
        this.registerDiagnosis = registerDiagnosis;
        this.deleteDiagnosis = deleteDiagnosis;
    }

    public Diagnosis register(Form form) {
        return registerDiagnosis.register(new Diagnosis(
                form.appointmentId(),
                FormNumbers.parse(
                        form.bloodPressure(), "Blood pressure", Float::parseFloat, InvalidDiagnosisDataException::new),
                FormNumbers.parse(
                        form.temperature(), "Temperature", Integer::parseInt, InvalidDiagnosisDataException::new),
                FormNumbers.parse(form.height(), "Height", Integer::parseInt, InvalidDiagnosisDataException::new),
                FormNumbers.parse(form.weight(), "Weight", Integer::parseInt, InvalidDiagnosisDataException::new),
                form.notes(),
                form.disease(),
                form.medications(),
                form.examNotes(),
                form.finalDescription()));
    }

    public void delete(Diagnosis diagnosis) {
        deleteDiagnosis.delete(diagnosis);
    }
}
