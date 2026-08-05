package br.udesc.ceavi.progii.avicena.patient.infrastructure.ui;

import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.view.frames.FrameCRUD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AddressEntryController {

    private static AddressEntryController instance;

    private Address address;
    private final FrameCRUD frame;

    public AddressEntryController(FrameCRUD frame) {
        this.frame = frame;

        addCrudListeners();
    }

    public static AddressEntryController getInstance(FrameCRUD frame) {
        if (instance == null) {
            instance = new AddressEntryController(frame);
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

        button = frame.getPanelBotoesCRUD().getBtCancelar();
        button.addActionListener(new CancelActionListener());
        button = frame.getPanelBotoesCRUD().getBtNovo();
        button.addActionListener(new NewActionListener());
        button = frame.getPanelBotoesCRUD().getBtExcluir();
        button.addActionListener(new DeleteActionListener());
        button = frame.getPanelBotoesCRUD().getBtGravar();
        button.addActionListener(new SaveActionListener());
    }

    private class CancelActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(
                    frame,
                    "Do you really want to close this window?",
                    "Confirm Close",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                frame.dispose();
            }
        }
    }

    private class NewActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, "New Address");
        }
    }

    private class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            address = null;
            frame.limparCampos();
        }
    }

    private class SaveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AddressEntryFrame entryFrame = AddressEntryFrame.getInstance();
            address = new Address(
                    Integer.parseInt(entryFrame.getNumberField().getText()),
                    entryFrame.getComplementField().getText(),
                    entryFrame.getZipCodeField().getText(),
                    entryFrame.getStreetField().getText(),
                    entryFrame.getNeighborhoodField().getText(),
                    entryFrame.getCityField().getText());
            JOptionPane.showMessageDialog(entryFrame, "Address saved");
            frame.dispose();
            frame.limparCampos();
        }
    }
}
