package br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

public class RegisterDoctorMenuListener extends MenuActionListener {

    private final DoctorRegistrationFrame doctorFrame;

    public RegisterDoctorMenuListener(FrameSistema tela, DoctorRegistrationFrame doctorFrame) {
        super(tela);
        this.doctorFrame = doctorFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!doctorFrame.isVisible()) {
            tela.adicionarFrameInterno(doctorFrame);
            doctorFrame.setVisible(true);
        }
    }
}
