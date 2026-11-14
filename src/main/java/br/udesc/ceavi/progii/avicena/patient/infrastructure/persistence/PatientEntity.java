package br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.AuditedEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class PatientEntity extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patients_seq")
    @SequenceGenerator(name = "patients_seq", sequenceName = "patients_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    protected PatientEntity() {}

    public PatientEntity(Long id, PersonEntity person) {
        this.id = id;
        this.person = person;
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
}
