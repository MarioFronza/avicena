package br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

public class RegisterDoctorMenuListener extends MenuActionListener {

    public RegisterDoctorMenuListener(FrameSistema tela) {
        super(tela);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame = DoctorRegistrationFrame.getInstance();

        DoctorCrudController.getInstance(frame);

        if (!frame.isVisible()) {
            tela.adicionarFrameInterno(frame);
            frame.setVisible(true);
        }
    }
}
