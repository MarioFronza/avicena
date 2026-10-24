package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

public class RegisterReceptionistMenuListener extends MenuActionListener {

    private final ReceptionistRegistrationFrame receptionistFrame;

    public RegisterReceptionistMenuListener(FrameSistema tela, ReceptionistRegistrationFrame receptionistFrame) {
        super(tela);
        this.receptionistFrame = receptionistFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!receptionistFrame.isVisible()) {
            tela.adicionarFrameInterno(receptionistFrame);
            receptionistFrame.setVisible(true);
        }
    }
}
