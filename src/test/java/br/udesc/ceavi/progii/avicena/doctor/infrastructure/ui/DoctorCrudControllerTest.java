package br.udesc.ceavi.progii.avicena.doctor.infrastructure.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.udesc.ceavi.progii.avicena.doctor.domain.Doctor;
import br.udesc.ceavi.progii.avicena.doctor.domain.InvalidDoctorDataException;
import br.udesc.ceavi.progii.avicena.doctor.usecase.DeleteDoctor;
import br.udesc.ceavi.progii.avicena.doctor.usecase.RegisterDoctor;
import br.udesc.ceavi.progii.avicena.patient.domain.Address;
import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import java.awt.Dimension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoctorCrudControllerTest {

    private InMemoryDoctorRepository repo;
    private DoctorRegistrationFrame frame;
    private DoctorCrudController ctrl;

    @BeforeEach
    void setUp() {
        repo = new InMemoryDoctorRepository();
        frame = new DoctorRegistrationFrame("Doctor Registration", new Dimension(360, 300));
        ctrl = new DoctorCrudController(frame, new RegisterDoctor(repo), new DeleteDoctor(repo));
    }

    @Test
    void registerSavesDoctorFromFormFields() {
        frame.getNameField().setText("Dr Test");
        frame.getCpfField().setText("12345678900");
        frame.getPhoneField().setText("48999990000");
        frame.getCrmField().setText("12345");
        frame.getSpecialtyField().setText("Cardiology");
        frame.getMaritalStatusComboBox().setSelectedItem(MaritalStatus.SINGLE);

        ctrl.register();

        assertEquals("12345678900", repo.findAll().get(0).getCpf());
    }

    @Test
    void deleteRemovesTheCurrentDoctor() {
        Doctor doctor = DoctorCrudController.toDoctor(
                "Dr Test", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "12345", "Cardiology");
        repo.save(doctor);
        ctrl.setDoctor(doctor);

        ctrl.delete();

        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void toDoctorBuildsADoctorFromValidFormValues() {
        Address address = new Address(100, "Apt 2", "88000000", "Main St", "Downtown", "Florianopolis");

        Doctor doctor = DoctorCrudController.toDoctor(
                "Joao Teste", "12345678900", "48999990000", address, MaritalStatus.SINGLE, "12345", "Cardiology");

        assertEquals("Joao Teste", doctor.getName());
        assertEquals("12345678900", doctor.getCpf());
        assertEquals("48999990000", doctor.getPhone());
        assertEquals(address, doctor.getAddress());
        assertEquals(MaritalStatus.SINGLE, doctor.getMaritalStatus());
        assertEquals("12345", doctor.getCrm());
        assertEquals("Cardiology", doctor.getSpecialty());
    }

    @Test
    void toDoctorThrowsForBlankName() {
        assertThrows(
                InvalidDoctorDataException.class,
                () -> DoctorCrudController.toDoctor(
                        "", "12345678900", "48999990000", null, MaritalStatus.SINGLE, "12345", "Cardiology"));
    }
}
