/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.progii.avicena.main;

import br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller.AppointmentController;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller.DiagnosisController;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.controller.PatientHistoryController;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.AppointmentJpaRepository;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence.DiagnosisJpaRepository;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing.AppointmentListFrame;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing.AppointmentRegistrationFrame;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing.BtGerarReceiraListener;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing.DiagnosisRegistrationFrame;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing.ListAppointmentsMenuListener;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing.PatientHistoryFrame;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing.RegisterAppointmentMenuListener;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing.RegisterDiagnosisMenuListener;
import br.udesc.ceavi.progii.avicena.appointment.infrastructure.ui.swing.ShowPatientHistoryMenuListener;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteAppointment;
import br.udesc.ceavi.progii.avicena.appointment.usecase.DeleteDiagnosis;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterAppointment;
import br.udesc.ceavi.progii.avicena.appointment.usecase.RegisterDiagnosis;
import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import br.udesc.ceavi.progii.avicena.control.listenersMenu.MenuSobreListener;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.controller.DoctorController;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence.DoctorJpaRepository;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui.swing.DoctorRegistrationFrame;
import br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui.swing.RegisterDoctorMenuListener;
import br.udesc.ceavi.progii.avicena.doctor.usecase.DeleteDoctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.ListDoctors;
import br.udesc.ceavi.progii.avicena.doctor.usecase.RegisterDoctor;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.controller.NurseController;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence.NurseJpaRepository;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.ui.swing.NurseRegistrationFrame;
import br.udesc.ceavi.progii.avicena.nurse.infrastructure.ui.swing.RegisterNurseMenuListener;
import br.udesc.ceavi.progii.avicena.nurse.usecase.DeleteNurse;
import br.udesc.ceavi.progii.avicena.nurse.usecase.ListNurses;
import br.udesc.ceavi.progii.avicena.nurse.usecase.RegisterNurse;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.controller.PatientController;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PatientJpaRepository;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.swing.AddAddressListener;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.swing.AddressEntryFrame;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.swing.PatientRegistrationFrame;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.ui.swing.RegisterPatientMenuListener;
import br.udesc.ceavi.progii.avicena.patient.usecase.DeletePatient;
import br.udesc.ceavi.progii.avicena.patient.usecase.ListPatients;
import br.udesc.ceavi.progii.avicena.patient.usecase.RegisterPatient;
import br.udesc.ceavi.progii.avicena.receptionist.infrastructure.controller.ReceptionistController;
import br.udesc.ceavi.progii.avicena.receptionist.infrastructure.persistence.ReceptionistJpaRepository;
import br.udesc.ceavi.progii.avicena.receptionist.infrastructure.ui.swing.ReceptionistRegistrationFrame;
import br.udesc.ceavi.progii.avicena.receptionist.infrastructure.ui.swing.RegisterReceptionistMenuListener;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.DeleteReceptionist;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.ListReceptionists;
import br.udesc.ceavi.progii.avicena.receptionist.usecase.RegisterReceptionist;
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

        PatientJpaRepository patientRepository = new PatientJpaRepository();
        PatientRegistrationFrame patientFrame = new PatientRegistrationFrame(new PatientController(
                new RegisterPatient(patientRepository),
                new DeletePatient(patientRepository),
                new ListPatients(patientRepository)));

        AddressEntryFrame patientAddressFrame = new AddressEntryFrame(patientFrame::useAddress);

        patientFrame.getAddressButton().addActionListener(new AddAddressListener(frameSistema, patientAddressFrame));

        frameSistema.getMenuPrincipal().add(new RegisterPatientMenuListener(frameSistema, patientFrame));

        ReceptionistJpaRepository receptionistRepository = new ReceptionistJpaRepository();
        ReceptionistRegistrationFrame receptionistFrame = new ReceptionistRegistrationFrame(new ReceptionistController(
                new RegisterReceptionist(receptionistRepository),
                new DeleteReceptionist(receptionistRepository),
                new ListReceptionists(receptionistRepository)));

        AddressEntryFrame receptionistAddressFrame = new AddressEntryFrame(receptionistFrame::useAddress);

        receptionistFrame
                .getAddressButton()
                .addActionListener(new AddAddressListener(frameSistema, receptionistAddressFrame));

        frameSistema.getMenuPrincipal().add(new RegisterReceptionistMenuListener(frameSistema, receptionistFrame));

        DoctorJpaRepository doctorRepository = new DoctorJpaRepository();
        DoctorRegistrationFrame doctorFrame = new DoctorRegistrationFrame(new DoctorController(
                new RegisterDoctor(doctorRepository),
                new DeleteDoctor(doctorRepository),
                new ListDoctors(doctorRepository)));

        AddressEntryFrame doctorAddressFrame = new AddressEntryFrame(doctorFrame::useAddress);

        doctorFrame.getAddressButton().addActionListener(new AddAddressListener(frameSistema, doctorAddressFrame));

        frameSistema.getMenuPrincipal().add(new RegisterDoctorMenuListener(frameSistema, doctorFrame));

        NurseJpaRepository nurseRepository = new NurseJpaRepository();
        NurseRegistrationFrame nurseFrame = new NurseRegistrationFrame(new NurseController(
                new RegisterNurse(nurseRepository), new DeleteNurse(nurseRepository), new ListNurses(nurseRepository)));

        AddressEntryFrame nurseAddressFrame = new AddressEntryFrame(nurseFrame::useAddress);

        nurseFrame.getAddressButton().addActionListener(new AddAddressListener(frameSistema, nurseAddressFrame));

        frameSistema.getMenuPrincipal().add(new RegisterNurseMenuListener(frameSistema, nurseFrame));

        AppointmentJpaRepository appointmentRepository = new AppointmentJpaRepository();
        AppointmentRegistrationFrame appointmentFrame = new AppointmentRegistrationFrame(new AppointmentController(
                new RegisterAppointment(appointmentRepository),
                new DeleteAppointment(appointmentRepository),
                new ListPatients(patientRepository),
                new ListDoctors(doctorRepository),
                new ListNurses(nurseRepository)));

        frameSistema.getMenuPrincipal().add(new RegisterAppointmentMenuListener(frameSistema, appointmentFrame));

        DiagnosisJpaRepository diagnosisRepository = new DiagnosisJpaRepository();
        DiagnosisRegistrationFrame diagnosisFrame = new DiagnosisRegistrationFrame(new DiagnosisController(
                new RegisterDiagnosis(diagnosisRepository), new DeleteDiagnosis(diagnosisRepository)));
        diagnosisFrame.getGenerateReceiptButton().addActionListener(new BtGerarReceiraListener(diagnosisFrame));

        frameSistema.getMenuPrincipal().add(new RegisterDiagnosisMenuListener(frameSistema, diagnosisFrame));

        AppointmentListFrame appointmentListFrame = new AppointmentListFrame();
        frameSistema.getMenuPrincipal().add(new ListAppointmentsMenuListener(frameSistema, appointmentListFrame));

        PatientHistoryFrame patientHistoryFrame = new PatientHistoryFrame(new PatientHistoryController());
        frameSistema.getMenuPrincipal().add(new ShowPatientHistoryMenuListener(frameSistema, patientHistoryFrame));

        frameSistema.getMenuPrincipal().add(new MenuSobreListener(frameSistema));

        frameSistema.setVisible(true);
    }
}
