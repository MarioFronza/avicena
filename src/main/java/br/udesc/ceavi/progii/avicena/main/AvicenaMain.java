/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.main;

import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorJpaRepository;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui.DoctorCrudController;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui.DoctorRegistrationFrame;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui.DoctorSearchController;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui.RegisterDoctorMenuListener;
import br.udesc.ceavi.progii.avicena.doctor.usecase.DeleteDoctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.ListDoctors;
import br.udesc.ceavi.progii.avicena.doctor.usecase.RegisterDoctor;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.AddAddressListener;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.AddressEntryController;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.AddressEntryFrame;
import br.udesc.ceavi.progii.avicena.view.principal.FrameSistema;
import javax.swing.JOptionPane;

/**
 * Classe principal da aplicação
 * @author Adroan, Mário, Vini, Raphael
 * @since 10/04/2018
 * @version 1.0
 */
public class AvicenaMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            PersistenceConfig.initialize();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(
                    null, "Could not connect to the database: " + e.getMessage(), "Avicena", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        FrameSistema frameSistema = new FrameSistema();

        DoctorJpaRepository doctorRepository = new DoctorJpaRepository();
        DoctorRegistrationFrame doctorFrame = new DoctorRegistrationFrame();
        DoctorCrudController doctorController = new DoctorCrudController(
                doctorFrame, new RegisterDoctor(doctorRepository), new DeleteDoctor(doctorRepository));

        AddressEntryFrame addressFrame = new AddressEntryFrame();
        new AddressEntryController(addressFrame, doctorController::useAddress);

        doctorFrame
                .getSearchButton()
                .addActionListener(
                        new DoctorSearchController(doctorFrame, new ListDoctors(doctorRepository), doctorController));
        doctorFrame.getAddressButton().addActionListener(new AddAddressListener(frameSistema, addressFrame));

        frameSistema.getMenuPrincipal().onRegisterDoctor(new RegisterDoctorMenuListener(frameSistema, doctorFrame));

        frameSistema.setVisible(true);
    }
}
