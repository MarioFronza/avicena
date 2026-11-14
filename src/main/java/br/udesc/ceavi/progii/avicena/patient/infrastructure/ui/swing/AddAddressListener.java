package br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.swing;

import br.udesc.ceavi.progii.avicena.view.main.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAddressListener implements ActionListener {

    private final MainFrame mainFrame;
    private final AddressEntryFrame addressFrame;

    public AddAddressListener(MainFrame mainFrame, AddressEntryFrame addressFrame) {
        this.mainFrame = mainFrame;
        this.addressFrame = addressFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.addInternalFrame(addressFrame);
        addressFrame.setVisible(true);
    }
}
