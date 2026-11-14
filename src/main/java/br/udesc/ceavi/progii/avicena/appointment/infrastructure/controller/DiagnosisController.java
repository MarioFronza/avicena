package br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.InvalidDiagnosisDataException;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteDiagnosis;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterDiagnosis;
import java.util.function.Function;

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
                parseNumber(form.bloodPressure(), "Blood pressure", Float::parseFloat),
                parseNumber(form.temperature(), "Temperature", Integer::parseInt),
                parseNumber(form.height(), "Height", Integer::parseInt),
                parseNumber(form.weight(), "Weight", Integer::parseInt),
                "Default",
                form.disease(),
                form.medications(),
                "Default exam",
                "Default"));
    }

    public void delete(Diagnosis diagnosis) {
        deleteDiagnosis.delete(diagnosis);
    }

    private static <T> T parseNumber(String text, String field, Function<String, T> parser) {
        try {
            return parser.apply(text);
        } catch (NumberFormatException e) {
            throw new InvalidDiagnosisDataException(field + " must be a number");
        }
    }
}
