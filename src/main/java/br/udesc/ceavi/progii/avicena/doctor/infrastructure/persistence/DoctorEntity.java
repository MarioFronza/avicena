package br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.AuditedEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.AddressEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctors")
public class DoctorEntity extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctors_seq")
    @SequenceGenerator(name = "doctors_seq", sequenceName = "doctors_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    @Column(name = "crm")
    private String crm;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private SpecialtyEntity specialty;

    protected DoctorEntity() {}

    public DoctorEntity(Long id, PersonEntity person, String crm, SpecialtyEntity specialty) {
        this.id = id;
        this.person = person;
        this.crm = crm;
        this.specialty = specialty;
    }

    public void update(String crm, SpecialtyEntity specialty) {
        this.crm = crm;
        this.specialty = specialty;
    }

    public Long getId() {
        return id;
    }

    public PersonEntity getPerson() {
        return person;
    }

    public String getName() {
        return person.getName();
    }

    public String getCpf() {
        return person.getCpf();
    }

    public String getPhone() {
        return person.getPhone();
    }

    public AddressEntity getAddress() {
        return person.getAddress();
    }

    public String getCrm() {
        return crm;
    }

    public SpecialtyEntity getSpecialty() {
        return specialty;
    }
}
