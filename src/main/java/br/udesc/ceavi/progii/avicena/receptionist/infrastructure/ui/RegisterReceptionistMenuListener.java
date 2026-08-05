package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

public class RegisterReceptionistMenuListener extends MenuActionListener {

    public RegisterReceptionistMenuListener(FrameSistema tela) {
        super(tela);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame = ReceptionistRegistrationFrame.getInstance();

        ReceptionistCrudController.getInstance(frame);

        if (!frame.isVisible()) {
            tela.adicionarFrameInterno(frame);
            frame.setVisible(true);
        }
    }
}
