package br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import br.udesc.ceavi.progii.avicena.view.principal.MenuPrincipal.Section;
import java.awt.event.ActionEvent;

public class RegisterPatientMenuListener extends MenuActionListener {

    private final PatientRegistrationFrame patientFrame;

    public RegisterPatientMenuListener(FrameSistema tela, PatientRegistrationFrame patientFrame) {
        super(
                tela,
                Section.REGISTRATION,
                "Cadastro Paciente",
                "vcard_add.png",
                "Cadastra o paciente e busca por seu CPF");
        this.patientFrame = patientFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!patientFrame.isVisible()) {
            tela.adicionarFrameInterno(patientFrame);
            patientFrame.setVisible(true);
        }
    }
}
