package br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import br.udesc.ceavi.progii.avicena.view.principal.MenuPrincipal.Section;
import java.awt.event.ActionEvent;

public class RegisterDiagnosisMenuListener extends MenuActionListener {

    private final DiagnosisRegistrationFrame diagnosisFrame;

    public RegisterDiagnosisMenuListener(FrameSistema tela, DiagnosisRegistrationFrame diagnosisFrame) {
        super(tela, Section.PATIENT, "Diagnóstico", "report_edit.png", "Cadastro dos diagnósticos primário e final");
        this.diagnosisFrame = diagnosisFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        diagnosisFrame.reloadAppointments();

        if (!diagnosisFrame.isVisible()) {
            tela.adicionarFrameInterno(diagnosisFrame);
            diagnosisFrame.setVisible(true);
        }
    }
}
