package br.udesc.ceavi.progii.avicena.patient.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AddressEntryController {

    private static AddressEntryController instance;

    private Address address;
    private final AddressEntryFrame entryFrame;
    private final Consumer<Address> callback;

    public AddressEntryController(AddressEntryFrame entryFrame, Consumer<Address> callback) {
        this.entryFrame = entryFrame;
        this.callback = callback;

        addCrudListeners();
    }

    public static AddressEntryController getInstance(FrameCRUD frame) {
        if (instance == null) {
            instance = new AddressEntryController((AddressEntryFrame) frame, address -> {});
        }
        return instance;
    }

    public static AddressEntryController getInstance() {
        return instance;
    }

    public Address getAddress() {
        return address;
    }

    private void addCrudListeners() {
        JButton button;

        button = entryFrame.getPanelBotoesCRUD().getBtCancelar();
        button.addActionListener(new CancelActionListener());
        button = entryFrame.getPanelBotoesCRUD().getBtNovo();
        button.addActionListener(new NewActionListener());
        button = entryFrame.getPanelBotoesCRUD().getBtExcluir();
        button.addActionListener(new DeleteActionListener());
        button = entryFrame.getPanelBotoesCRUD().getBtGravar();
        button.addActionListener(new SaveActionListener());
    }

    private class CancelActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(
                    entryFrame,
                    "Do you really want to close this window?",
                    "Confirm Close",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                entryFrame.dispose();
            }
        }
    }

    private class NewActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(entryFrame, "New Address");
        }
    }

    private class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            address = null;
            entryFrame.limparCampos();
        }
    }

    private class SaveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            address = new Address(
                    Integer.parseInt(entryFrame.getNumberField().getText()),
                    entryFrame.getComplementField().getText(),
                    entryFrame.getZipCodeField().getText(),
                    entryFrame.getStreetField().getText(),
                    entryFrame.getNeighborhoodField().getText(),
                    entryFrame.getCityField().getText());
            callback.accept(address);
            JOptionPane.showMessageDialog(entryFrame, "Address saved");
            entryFrame.dispose();
            entryFrame.limparCampos();
        }
    }
}
