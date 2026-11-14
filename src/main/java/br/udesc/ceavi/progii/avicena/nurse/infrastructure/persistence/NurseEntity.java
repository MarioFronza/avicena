package br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.AuditedEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
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
@Table(name = "nurses")
public class NurseEntity extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nurses_seq")
    @SequenceGenerator(name = "nurses_seq", sequenceName = "nurses_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    @Column(name = "qualification", nullable = false)
    private String qualification;

    @Column(name = "training_hours", nullable = false)
    private int trainingHours;

    protected NurseEntity() {}

    public NurseEntity(Long id, PersonEntity person, String qualification, int trainingHours) {
        this.id = id;
        this.person = person;
        this.qualification = qualification;
        this.trainingHours = trainingHours;
    }

    public void update(String qualification, int trainingHours) {
        this.qualification = qualification;
        this.trainingHours = trainingHours;
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

    public String getQualification() {
        return qualification;
    }

    public int getTrainingHours() {
        return trainingHours;
    }
}
