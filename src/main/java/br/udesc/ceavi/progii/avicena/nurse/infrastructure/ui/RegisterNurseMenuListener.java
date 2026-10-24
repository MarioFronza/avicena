package br.udesc.ceavi.progii.avicena.nurse.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

public class RegisterNurseMenuListener extends MenuActionListener {

    private final NurseRegistrationFrame nurseFrame;

    public RegisterNurseMenuListener(FrameSistema tela, NurseRegistrationFrame nurseFrame) {
        super(tela);
        this.nurseFrame = nurseFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!nurseFrame.isVisible()) {
            tela.adicionarFrameInterno(nurseFrame);
            nurseFrame.setVisible(true);
        }
    }
}
