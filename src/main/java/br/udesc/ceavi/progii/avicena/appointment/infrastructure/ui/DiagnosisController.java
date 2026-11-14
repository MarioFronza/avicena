package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteDiagnosis;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterDiagnosis;

public class DiagnosisController {

    public record Form(
            Long appointmentId,
            String bloodPressure,
            String temperature,
            String height,
            String weight,
            String disease,
            String medications) {}

    private final RegisterDiagnosis registerDiagnosis;
    private final DeleteDiagnosis deleteDiagnosis;

    public DiagnosisController(RegisterDiagnosis registerDiagnosis, DeleteDiagnosis deleteDiagnosis) {
        this.registerDiagnosis = registerDiagnosis;
        this.deleteDiagnosis = deleteDiagnosis;
    }

    public Diagnosis register(Form form) {
        return registerDiagnosis.register(new Diagnosis(
                form.appointmentId(),
                Float.parseFloat(form.bloodPressure()),
                Integer.parseInt(form.temperature()),
                Integer.parseInt(form.height()),
                Integer.parseInt(form.weight()),
                "Padrão",
                form.disease(),
                form.medications(),
                "Exame paddão",
                "Padrão"));
    }

    public void delete(Diagnosis diagnosis) {
        deleteDiagnosis.delete(diagnosis);
    }
}
