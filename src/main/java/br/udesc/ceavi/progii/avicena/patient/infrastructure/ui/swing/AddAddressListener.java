package br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAddressListener implements ActionListener {

    private final FrameSistema tela;
    private final AddressEntryFrame addressFrame;

    public AddAddressListener(FrameSistema tela, AddressEntryFrame addressFrame) {
        this.tela = tela;
        this.addressFrame = addressFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tela.adicionarFrameInterno(addressFrame);
        addressFrame.setVisible(true);
    }
}
