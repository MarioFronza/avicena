package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.InvalidDiagnosisDataException;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteDiagnosis;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterDiagnosis;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class DiagnosisCrudController {

    private Diagnosis diagnosis;
    private final DiagnosisRegistrationFrame frame;
    private final RegisterDiagnosis registerDiagnosis;
    private final DeleteDiagnosis deleteDiagnosis;

    public DiagnosisCrudController(
            DiagnosisRegistrationFrame frame, RegisterDiagnosis registerDiagnosis, DeleteDiagnosis deleteDiagnosis) {
        this.frame = frame;
        this.registerDiagnosis = registerDiagnosis;
        this.deleteDiagnosis = deleteDiagnosis;
        addCrudListeners();
    }

    public void delete() {
        deleteDiagnosis.delete(diagnosis);
    }

    public void register() {
        Long appointmentId = frame.getSelectedAppointmentId();
        float bloodPressure = Float.parseFloat(frame.getBloodPressureField().getText());
        int temperature = Integer.parseInt(frame.getTemperatureField().getText());
        int height = Integer.parseInt(frame.getHeightField().getText());
        int weight = Integer.parseInt(frame.getWeightField().getText());
        String disease = frame.getDiseaseField().getText();
        String medications = frame.getMedicationsField().getText();
        diagnosis = registerDiagnosis.register(new Diagnosis(
                appointmentId,
                bloodPressure,
                temperature,
                height,
                weight,
                "Padrão",
                disease,
                medications,
                "Exame paddão",
                "Padrão"));
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    private void addCrudListeners() {
        JButton button;

        button = frame.getPanelBotoesCRUD().getBtCancelar();
        button.addActionListener(new CancelActionListener());
        button = frame.getPanelBotoesCRUD().getBtNovo();
        button.addActionListener(new RegisterActionListener());
        button = frame.getPanelBotoesCRUD().getBtExcluir();
        button.addActionListener(new DeleteActionListener());
        button = frame.getPanelBotoesCRUD().getBtGravar();
        button.addActionListener(new RegisterActionListener());
    }

    private class CancelActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(
                    frame,
                    "Do you really want to close this window?",
                    "Confirm Close",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                frame.dispose();
                frame.limparCampos();
            }
        }
    }

    private class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(
                    frame,
                    "Do you want to delete this Diagnosis?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                delete();
                frame.limparCampos();
            }
        }
    }

    private class RegisterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                register();
                JOptionPane.showMessageDialog(frame, "Diagnosis saved");
            } catch (InvalidDiagnosisDataException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }

            frame.limparCampos();
        }
    }
}
