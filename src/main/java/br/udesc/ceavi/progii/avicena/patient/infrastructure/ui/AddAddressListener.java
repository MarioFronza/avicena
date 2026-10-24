package br.udesc.ceavi.progii.avicena.patient.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuActionListener;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;

public class AddAddressListener extends MenuActionListener {

    public AddAddressListener(FrameSistema tela) {
        super(tela);
    }

    public AddAddressListener(FrameSistema tela, AddressEntryFrame addressFrame) {
        super(tela);
        this.frame = addressFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (frame == null) {
            frame = AddressEntryFrame.getInstance();
            AddressEntryController.getInstance(frame);
        }
        tela.adicionarFrameInterno(frame);
        frame.setVisible(true);
    }
}
