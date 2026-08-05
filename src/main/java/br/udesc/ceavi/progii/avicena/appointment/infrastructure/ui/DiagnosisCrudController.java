package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.appointment.domain.Diagnosis;
import br.udesc.ceavi.progii.avicena.appointment.domain.InvalidDiagnosisDataException;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.DiagnosisJpaRepository;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteDiagnosis;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterDiagnosis;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class DiagnosisCrudController {

    private static DiagnosisCrudController instance;

    private Diagnosis diagnosis;
    private final FrameCRUD frame;
    private final RegisterDiagnosis registerDiagnosis;
    private final DeleteDiagnosis deleteDiagnosis;

    public DiagnosisCrudController(FrameCRUD frame) {
        this.frame = frame;
        DiagnosisJpaRepository repository = new DiagnosisJpaRepository();
        this.registerDiagnosis = new RegisterDiagnosis(repository);
        this.deleteDiagnosis = new DeleteDiagnosis(repository);

        addCrudListeners();
    }

    public static DiagnosisCrudController getInstance(FrameCRUD frame) {
        if (instance == null) {
            instance = new DiagnosisCrudController(frame);
        }
        return instance;
    }

    public static DiagnosisCrudController getInstance() {
        return instance;
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
                deleteDiagnosis.delete(diagnosis);
                frame.limparCampos();
            }
        }
    }

    private class RegisterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DiagnosisRegistrationFrame registrationFrame = DiagnosisRegistrationFrame.getInstance();
            Long appointmentId = registrationFrame.getSelectedAppointmentId();
            float bloodPressure =
                    Float.parseFloat(registrationFrame.getBloodPressureField().getText());
            int temperature =
                    Integer.parseInt(registrationFrame.getTemperatureField().getText());
            int height = Integer.parseInt(registrationFrame.getHeightField().getText());
            int weight = Integer.parseInt(registrationFrame.getWeightField().getText());
            String disease = registrationFrame.getDiseaseField().getText();
            String medications = registrationFrame.getMedicationsField().getText();

            try {
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
                JOptionPane.showMessageDialog(frame, "Diagnosis saved");
            } catch (InvalidDiagnosisDataException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }

            frame.limparCampos();
        }
    }
}
